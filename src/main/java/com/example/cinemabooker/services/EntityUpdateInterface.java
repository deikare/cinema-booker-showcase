package com.example.cinemabooker.services;

public interface EntityUpdateInterface<T> {
    void setId(String id);
    void update(T entity);
}
