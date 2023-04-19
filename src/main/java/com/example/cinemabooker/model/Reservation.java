package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Reservation extends AbstractEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    private Instant expirationTime;
    private String reserverName;
    private String reserverSurname;

    public Reservation() {
    }
}
