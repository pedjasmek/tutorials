package com.baeldung.spring.cloud.feign.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ResponseStatusException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, ((ResponseStatusException) ex).getReason(), new HttpHeaders(),
				((ResponseStatusException) ex).getStatus(), request);
	}
}