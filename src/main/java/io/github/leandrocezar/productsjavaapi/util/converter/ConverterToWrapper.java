package io.github.leandrocezar.productsjavaapi.util.converter;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.github.leandrocezar.productsjavaapi.wrapper.ResponseWrapper;
/***
 * Class that convert <code>S</code> class to <code>T</code> object
 *
 *
 * @author Leandro Moreira Cezar
 *
 * @param <T>
 * @param <S>
 */
@SuppressWarnings("rawtypes")
public class ConverterToWrapper<T extends ResponseWrapper, S> {
    
    private Supplier<T> wrapperType; 
    
    public ConverterToWrapper(Supplier<T> wrapperType) {
	this.wrapperType = wrapperType;
    }
    
    /**
     * Conver a list of <code>S</code> class to a list of <code>T</code> ResponseWrapper class
     * 
     * @author Leandro Moreira Cezar
     *
     * @param result Iterable<S> 
     * @return Iterable<T>
     */
    public Iterable<T> toList(Iterable<S> result) {
	Iterable<T> retorno = null;
	
	retorno = StreamSupport.stream(result.spliterator(), false)
		      .map(p-> convert(p))
		      .collect(Collectors.toList());
	return retorno;
    }
    
    /***
     * Convert object S to T 
     * 
     * @author Leandro Moreira Cezar
     *
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    public T convert(S entity) { 
	return (T) wrapperType.get().convert(entity);
    }

    
}
