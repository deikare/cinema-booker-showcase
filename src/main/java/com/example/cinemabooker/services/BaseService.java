package com.example.cinemabooker.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class BaseService<T> {
    JpaRepository<T, String> repository;

    public BaseService(JpaRepository<T, String> repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void delete(String id) throws NotFoundException {
        repository.delete(find(id));
    }

    public Page<T> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public T find(String id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Entity{" + id + "} not found"));
    }

    public List<T> findAll() {
        return repository.findAll();
    }
}
