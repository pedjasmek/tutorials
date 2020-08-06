package com.baeldung.spring.cloud.eureka.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-eureka-oauth2")
public interface UserDetailsClient {

	@RequestMapping("/client/details")
	public CustomUserDetails getClientDetails(@RequestParam(name = "username") String username);
	
}
