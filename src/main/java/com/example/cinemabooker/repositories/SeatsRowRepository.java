package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.SeatsRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatsRowRepository extends JpaRepository<SeatsRow, Long> {
}
