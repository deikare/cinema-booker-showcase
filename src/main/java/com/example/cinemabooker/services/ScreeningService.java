package com.example.cinemabooker.services;

import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.repositories.SeatRepository;
import com.example.cinemabooker.repositories.SeatsRowRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.Function;

@Service
public class ScreeningService extends BaseServiceWithUpdate<Screening> {
    private final SeatsRowRepository seatsRowRepository;
    private final SeatRepository seatRepository;

    public ScreeningService(ScreeningRepository screeningRepository, SeatsRowRepository seatsRowRepository, SeatRepository seatRepository) {
        super(screeningRepository, LoggerFactory.getLogger(ScreeningService.class));
        this.seatsRowRepository = seatsRowRepository;
        this.seatRepository = seatRepository;
    }

    public void createScreening(Movie movie, Room room, Instant screeningTime, long rowsNumber, long columnsNumber) {
        createScreening(movie, room, screeningTime, rowsNumber, index -> columnsNumber);
    }

    public void createScreening(Movie movie, Room room, Instant screeningTime, Long[] rowNumbers) {
        createScreening(movie, room, screeningTime, rowNumbers.length, index -> rowNumbers[index - 1]);
    }

    private void createScreening(Movie movie, Room room, Instant screeningTime, long rowsNumber, Function<Integer, Long> seatsInRowNumberSupplier) {
        Screening screening = new Screening(movie, room, screeningTime);
        screening = repository.save(screening);

        for (int rowNumber = 1; rowNumber <= rowsNumber; rowNumber++)
            createRowWithSeats(screening, rowNumber, seatsInRowNumberSupplier.apply(rowNumber));

        repository.save(screening);
    }

    private void createRowWithSeats(Screening screening, long rowPosition, long seatsNumber) {
        SeatsRow newRow = new SeatsRow(rowPosition);
        screening.addSeatsRow(newRow);
        seatsRowRepository.save(newRow);

        for (long column = 1; column <= seatsNumber; column++) {
            Seat newSeat = new Seat(seatsNumber);
            newRow.addSeat(newSeat);
            seatRepository.save(newSeat);
        }
    }
}
