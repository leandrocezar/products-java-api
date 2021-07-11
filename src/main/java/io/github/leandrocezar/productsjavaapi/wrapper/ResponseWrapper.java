package io.github.leandrocezar.productsjavaapi.wrapper;

import org.modelmapper.ModelMapper;

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
