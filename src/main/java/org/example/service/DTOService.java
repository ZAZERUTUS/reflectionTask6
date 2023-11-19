package org.example.service;

import java.util.List;

public interface DTOService<T> {
    T getById(int id);
    List<T> getAll();
    int save(T entity);
    boolean update(T entity);
    boolean delete(int id);
}
