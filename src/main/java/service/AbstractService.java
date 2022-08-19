package service;

import java.util.List;

import controller.util.exception.impl.InternalErrorException;

public interface AbstractService<K, T> {

    T getById(K id) throws Exception;

    List<T> getAll();

    T create(T entity) throws Exception;

    T update(T entity) throws Exception;

    void delete(K id) throws InternalErrorException;

    }
