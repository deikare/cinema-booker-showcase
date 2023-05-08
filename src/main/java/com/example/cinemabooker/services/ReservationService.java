package com.example.cinemabooker.services;

import com.example.cinemabooker.controllers.forms.ReservationForm;
import com.example.cinemabooker.model.Reservation;
import com.example.cinemabooker.model.Screening;
import com.example.cinemabooker.model.SeatsRow;
import com.example.cinemabooker.repositories.ReservationRepository;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.repositories.SeatRepository;
import com.example.cinemabooker.repositories.SeatsRowRepository;
import com.example.cinemabooker.services.exceptions.BadReservationFormException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {
    private final SeatsRowRepository seatsRowRepository;
    private final SeatRepository seatRepository;

    private final ScreeningRepository screeningRepository;

    public ReservationService(ReservationRepository repository, SeatsRowRepository seatsRowRepository, SeatRepository seatRepository, ScreeningRepository screeningRepository) {
        super(repository, LoggerFactory.getLogger(ReservationService.class));
        this.seatsRowRepository = seatsRowRepository;
        this.seatRepository = seatRepository;
        this.screeningRepository = screeningRepository;
    }

    public Reservation validateAndSaveReservation(ReservationForm reservationForm) throws BadReservationFormException {
        Set<Long> rowPositions = reservationForm.getSeats().keySet();
        String screeningId = reservationForm.getScreeningId();
        List<SeatsRow> seatsRows = new ArrayList<>();

        Screening screening = screeningRepository.findByIdAndFetchSeatsRows(screeningId).orElseThrow(() -> {
            String msg = "Screening{id=" + screeningId + "} not found";
            logger.info(msg);
            return new BadReservationFormException(msg);
        });

        Instant expirationTime = screening.getScreeningTime().minus(15, ChronoUnit.MINUTES);

        Reservation reservation = reservationForm.getSeats().entrySet().stream().collect(Collectors.sing) //todo collect verified rows into new reservation based on screening object

        Reservation result = reservationForm.getSeats().entrySet().stream().co(new Reservation(screening, expirationTime, reservationForm.getName(), reservationForm.getSurname()), (reservation, seatsRowEntry) -> {
            reservation.
        }); //todo

        return result;
    }
}
