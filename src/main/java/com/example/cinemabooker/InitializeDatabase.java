package com.example.cinemabooker;

import com.example.cinemabooker.model.Cinema;
import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.model.Room;
import com.example.cinemabooker.model.Screening;
import com.example.cinemabooker.repositories.*;
import com.example.cinemabooker.services.ScreeningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class InitializeDatabase {
    private static final Logger logger = LoggerFactory.getLogger(InitializeDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CinemaRepository cinemaRepository, MovieRepository movieRepository, ReservationRepository reservationRepository, RoomRepository roomRepository, ScreeningService screeningService, SeatRepository seatRepository, SeatsRowRepository seatsRowRepository) {
        return args -> {
            Cinema cinema = new Cinema();
            cinemaRepository.save(cinema);

            logger.info(cinemaRepository.findAll().toString());

            Long[] entityNumbers = {1L, 2L, 3L};

            ArrayList<Room> rooms = Arrays.stream(entityNumbers).map(roomNumber -> {
                Room room = new Room(roomNumber);
                cinema.addRoom(room);
                cinemaRepository.save(cinema);
                return roomRepository.save(room);
            }).collect(Collectors.toCollection(ArrayList::new));

            logger.info(roomRepository.findAll().toString());

            ArrayList<Movie> movies = Arrays.stream(entityNumbers).map(movieNumber -> {
                Movie movie = new Movie("m" + movieNumber);
                return movieRepository.save(movie);
            }).collect(Collectors.toCollection(ArrayList::new));

            logger.info(movies.toString());

            Collections.shuffle(movies);
            int i = 0;
            for (Room room : rooms) {
                for (int j = 0; j < 2; j++) {
                    screeningService.createScreening(movies.get(i), room, Instant.now().minus(1, ChronoUnit.HOURS), 5L, 10L);
                    i++;
                    if (i == movies.size())
                        i = 0;
                }
            }

//            logger.info(screeningService.getScreeningPage(0, 10000).toString());
            logger.info(screeningService.findAll().stream().map(screening -> screening.toString()).toList().toString());
        };
    }
}
