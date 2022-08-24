package dao.interfaces;

import java.util.List;

import controller.util.exception.impl.DaoException;

public interface AbstractDao<K, T> {

    /**
     * Method find Entity object in the data source by id
     * 
     * @param id Object id to be get
     * @return Object
     * @throws DaoException
     */
    T get(K id) throws DaoException;

    /**
     * Method finds all Entity objects in the data source
     * 
     * @return List of Objects
     * @throws DaoException
     */
    List<T> getAll() throws DaoException;

    /**
     * Method is used to create Object in the data source
     * 
     * @param entity to be saved
     * @return created Object
     * @throws DaoException
     */
    T create(T entity) throws DaoException;

    /**
     * Method is used to update Object in the data source
     * 
     * @param entity to be updated
     * @return updated Object
     * @throws DaoException
     */
    T update(T entity) throws DaoException;

    /**
     * Method is used for "soft" deleting Object in the data source
     * 
     * @param id Object id to be "soft" deleted
     * @return boolean Object deleted
     * @throws DaoException
     */
    boolean delete(K id) throws DaoException;

}
