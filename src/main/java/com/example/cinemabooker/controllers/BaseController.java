package com.example.cinemabooker.controllers;

import com.example.cinemabooker.services.BaseService;
import com.example.cinemabooker.services.interfaces.EntityInterface;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<T extends EntityInterface, R extends JpaRepository<T, String>, S extends BaseService<T, R>, A extends RepresentationModelAssembler<T, EntityModel<T>>> {
    protected final Logger logger;
    protected final S service;
    protected final PagedResourcesAssembler<T> pagedResourcesAssembler;
    protected final A modelAssembler;

    protected BaseController(Logger logger, S service, PagedResourcesAssembler<T> pagedResourcesAssembler, A modelAssembler) {
        this.logger = logger;
        this.service = service;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = modelAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<T> one(@PathVariable String id) {
        logger.info("Received request get one entity: id=" + id);
        T entity = service.find(id);
        return modelAssembler.toModel(entity);
    }

    @PostMapping
    public ResponseEntity<?> newEntity(@RequestBody T entity) {
        logger.info("Received request post entity" + entity);

        EntityModel<T> entityModel = modelAssembler.toModel(service.create(entity));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        logger.info("Received request get one entity: id=" + id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
