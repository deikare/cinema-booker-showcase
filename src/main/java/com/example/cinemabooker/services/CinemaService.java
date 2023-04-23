package com.example.cinemabooker.services;

import com.example.cinemabooker.model.Cinema;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CinemaService extends BaseService<Cinema> {
    public CinemaService(JpaRepository<Cinema, String> repository) {
        super(repository, LoggerFactory.getLogger(CinemaService.class));
    }
}
