package com.nc.project.dao;

import java.io.Serializable;
import java.util.Optional;

public interface GenericDao<T, ID extends Serializable> {

    T create(T entity);

    Optional<T> findById(ID id);

    T update(T entity);

    void delete(ID id);
}
