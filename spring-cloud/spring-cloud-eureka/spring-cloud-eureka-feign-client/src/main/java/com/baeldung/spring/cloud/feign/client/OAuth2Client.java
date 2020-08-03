package com.baeldung.spring.cloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-eureka-oauth2")
public interface OAuth2Client {

	@PostMapping(value = "/oauth/token",consumes = { "application/x-www-form-urlencoded" })
	ResponseEntity<OAuth2AccessToken> getToken(
			@RequestParam(name = "grant_type") String grantType, //
			@RequestParam(name = "username") String username, //
			@RequestParam(name = "password") String password, //
			@RequestHeader(name = "Authorization") String authHeader); //

}
