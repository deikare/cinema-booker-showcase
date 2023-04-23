package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.representation.assemblers.CinemaModelAssembler;
import com.example.cinemabooker.model.Cinema;
import com.example.cinemabooker.services.NotFoundException;
import com.example.cinemabooker.services.CinemaService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {
    private final CinemaService cinemaService;
    private final PagedResourcesAssembler<Cinema> pagedResourcesAssembler;
    private final CinemaModelAssembler cinemaModelAssembler;

    public CinemaController(CinemaService cinemaService, PagedResourcesAssembler<Cinema> pagedResourcesAssembler, CinemaModelAssembler cinemaModelAssembler) {
        this.cinemaService = cinemaService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.cinemaModelAssembler = cinemaModelAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Cinema>>> all(@RequestParam(defaultValue = ControllerDefaults.PAGE_NUMBER_AS_STRING) int page, @RequestParam(defaultValue = ControllerDefaults.PAGE_SIZE_AS_STRING) int size) {
        Page<Cinema> cinemas = cinemaService.findAll(page, size);

        PagedModel<EntityModel<Cinema>> cinemasView = pagedResourcesAssembler.toModel(cinemas, cinemaModelAssembler);

        return new ResponseEntity<>(cinemasView, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EntityModel<Cinema> one(@PathVariable String id) {
        Cinema cinema;

        try {
            cinema = cinemaService.find(id);
        }
        catch (NotFoundException e) {
            throw e;
        }

        return cinemaModelAssembler.toModel(cinema);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        try {
            cinemaService.delete(id);
        }
        catch (NotFoundException e) {
            throw e;
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
