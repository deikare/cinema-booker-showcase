package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
