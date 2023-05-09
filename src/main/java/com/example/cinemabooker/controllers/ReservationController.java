package com.example.cinemabooker.controllers;

import com.example.cinemabooker.controllers.requests.ReservationRequest;
import com.example.cinemabooker.controllers.representation.assemblers.ReservationModelAssembler;
import com.example.cinemabooker.model.Reservation;
import com.example.cinemabooker.repositories.ReservationRepository;
import com.example.cinemabooker.services.ReservationService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends BaseControllerWithGetOne<Reservation, ReservationRepository, ReservationService, ReservationModelAssembler> {
    protected ReservationController(ReservationService service, PagedResourcesAssembler<Reservation> pagedResourcesAssembler, ReservationModelAssembler modelAssembler) {
        super(LoggerFactory.getLogger(ReservationController.class), service, pagedResourcesAssembler, modelAssembler);
    }

    @PostMapping
    public ResponseEntity<?> newEntity(@RequestBody @Valid ReservationRequest reservationRequest) {
        logger.info("Received request post entity" + reservationRequest);

        return null;


//        EntityModel<Reservation> entityModel = modelAssembler.toModel(service.create(reservationForm));

//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(entityModel);
    }
}
