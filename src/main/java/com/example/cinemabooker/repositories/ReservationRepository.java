package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
