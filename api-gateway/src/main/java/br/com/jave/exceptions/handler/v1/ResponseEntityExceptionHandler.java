package br.com.jave.exceptions.handler.v1;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import br.com.jave.exceptions.v1.ExceptionResponse;
import br.com.jave.exceptions.v1.InvalidJwtAuthenticationException;

@ControllerAdvice
@RestController
public class ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, ServerHttpRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), "uri=" + request.getURI().getPath());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationExceptions(Exception exception, ServerHttpRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), "uri=" + request.getURI().getPath());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}

}
