package com.example.cinemabooker.repositories;

import com.example.cinemabooker.model.Screening;
import com.example.cinemabooker.model.projections.ScreeningWithRoomNumberProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RepositoryRestResource(excerptProjection = ScreeningWithRoomNumberProjection.class)
public interface ScreeningRepository extends JpaRepository<Screening, String> {
    Page<Screening> findAllByScreeningTimeBetween(Instant start, Instant end, Pageable pageable);
    Page<Screening> findAllByMovieId(String movieId, Pageable pageable);
}
