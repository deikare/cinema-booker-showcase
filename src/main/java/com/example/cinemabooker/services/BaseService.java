package com.example.cinemabooker.services;

import com.example.cinemabooker.services.interfaces.EntityInterface;
import com.example.cinemabooker.services.exceptions.IdAlreadyDefinedException;
import com.example.cinemabooker.services.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseService<T extends EntityInterface> {
    protected final JpaRepository<T, String> repository;
    protected final Logger logger;

    public BaseService(JpaRepository<T, String> repository, Logger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    public T create(T entity) throws IdAlreadyDefinedException {
        if (entity.getId() != null) {
            String msg = "Id of entity has been set";
            logger.info(msg);
            throw new IdAlreadyDefinedException(msg);
        }
        return save(entity);
    }

    public T save(T entity) {
        T result = repository.save(entity);
        logger.info("Successfully saved entity " + result);
        return result;
    }

    public void delete(String id) throws NotFoundException {
        repository.delete(find(id));
    }

    public Page<T> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<T> result = repository.findAll(pageable);
        logger.info("Found page of entities: " + result);
        return result;
    }

    public T find(String id) throws NotFoundException {
        return repository.findById(id).map(t -> {
                    logger.info("Successfully found entity{id=" + id + "}");
                    return t;
                })
                .orElseThrow(() -> {
                    String msg = "Entity{id=" + id + "} not found";
                    logger.info(msg);
                    return new NotFoundException(msg);
                });
    }

    public List<T> findAll() {
        List<T> result = repository.findAll();
        logger.info("Found list of entities, size=" + result.size());
        return result;
    }
}
