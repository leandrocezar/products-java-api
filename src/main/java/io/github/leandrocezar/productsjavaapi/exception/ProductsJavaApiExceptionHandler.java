package io.github.leandrocezar.productsjavaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductsJavaApiExceptionHandler<T> {

    @ExceptionHandler(value = { RecordNotFoundException.class })
    protected ResponseEntity<ApiError> handleProductNotFoundException(RecordNotFoundException exception) {

	ApiError response = new ApiError(HttpStatus.NOT_FOUND, "Record not found in the database");

	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

	ApiError response = new ApiError(HttpStatus.BAD_REQUEST, "Invalid request! Check required fields.");

	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<ApiError> handleException(Exception exception) {

	ApiError response = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Ooops! An internal server error ocurred.");

	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
