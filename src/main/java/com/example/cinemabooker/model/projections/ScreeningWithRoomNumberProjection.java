package com.example.cinemabooker.model.projections;

import com.example.cinemabooker.model.Screening;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.Instant;

@Projection(name = "roomNumber", types = {Screening.class})
public interface ScreeningWithRoomNumberProjection {
    @Value("#{target.room.roomNumber}") //todo https://stackoverflow.com/questions/29376090/how-to-apply-spring-data-projections-in-a-spring-mvc-controllers/67971287#67971287
    long getRoomNumber();

    @Value("#{target.screeningTime}")
    Instant getTimestamp();

    @Value("test")
    String getTest();
}
