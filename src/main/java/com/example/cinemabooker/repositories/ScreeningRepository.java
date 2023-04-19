package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ScreeningRepository extends JpaRepository<Screening, String> {
}
