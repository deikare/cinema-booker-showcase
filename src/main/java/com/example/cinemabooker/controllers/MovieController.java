package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.representation.assemblers.MovieModelAssembler;
import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController extends BaseController<Movie, MovieService, MovieModelAssembler> {
    protected MovieController(MovieService service, PagedResourcesAssembler<Movie> pagedResourcesAssembler, MovieModelAssembler modelAssembler) {
        super(LoggerFactory.getLogger(MovieController.class), service, pagedResourcesAssembler, modelAssembler);
    }
}
