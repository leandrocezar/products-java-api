package io.github.leandrocezar.productsjavaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.leandrocezar.productsjavaapi.dto.response.ResponseError;
import lombok.extern.log4j.Log4j2;


/***
 * Global ExceptionHandler 
 *
 *
 * @author Leandro Moreira Cezar
 *
 * @param <T>
 */
@Log4j2
@ControllerAdvice
public class ProductsJavaApiExceptionHandler<T> {

    /**
     * Exception handling for Record not found in the database
     * 
     * @author Leandro Moreira Cezar
     *
     * @param exception
     * @return  <code>ResponseErro</code>
     */
    @ExceptionHandler(value = { RecordNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ResponseError> handleRecordNotFoundException(RecordNotFoundException exception) {

	log.error("Record not found in database", exception);
	
	ResponseError response = new ResponseError(HttpStatus.NOT_FOUND, "Record not found in the database");

	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /***
     * Exception handling for invalid request (request validation)
     * 
     * @author Leandro Moreira Cezar
     *
     * @param exception
     * @return <code>ResponseError</code>
     */
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

	log.error("Invalid json request");
	
	ResponseError response = new ResponseError(HttpStatus.BAD_REQUEST, "Invalid request! Check required fields.");

	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Exception handlinf for unmapped exceptions
     * 
     * @author Leandro Moreira Cezar
     *
     * @param exception
     * @return <code>ResponseError</code>
     */
    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ResponseError> handleException(Exception exception) {

	log.error("Internal Server Error");
	ResponseError response = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, "Ooops! An internal server error ocurred.");

	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
