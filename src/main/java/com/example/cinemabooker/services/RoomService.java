package com.example.cinemabooker.services;

import com.example.cinemabooker.model.Room;
import com.example.cinemabooker.repositories.RoomRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends BaseService<Room, RoomRepository> {
    public RoomService(RoomRepository repository) {
        super(repository, LoggerFactory.getLogger(RoomService.class));
    }
}
