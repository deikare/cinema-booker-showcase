package com.example.cinemabooker.controllers.representation.assemblers;

import com.example.cinemabooker.controllers.ControllerDefaults;
import com.example.cinemabooker.controllers.MovieController;
import com.example.cinemabooker.controllers.representation.models.MovieWithScreeningsModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieWithScreeningsModelAssembler implements RepresentationModelAssembler<MovieWithScreeningsModel, EntityModel<MovieWithScreeningsModel>> {
    @Override
    public EntityModel<MovieWithScreeningsModel> toModel(MovieWithScreeningsModel entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(MovieController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).all(ControllerDefaults.PAGE_NUMBER, ControllerDefaults.PAGE_SIZE, null, null)).withRel(ControllerDefaults.ALL_LINK_REL)
        );
    }
}
