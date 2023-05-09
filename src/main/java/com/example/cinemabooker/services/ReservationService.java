package com.example.cinemabooker.services;

import com.example.cinemabooker.controllers.requests.ReservationRequest;
import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ReservationRepository;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.repositories.SeatRepository;
import com.example.cinemabooker.repositories.SeatsRowRepository;
import com.example.cinemabooker.services.exceptions.BadReservationRequestException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {
    private final SeatRepository seatRepository;

    private final ScreeningRepository screeningRepository;

    public ReservationService(ReservationRepository repository, SeatsRowRepository seatsRowRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository) {
        super(repository, LoggerFactory.getLogger(ReservationService.class));
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
    }

    public Reservation validateAndSaveReservation(ReservationRequest reservationRequest) throws BadReservationRequestException {
        String screeningId = reservationRequest.getScreeningId();

        Screening screening = findFetchedScreening(screeningId);

        Instant expirationTime = screening.getScreeningTime().minus(15, ChronoUnit.MINUTES);

        Reservation reservation = new Reservation(screening, expirationTime, reservationRequest.getName(), reservationRequest.getSurname());

        List<Seat> seatsToSave = getSeatsWithUpdatedReservation(reservationRequest.getSeats(), reservation, screening);

        seatRepository.saveAll(seatsToSave);
        logger.info("Successfully created new " + reservation);
        return repository.save(reservation);
    }

    private Screening findFetchedScreening(String screeningId) throws BadReservationRequestException {
        return screeningRepository.findByIdAndFetchSeatsRows(screeningId).orElseThrow(() -> {
            String msg = "Screening{id=" + screeningId + "} not found";
            logger.info(msg);
            return new BadReservationRequestException(msg);
        });
    }

    private List<Seat> getSeatsWithUpdatedReservation(Map<Long, ReservationRequest.SeatReservation> seatReservationMap, Reservation reservation, Screening screening) throws BadReservationRequestException {
        List<Seat> result = new ArrayList<>();

        seatReservationMap.forEach((rowPosition, seatRequest) -> {
            long first = seatRequest.getFirst();
            long last = seatRequest.getLast();
            List<SeatType> types = seatRequest.getTypes();

            if (first > last) {
                String msg = "Starting seat position is bigger than end's position";
                logger.info(msg);
                throw new BadReservationRequestException(msg);
            }

            long declaredSeatsSize = last - first + 1;
            if (declaredSeatsSize != types.size()) {
                String msg = "Types list size=" + types.size() + " should match declared size=" + declaredSeatsSize;
                logger.info(msg);
                throw new BadReservationRequestException(msg);
            }

            SeatsRow seatsRow = getSeatsRowWithPosition(screening.getSeatsRows(), rowPosition);

            AtomicInteger indexHolder = new AtomicInteger(0);

            seatsRow.getSeats().forEach(seat -> {
                int index = indexHolder.getAndIncrement();
                long seatPosition = seat.getPosition();
                if (seatPosition >= first && seatPosition <= last) {
                    if (seat.getReservation() == null) {
                        reservation.addSeat(seat, types.get(index));
                        result.add(seat);
                    } else {
                        String msg = seat + " is already taken";
                        logger.info(msg);
                        throw new BadReservationRequestException(msg);
                    }
                }
            });
        });

        return result;
    }

    private SeatsRow getSeatsRowWithPosition(List<SeatsRow> seatsRows, long rowPosition) {
        return seatsRows.stream()
                .filter(aSeatsRow -> aSeatsRow.getPosition() == rowPosition)
                .findFirst()
                .orElseThrow(() -> {
                    String msg = "Seats row with position=" + rowPosition + " not found";
                    logger.info(msg);
                    return new BadReservationRequestException(msg);
                });
    }
}
