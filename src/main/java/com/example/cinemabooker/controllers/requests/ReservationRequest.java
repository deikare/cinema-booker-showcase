package com.example.cinemabooker.controllers.requests;

import com.example.cinemabooker.model.SeatType;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationRequest {
    @NotBlank(message = "screeningId must not be blank")
    @Pattern(regexp = ValidationDefaults.ID_PATTERN, message = "screeningId must match uuid pattern")
    private String screeningId;

    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = ValidationDefaults.NAME_PATTERN, message = "name must match pattern")
    private String name;

    @NotBlank(message = "surname must not be blank")
    @Pattern(regexp = ValidationDefaults.SURNAME_PATTERN, message = "surname must match pattern")
    private String surname;


    @NotEmpty(message = "seats map cannot be empty")
    Map<@Positive(message = "row number must be positive") Long, @NotNull(message = "seat row info must be set") SeatReservation> seats; //todo change list<seatReservation> to list of union of {seat position, type} or {(seat start, seat end) + types list} - require sorted list
    //todo just do only start, finish + list of types

    @Override
    public String toString() {
        String seatsMsg = seats.entrySet().stream().map(longListEntry -> {
            return "{row=" + longListEntry.getKey() + ": seats=" + longListEntry.getValue().toString() + "}";
        }).collect(Collectors.joining(", "));

        return "ReservationForm{" +
                "screeningId='" + screeningId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", seats=[" + seatsMsg + "]" +
                '}';
    }

    public static class SeatReservation {
        @Positive(message = "seat number first must be positive")
        private long first;

        @Positive(message = "seat number last must be positive")
        private long last;

        @NotEmpty(message = "reservation types must not be empty")
        private List<SeatType> types;

        public long getFirst() {
            return first;
        }

        public void setFirst(long first) {
            this.first = first;
        }

        public long getLast() {
            return last;
        }

        public void setLast(long last) {
            this.last = last;
        }

        public List<SeatType> getTypes() {
            return types;
        }

        public void setTypes(List<SeatType> types) {
            this.types = types;
        }

        @Override
        public String toString() {
            return "SeatReservation{" +
                    "start=" + first +
                    ", end=" + last +
                    ", types=" + types +
                    '}';
        }
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Map<Long, SeatReservation> getSeats() {
        return seats;
    }

    public void setSeats(Map<Long, SeatReservation> seats) {
        this.seats = seats;
    }
}
