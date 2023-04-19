package com.example.cinemabooker.model;

import jakarta.persistence.*;


@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private long position;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seats_row_id")
    private SeatsRow row;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;


    public Seat() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
