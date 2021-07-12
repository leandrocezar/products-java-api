package io.github.leandrocezar.productsjavaapi.wrapper;

import org.modelmapper.ModelMapper;

/**
 * Generic response json
 *
 *
 * @author Leandro Moreira Cezar
 *
 * @param <T>
 */
public class ResponseWrapper<T> {
    /***
     * Return objet wrapper with the entity values
     * 
     * @author Leandro Moreira Cezar
     *
     * @param entity
     * @return
     */
    public ResponseWrapper<T> convert(T entity) {
	new ModelMapper().map(entity, this);
	return this;
    }    
}
