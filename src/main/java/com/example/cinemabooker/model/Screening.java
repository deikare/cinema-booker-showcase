package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
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

    public Screening() {
    }

    public Screening(Movie movie, Room room, Instant screeningTime) {
        this.movie = movie;
        this.room = room;
        this.screeningTime = screeningTime;
    }

    public Screening(Movie movie, Room room, Instant screeningTime, long rowsNumber, long columnsNumber) {
        this.movie = movie;
        this.room = room;
        this.screeningTime = screeningTime;

        for (long row = 1; row <= rowsNumber; row++) {
            addSeatsInRow(row, columnsNumber);
        }
    }

    public Screening(Movie movie, Room room, Instant screeningTime, Long[] rowNumbers) {
        this.movie = movie;
        this.room = room;
        this.screeningTime = screeningTime;

        int n = rowNumbers.length;
        for (int rowNumber = 1; rowNumber <= n; rowNumber++)
            addSeatsInRow(rowNumber, rowNumbers[rowNumber - 1]);
    }

    private void addSeatsInRow(long rowPosition, long seatsNumber) {
        SeatsRow newRow = new SeatsRow(rowPosition, this);
        for (long column = 1; column <= seatsNumber; column++)
            newRow.addSeat(new Seat(seatsNumber));

        seatsRows.add(newRow);
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
