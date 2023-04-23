package com.example.cinemabooker.services;

import org.springframework.data.jpa.repository.JpaRepository;

public class BaseServiceWithUpdate<T extends EntityUpdateInterface<T>> extends BaseService<T> {
    public BaseServiceWithUpdate(JpaRepository<T, String> repository) {
        super(repository);
    }

    void update(String id, T newEntity) {
        T entity = repository.findById(id)
                .map(t -> { //update if exists
                    t.update(newEntity);
                    return t;
                })
                .orElseGet(() -> { //create otherwise
                    newEntity.setId(id);
                    return newEntity;
                });
        save(entity);
    }
}
