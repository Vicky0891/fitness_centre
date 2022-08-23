package service;

import java.util.List;

public interface AbstractService<K, T> {

    T getById(K id) throws Exception;

    List<T> getAll() throws Exception;

    T create(T entity) throws Exception;

    T update(T entity) throws Exception;

    void delete(K id) throws Exception;

}
