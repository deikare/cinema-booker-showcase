package com.example.cinemabooker.services;

import com.example.cinemabooker.controllers.requests.ReservationRequest;
import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ReservationRepository;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.repositories.SeatRepository;
import com.example.cinemabooker.repositories.SeatsRowRepository;
import com.example.cinemabooker.services.exceptions.BadReservationRequestException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Reservation validateAndSaveReservation(ReservationRequest reservationRequest) throws BadReservationRequestException {
        String screeningId = reservationRequest.getScreeningId();

        Screening screening = findFetchedScreening(screeningId);

        Instant expirationTime = screening.getScreeningTime().minus(15, ChronoUnit.MINUTES);

        Reservation reservation = new Reservation(screening, expirationTime, reservationRequest.getName(), reservationRequest.getSurname());

        List<Seat> seatsToSave = getSeatsWithUpdatedReservation(reservationRequest.getSeats(), reservation, screening);

        seatRepository.saveAll(seatsToSave);
        reservation = repository.save(reservation);
        logger.info("Successfully created new " + reservation);
        return reservation;
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
            int first = seatRequest.getFirst();
            List<SeatType> types = seatRequest.getTypes();
            int size = types.size();
            int last = size + first;

            SeatsRow seatsRow = getSeatsRowWithPosition(screening.getSeatsRows(), rowPosition);

            List<Seat> seats = seatsRow.getSeats();
            if (last > seats.size()) {
                String msg = "Too many seats in row " + rowPosition + ": received request to reserve seats in range [" + first + "," + last + "], last seat number = " + (seats.size() + 1);
                logger.info(msg);
                throw new BadReservationRequestException(msg);
            }

            first--;
            for (int i = 0; i < size; i++) {
                Seat seat = seats.get(first);
                if (seat.getReservation() != null) {
                    String msg = seat + " is already taken";
                    logger.info(msg);
                    throw new BadReservationRequestException(msg);
                }

                reservation.addSeat(seat, types.get(i));
                result.add(seat);
                first++;
            }
        });

        return result;
    }

    private SeatsRow getSeatsRowWithPosition(List<SeatsRow> seatsRows, long rowPosition) throws BadReservationRequestException {
        if (rowPosition > seatsRows.size()) {
            String msg = "Seats row with position=" + rowPosition + " not found";
            logger.info(msg);
            throw new BadReservationRequestException(msg);
        }

        return seatsRows.get((int) rowPosition - 1);
    }
}
