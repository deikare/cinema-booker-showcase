package com.example.cinemabooker.services;

import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseServiceWithUpdate<T extends EntityUpdateInterface<T>> extends BaseService<T> {
    public BaseServiceWithUpdate(JpaRepository<T, String> repository, Logger logger) {
        super(repository, logger);
    }

    void update(String id, T newEntity) {
        T entity = repository.findById(id)
                .map(t -> { //update if exists
                    t.update(newEntity);
                    logger.info("Entity updated, current state: " + t);
                    return t;
                })
                .orElseGet(() -> { //create otherwise
                    newEntity.setId(id);
                    logger.info("Entity not found, new entity created");
                    return newEntity;
                });
        save(entity);
    }
}
