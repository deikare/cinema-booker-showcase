package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.representation.assemblers.MovieModelAssembler;
import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.repositories.MovieRepository;
import com.example.cinemabooker.services.MovieService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/movies")
public class MovieController extends BaseController<Movie, MovieRepository, MovieService, MovieModelAssembler> {
    protected MovieController(MovieService service, PagedResourcesAssembler<Movie> pagedResourcesAssembler, MovieModelAssembler modelAssembler) {
        super(LoggerFactory.getLogger(MovieController.class), service, pagedResourcesAssembler, modelAssembler);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Movie>>> all(@RequestParam(defaultValue = ControllerDefaults.PAGE_NUMBER_AS_STRING) int page, @RequestParam(defaultValue = ControllerDefaults.PAGE_SIZE_AS_STRING) int size, @RequestParam(required = false) Instant start, @RequestParam(required = false) Instant end) {
        if (start == null)
            start = Instant.EPOCH;
        if (end == null)
            end = Instant.now();
        Page<Movie> movies = service.findAllBetween(page, size, start, end);
        PagedModel<EntityModel<Movie>> moviesView = pagedResourcesAssembler.toModel(movies, modelAssembler);
        return new ResponseEntity<>(moviesView, HttpStatus.OK);
    }
}
