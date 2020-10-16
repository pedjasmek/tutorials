package com.baeldung.spring.cloud.eureka.client;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case org.apache.http.HttpStatus.SC_BAD_REQUEST:
		{
			try {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()),
						IOUtils.toString(response.body().asReader()));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		case org.apache.http.HttpStatus.SC_FORBIDDEN: {
			try {
				return new AccessDeniedException(response.body() == null ? HttpStatus.FORBIDDEN.getReasonPhrase()
						: IOUtils.toString(response.body().asReader()));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		default:
			return new Exception(response.reason());
		}
	}

}