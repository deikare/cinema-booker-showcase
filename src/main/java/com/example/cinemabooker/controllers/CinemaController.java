package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.representation.assemblers.CinemaModelAssembler;
import com.example.cinemabooker.model.Cinema;
import com.example.cinemabooker.services.CinemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(CinemaController.class);
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
        logger.info("Received request get paged entities: page=" + page + ", size=" + size);
        PagedModel<EntityModel<Cinema>> cinemasView = pagedResourcesAssembler.toModel(cinemas, cinemaModelAssembler);
        return new ResponseEntity<>(cinemasView, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EntityModel<Cinema> one(@PathVariable String id) {
        logger.info("Received request get one entity: id=" + id);
        Cinema cinema = cinemaService.find(id);
        return cinemaModelAssembler.toModel(cinema);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        logger.info("Received request get one entity: id=" + id);
        cinemaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
