package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    private Instant expirationTime;
    private String reserverName;
    private String reserverSurname;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
