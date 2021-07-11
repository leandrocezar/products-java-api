package io.github.leandrocezar.productsjavaapi.util.converter;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.github.leandrocezar.productsjavaapi.wrapper.ResponseWrapper;

public class ConverterToWrapper<T extends ResponseWrapper, S> {
    
    private Supplier<T> wrapperType; 
    
    public ConverterToWrapper(Supplier<T> wrapperType) {
	this.wrapperType = wrapperType;
    }
    
    public Iterable<T> toList(Iterable<S> result) {
	Iterable<T> retorno = null;
	
	retorno = StreamSupport.stream(result.spliterator(), false)
		      .map(p-> convert(p))
		      .collect(Collectors.toList());
	return retorno;
    }
    
    @SuppressWarnings("unchecked")
    public T convert(S entity) { 
	return (T) wrapperType.get().convert(entity);
    }

    
}
