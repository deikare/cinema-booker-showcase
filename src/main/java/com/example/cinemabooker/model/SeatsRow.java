package com.example.cinemabooker.model;

import com.example.cinemabooker.services.interfaces.EntityInterface;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SeatsRows")
public class SeatsRow extends AbstractEntity implements EntityInterface {
    private long position;

    @OneToMany(mappedBy = "row", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Seat> seats = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public SeatsRow() {
    }

    public SeatsRow(long position) {
        this.position = position;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setRow(this);
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }
}
