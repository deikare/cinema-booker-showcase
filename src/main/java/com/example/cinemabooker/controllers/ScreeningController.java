package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.representation.assemblers.ScreeningModelAssembler;
import com.example.cinemabooker.controllers.representation.assemblers.ScreeningWithSeatsModelAssembler;
import com.example.cinemabooker.controllers.representation.models.ScreeningWithSeatsModel;
import com.example.cinemabooker.model.Screening;
import com.example.cinemabooker.repositories.ScreeningRepository;
import com.example.cinemabooker.services.ReservationService;
import com.example.cinemabooker.services.ScreeningService;
import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@RestController
@RequestMapping("/screenings")
public class ScreeningController extends BaseController<Screening, ScreeningRepository, ScreeningService, ScreeningModelAssembler> {
    private final ScreeningWithSeatsModelAssembler screeningWithSeatsModelAssembler;

    protected ScreeningController(ScreeningService service, PagedResourcesAssembler<Screening> pagedResourcesAssembler, ScreeningModelAssembler modelAssembler, ScreeningWithSeatsModelAssembler screeningWithSeatsModelAssembler) {
        super(LoggerFactory.getLogger(ScreeningController.class), service, pagedResourcesAssembler, modelAssembler);
        this.screeningWithSeatsModelAssembler = screeningWithSeatsModelAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Screening>>> all(@RequestParam(defaultValue = ControllerDefaults.PAGE_NUMBER_AS_STRING) int page, @RequestParam(defaultValue = ControllerDefaults.PAGE_SIZE_AS_STRING) int size, @RequestParam(required = false) Instant start, @RequestParam(required = false) Instant end, @RequestParam(required = false) String movieId) {
        Page<Screening> screenings;
        if (movieId == null) {
            if (start == null)
                start = Instant.EPOCH;
            if (end == null)
                end = ControllerDefaults.END;
            end = end.minus(ReservationService.PRE_SCREENING_DURATION, ChronoUnit.MINUTES);
            logger.info("Received request to get all screenings={start=" + start + ",end=" + end + "}");
            Sort sort = Sort.by("movie.title").ascending().and(Sort.by("screeningTime").ascending());
            Pageable pageable = PageRequest.of(page, size, sort);
            screenings = service.findAll(pageable, start, end);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            screenings = service.findAll(movieId, pageable);
        }
        PagedModel<EntityModel<Screening>> screeningsView = pagedResourcesAssembler.toModel(screenings, modelAssembler);
        return new ResponseEntity<>(screeningsView, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public EntityModel<ScreeningWithSeatsModel> one(@PathVariable String id) {
        logger.info("Received request get one entity: id=" + id);
        Screening entity = service.findAndFetchSeats(id);
        return screeningWithSeatsModelAssembler.toModel(entity);
    }
}
