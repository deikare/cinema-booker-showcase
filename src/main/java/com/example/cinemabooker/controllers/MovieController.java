package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.representation.assemblers.MovieModelAssembler;
import com.example.cinemabooker.controllers.representation.assemblers.MovieWithScreeningsModelAssembler;
import com.example.cinemabooker.controllers.representation.models.MovieWithScreeningsModel;
import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.repositories.MovieRepository;
import com.example.cinemabooker.services.MovieService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
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
    private final MovieWithScreeningsModelAssembler movieWithScreeningsModelAssembler;
    private final PagedResourcesAssembler<MovieWithScreeningsModel> movieWithScreeningsPagedResourcesAssembler;
    protected MovieController(MovieService service, PagedResourcesAssembler<Movie> pagedResourcesAssembler, MovieModelAssembler modelAssembler, MovieWithScreeningsModelAssembler movieWithScreeningsModelAssembler, PagedResourcesAssembler<MovieWithScreeningsModel> movieWithScreeningsPagedResourcesAssembler) {
        super(LoggerFactory.getLogger(MovieController.class), service, pagedResourcesAssembler, modelAssembler);
        this.movieWithScreeningsModelAssembler = movieWithScreeningsModelAssembler;
        this.movieWithScreeningsPagedResourcesAssembler = movieWithScreeningsPagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<MovieWithScreeningsModel>>> all(@RequestParam(defaultValue = ControllerDefaults.PAGE_NUMBER_AS_STRING) int page, @RequestParam(defaultValue = ControllerDefaults.PAGE_SIZE_AS_STRING) int size, @RequestParam(required = false) Instant start, @RequestParam(required = false) Instant end) {
        if (start == null)
            start = Instant.EPOCH;
        if (end == null)
            end = Instant.now();
        Sort sort = Sort.by("movie.title").ascending().and(Sort.by("screeningTime").ascending());
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MovieWithScreeningsModel> movies = service.findAllBetween(start, end, pageable);
        PagedModel<EntityModel<MovieWithScreeningsModel>> moviesView = movieWithScreeningsPagedResourcesAssembler.toModel(movies, movieWithScreeningsModelAssembler);
        return new ResponseEntity<>(moviesView, HttpStatus.OK);
    }
}
