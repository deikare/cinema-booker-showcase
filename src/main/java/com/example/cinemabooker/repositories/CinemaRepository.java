package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Cinema;
import com.example.cinemabooker.model.projections.TestProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(excerptProjection = TestProjection.class)
public interface CinemaRepository extends JpaRepository<Cinema, String> {
}
