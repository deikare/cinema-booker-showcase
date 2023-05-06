package com.example.cinemabooker.controllers.forms;

import com.example.cinemabooker.model.SeatType;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationForm {
    @NotNull
    @Pattern(regexp = ValidationDefaults.ID_PATTERN)
    private String screeningId;

    @NotNull
    @Pattern(regexp = ValidationDefaults.NAME_PATTERN)
    private String name;

    @NotNull
    @Pattern(regexp = ValidationDefaults.SURNAME_PATTERN)
    private String surname;


    @NotNull
    Map<Long, List<SeatReservation>> seats;

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

    private static class SeatReservation { //todo add inner validation to check on emptiness of list
        @Positive
        private long number;

        @NotNull
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
