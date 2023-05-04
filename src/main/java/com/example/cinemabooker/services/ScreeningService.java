package com.example.cinemabooker.services;

import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.services.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Screening> findAll(Pageable pageable, Instant start, Instant end) {
        Page<Screening> result = repository.findAllByScreeningTimeBetween(start, end, pageable);
        return logFindResult(result);
    }

    public Page<Screening> findAll(String movieId, Pageable pageable) {
        Page<Screening> result = repository.findAllByMovieId(movieId, pageable);
        return logFindResult(result);
    }

    public Screening findAndFetchSeats(String id) throws EntityNotFoundException {
        return repository.findByIdAndFetchSeatsRows(id).map(t -> {
                    logger.info("Successfully found entity{id=" + id + "}");
                    return t;
                })
                .orElseThrow(() -> {
                    String msg = "Entity{id=" + id + "} not found";
                    logger.info(msg);
                    return new NotFoundException(msg);
                });
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
            Seat newSeat = new Seat(column);
            newRow.addSeat(newSeat);
            seatService.save(newSeat);
        }
    }
}
