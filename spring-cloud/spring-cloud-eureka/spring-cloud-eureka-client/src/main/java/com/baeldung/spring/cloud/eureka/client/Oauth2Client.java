package com.baeldung.spring.cloud.eureka.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-eureka-oauth2")
public interface Oauth2Client {

	@RequestMapping("/client/details")
	public CustomUserDetails getClientDetails(@RequestParam(name = "username") String username);
	
	@PostMapping("/authorities/add/{username}/{authority}")
	public String addAuthorityForUser(@RequestHeader(name = "Authorization") String authorization, //
									  @PathVariable(name = "username") String username,//
									  @PathVariable(name = "authority") String authority); //
	
}
