package com.example.cinemabooker.services;

import com.example.cinemabooker.controllers.requests.ReservationRequest;
import com.example.cinemabooker.model.*;
import com.example.cinemabooker.repositories.ReservationRepository;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.repositories.SeatRepository;
import com.example.cinemabooker.repositories.SeatsRowRepository;
import com.example.cinemabooker.services.exceptions.BadReservationRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.*;

@SpringBootTest
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @InjectMocks
    private ScreeningService screeningService;

    @InjectMocks
    SeatsRowService seatsRowService;
    @InjectMocks
    SeatService seatService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatsRowRepository seatsRowRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @Test
    void shouldThrowScreeningNotFoundException() {
        ReservationRequest request = new ReservationRequest();
        request.setScreeningId(UUID.randomUUID().toString());
        request.setName("Xyz");
        request.setSurname("Xyz");

        Map<Long, ReservationRequest.SeatReservation> seats = new HashMap<>();

        ReservationRequest.SeatReservation seatReservation = new ReservationRequest.SeatReservation();

        seatReservation.setFirst(12);
        List<SeatType> seatTypes = new ArrayList<>();
        seatTypes.add(SeatType.ADULT);
        seatTypes.add(SeatType.STUDENT);
        seatReservation.setTypes(seatTypes);
        seats.put(1L, seatReservation);

        request.setSeats(seats);
        Assertions.assertThrows(BadReservationRequestException.class,
                () -> reservationService.validateAndSaveReservation(request),
                "Screening{id=" + request.getScreeningId() + "} not found");
    }

    @Test
    void shouldThrowToManySeatsInRow() {

        Movie movie = new Movie("m");
        Cinema cinema = new Cinema("c");
        Room room = new Room(123L);
        cinema.addRoom(room);
        Instant time = Instant.now();
        int rows = 4;
        int columns = 5;
//        BDDMockito.given(screeningService.createScreening(movie, room, time, rows, columns)).willReturn(Screening.bui)


        Screening screening = screeningService.createScreening(movie, room, Instant.now(), 5, 6);

        ReservationRequest request = new ReservationRequest();
        request.setScreeningId(screening.getId());
        request.setName("Xyz");
        request.setSurname("Xyz");

        Map<Long, ReservationRequest.SeatReservation> seats = new HashMap<>();

        ReservationRequest.SeatReservation seatReservation = new ReservationRequest.SeatReservation();

        seatReservation.setFirst(12);
        List<SeatType> seatTypes = new ArrayList<>();
        seatTypes.add(SeatType.ADULT);
        seatTypes.add(SeatType.STUDENT);
        seatReservation.setTypes(seatTypes);
        seats.put(1L, seatReservation);

        request.setSeats(seats);
        Throwable exception = Assertions.assertThrows(BadReservationRequestException.class,
                () -> reservationService.validateAndSaveReservation(request));

        Assertions.assertTrue(exception.getMessage().startsWith("Too many seats in row"));
    }
}
