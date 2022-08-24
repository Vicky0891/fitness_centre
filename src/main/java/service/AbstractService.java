package service;

import java.util.List;

public interface AbstractService<K, T> {

    /**
     * Method to get object from Dao by id
     * 
     * @param id Object id to be get
     * @return Object
     * @throws Exception
     */
    T getById(K id) throws Exception;

    /**
     * Method finds all objects from Dao
     * 
     * @return List of Objects
     * @throws Exception
     */
    List<T> getAll() throws Exception;

    /**
     * Method is used to get Dto object and pass it to create in the data source
     * 
     * @param entity to be create
     * @return created Dto object
     * @throws Exception
     */
    T create(T entity) throws Exception;

    /**
     * Method is used to get Dto object and pass it to update in the data source
     * 
     * @param entity to be update
     * @return updated Dto object
     * @throws Exception
     */
    T update(T entity) throws Exception;

    /**
     * Method is used to delete object by id
     * 
     * @param id Object id to delete
     * @throws Exception
     */
    void delete(K id) throws Exception;

}
