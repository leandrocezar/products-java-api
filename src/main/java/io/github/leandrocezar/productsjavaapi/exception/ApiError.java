package io.github.leandrocezar.productsjavaapi.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
public final class ApiError {

    @JsonProperty("status_code")
    private HttpStatus statusCode;
    @Getter
    private String message;
    
    public int getStatusCode() { 
	return statusCode.value();
    }
}
