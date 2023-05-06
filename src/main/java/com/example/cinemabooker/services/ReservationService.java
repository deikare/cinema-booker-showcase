package com.example.cinemabooker.services;

import com.example.cinemabooker.model.Reservation;
import com.example.cinemabooker.repositories.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends BaseService<Reservation, ReservationRepository> {
    public ReservationService(ReservationRepository repository) {
        super(repository, LoggerFactory.getLogger(ReservationService.class));
    }
}
