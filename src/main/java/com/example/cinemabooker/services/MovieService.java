package com.example.cinemabooker.services;

import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.repositories.MovieRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends BaseService<Movie> {
    public MovieService(MovieRepository repository) {
        super(repository, LoggerFactory.getLogger(MovieService.class));
    }
}
