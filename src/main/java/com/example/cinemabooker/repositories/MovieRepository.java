package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
