package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "movie")
    private Set<Screening> screenings = new HashSet<>();

    public Movie() {
    }

    public Movie(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void addScreening(Screening screening) {
        screenings.add(screening);
        screening.setMovie(this);
    }

}
