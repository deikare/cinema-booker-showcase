package com.example.cinemabooker.model;

import com.example.cinemabooker.services.interfaces.EntityInterface;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;

@Entity
@Table(name = "Reservations")
public class Reservation extends AbstractEntity implements EntityInterface {
    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reservation")
    private HashSet<Seat> seats = new HashSet<>();



    public Reservation(Screening screening, Instant expirationTime, String name, String surname) {
        this.screening = screening;
        this.expirationTime = expirationTime;
        this.name = name;
        this.surname = surname;
    }

    private Instant expirationTime;
    private String name;
    private String surname;

    public Reservation() {
    }
}
