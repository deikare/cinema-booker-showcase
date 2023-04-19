package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
