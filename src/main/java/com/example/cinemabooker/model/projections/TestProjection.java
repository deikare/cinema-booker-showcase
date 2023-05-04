package com.example.cinemabooker.model.projections;

import com.example.cinemabooker.model.Cinema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "test", types = {Cinema.class})
public interface TestProjection {
    @Value("test_#{target.id}")
    String getTest();
}
