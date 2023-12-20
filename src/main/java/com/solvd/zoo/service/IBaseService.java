package com.solvd.zoo.service;

import java.util.List;

public interface IBaseService<T> {
    T getEntityById(long id);

    void saveEntity(T entity);

    void updateEntity(T entity);

    void removeEntity(T entity);
    List<T> getEntities();
}
