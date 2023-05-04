package com.example.cinemabooker.controllers.representation.models;

import com.example.cinemabooker.controllers.ScreeningController;
import com.example.cinemabooker.model.Movie;
import com.example.cinemabooker.model.Screening;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class MovieWithScreeningsModel extends RepresentationModel<MovieWithScreeningsModel> {
    private final String id;
    private final String title;
    private final List<EntityModel<Screening>> screenings = new LinkedList<>();


    public MovieWithScreeningsModel(Movie movie, List<Screening> screenings) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        screenings.forEach(screening -> this.screenings.add(EntityModel.of(screening,
                linkTo(methodOn(ScreeningController.class).one(screening.getId())).withSelfRel() //todo embedding links here is considered antipattern - perhaps custom representation model is needed? - see projections
        )));
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<EntityModel<Screening>> getScreenings() {
        return screenings;
    }
}
