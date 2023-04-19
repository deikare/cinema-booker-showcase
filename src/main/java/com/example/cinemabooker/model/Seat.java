package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Seat {
    @Id //todo check if auto increment is possible
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seats_row_id")
    private SeatsRow row;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Seat() {

    }
}
