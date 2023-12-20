package com.solvd.zoo.dao;

import java.util.List;

public interface IBaseDAO<T> {
    T getEntityById(long id);

    void saveEntity(T entity);

    void updateEntity(T entity);

    void removeEntity(T entity);
    List<T> getEntities();
}
