package com.example.cinemabooker.model;

import jakarta.persistence.*;


@Entity
public class Seat extends AbstractEntity {
    private long position;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seats_row_id")
    private SeatsRow row;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;


    public Seat() {

    }

    public Seat(long position) {
        this.position = position;
    }

    public SeatsRow getRow() {
        return row;
    }

    public void setRow(SeatsRow row) {
        this.row = row;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
