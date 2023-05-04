package com.example.cinemabooker.services;

import com.example.cinemabooker.controllers.representation.models.MovieWithScreeningsModel;
import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.model.Screening;
import com.example.cinemabooker.repositories.MovieRepository;
import com.example.cinemabooker.repositories.ScreeningRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService extends BaseService<Movie, MovieRepository> {
    private final ScreeningRepository screeningRepository;
    public MovieService(MovieRepository repository, ScreeningRepository screeningRepository) {
        super(repository, LoggerFactory.getLogger(MovieService.class));
        this.screeningRepository = screeningRepository;
    }
    public Page<MovieWithScreeningsModel> findAllBetween(Instant start, Instant end, Pageable pageable) {
        Page<Screening> screenings = screeningRepository.findAllByScreeningTimeBetween(start, end, pageable);

        Map<Movie, List<Screening>> movies = screenings.get().collect(Collectors.groupingBy(Screening::getMovie));
        List<MovieWithScreeningsModel> moviesView = movies.entrySet().stream().map(movieListEntry -> new MovieWithScreeningsModel(movieListEntry.getKey(), movieListEntry.getValue())).toList();
        return new PageImpl<>(moviesView, pageable, screenings.getTotalElements());
    }

}
