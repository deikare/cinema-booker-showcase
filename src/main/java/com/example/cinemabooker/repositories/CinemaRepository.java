package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
