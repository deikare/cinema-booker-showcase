package com.example.cinemabooker.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Cinema extends AbstractEntity {
    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms = new HashSet<>();

    public Cinema() {
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setCinema(this);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                '}';
    }
}
