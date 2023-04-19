package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Screening {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id")
    private Room room;

    private Instant screeningTime;

    @OneToMany(mappedBy = "screening", orphanRemoval = true, cascade = CascadeType.ALL)
    Set<SeatsRow> seatsRows = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
