package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class SeatsRow {
    @Id
    private Long id;

    private long position;

    @OneToMany(mappedBy = "row", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Seat> seats = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
