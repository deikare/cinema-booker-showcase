package com.example.cinemabooker.services;

import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.repositories.SeatRepository;
import com.example.cinemabooker.repositories.SeatsRowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final SeatsRowRepository seatsRowRepository;
    private final SeatRepository seatRepository;

    public ScreeningService(ScreeningRepository screeningRepository, SeatsRowRepository seatsRowRepository, SeatRepository seatRepository) {
        this.screeningRepository = screeningRepository;
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
        screening = screeningRepository.save(screening);

        for (int rowNumber = 1; rowNumber <= rowsNumber; rowNumber++)
            createRowWithSeats(screening, rowNumber, seatsInRowNumberSupplier.apply(rowNumber));

        screeningRepository.save(screening);
    }

//    private void createScreening(Movie movie, Room room, Instant screeningTime, )

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

    public Page<Screening> findAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        return screeningRepository.findAll(pageRequest);
    }

    public List<Screening> findAll() {
        return screeningRepository.findAll();
    }

}
