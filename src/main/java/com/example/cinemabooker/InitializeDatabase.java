package com.example.cinemabooker;

import com.example.cinemabooker.model.Cinema;
import com.example.cinemabooker.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitializeDatabase {
    private static final Logger logger = LoggerFactory.getLogger(InitializeDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CinemaRepository cinemaRepository, MovieRepository movieRepository, ReservationRepository reservationRepository, RoomRepository roomRepository, ScreeningRepository screeningRepository, SeatRepository seatRepository, SeatsRowRepository seatsRowRepository) {
        return args -> {
            Cinema cinema = new Cinema();
            cinema = cinemaRepository.save(cinema);


        };
    }
}
