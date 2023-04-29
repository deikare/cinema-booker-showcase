package com.example.cinemabooker.services;

import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.repositories.MovieRepository;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MovieService extends BaseService<Movie, MovieRepository> {
    public MovieService(MovieRepository repository) {
        super(repository, LoggerFactory.getLogger(MovieService.class));
    }
    public Page<Movie> findAllBetween(int page, int size, Instant start, Instant end) {
        return repository.findAllByScreeningsBetweenOrderByTitleAscScreeningsAsc(PageRequest.of(page, size), start, end);
    }

}
