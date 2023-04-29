package com.example.cinemabooker.services;

import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ScreeningRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.Function;

@Service
public class ScreeningService extends BaseServiceWithUpdate<Screening, ScreeningRepository> {
    private final SeatsRowService seatsRowService;
    private final SeatService seatService;

    public ScreeningService(ScreeningRepository screeningRepository, SeatsRowService seatsRowService, SeatService seatService) {
        super(screeningRepository, LoggerFactory.getLogger(ScreeningService.class));
        this.seatsRowService = seatsRowService;
        this.seatService = seatService;
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
        seatsRowService.save(newRow);

        for (long column = 1; column <= seatsNumber; column++) {
            Seat newSeat = new Seat(seatsNumber);
            newRow.addSeat(newSeat);
            seatService.save(newSeat);
        }
    }
}
