package com.baeldung.spring.cloud.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("spring-cloud-eureka-client")
public interface CloudEurekaClient {
    @RequestMapping("/greeting")
    String getGreeting(@RequestHeader(name = "Authorization") String authorization);
    
    @PostMapping("/authorities/add/{username}/{authority}")
	String addAuthorityForUser(@RequestHeader(name = "Authorization") String authorization, //
									  @PathVariable(name = "username") String username,//
									  @PathVariable(name = "authority") String authority); //
}
