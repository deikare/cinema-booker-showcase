package com.example.cinemabooker.controllers.forms;

import com.example.cinemabooker.model.SeatType;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationForm {
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
    Map<@Positive(message = "row number must be positive") Long, @NotEmpty(message = "seats in row list cannot be empty") List<SeatReservation>> seats;

    @Override
    public String toString() {
        String seatsMsg = seats.entrySet().stream().map(longListEntry -> {
            return "{row=" + longListEntry.getKey() + ": seats=[" + longListEntry.getValue().stream().map(SeatReservation::toString).collect(Collectors.joining(",")) + "]}";
        }).collect(Collectors.joining(", "));
        return "ReservationForm{" +
                "screeningId='" + screeningId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", seats=[" + seatsMsg + "]" +
                '}';
    }

    private static class SeatReservation {
        @Positive(message = "seat number must be positive")
        private long number;

        @NotBlank(message = "reservation type must not be blank")
        private SeatType type;

        @Override
        public String toString() {
            return "{number=" + number + ", type=" + type + "}";
        }

        public long getNumber() {
            return number;
        }

        public void setNumber(long number) {
            this.number = number;
        }

        public SeatType getType() {
            return type;
        }

        public void setType(SeatType type) {
            this.type = type;
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

    public Map<Long, List<SeatReservation>> getSeats() {
        return seats;
    }

    public void setSeats(Map<Long, List<SeatReservation>> seats) {
        this.seats = seats;
    }
}
