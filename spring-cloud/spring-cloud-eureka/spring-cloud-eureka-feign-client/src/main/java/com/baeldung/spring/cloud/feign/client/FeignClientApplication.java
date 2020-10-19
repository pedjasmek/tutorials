package com.baeldung.spring.cloud.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@SpringBootApplication
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
//@EnableResourceServer
//@EnableCaching
@RestController
public class FeignClientApplication {
	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private CloudEurekaClient cloudEurekaClient;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private OAuth2Client oauth2Client;

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}

	@RequestMapping("/get-greeting")
	@HystrixCommand(commandKey = "getGreeting", fallbackMethod = "getDefaultGreeting", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") }, ignoreExceptions = {
					AccessDeniedException.class, ResponseStatusException.class })
	public String getGreeting(@RequestHeader(name = "Authorization") String authorization) {
		return cloudEurekaClient.getGreeting(authorization);
	}

	@HystrixCommand(commandKey = "addAuthorityForUser", fallbackMethod = "addAuthorityFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") }, ignoreExceptions = {
					AccessDeniedException.class, ResponseStatusException.class })
	@PostMapping("/addAuthority/{username}/{authority}")
	public String addAuthorityForUser(@RequestHeader(name = "Authorization") String authorization,
			@PathVariable String username, @PathVariable String authority) {
		return cloudEurekaClient.addAuthorityForUser(authorization, username, authority);
	}

	@PostMapping("/token")
	public String getAccessToken(@RequestParam(name = "grant_type") String grantType,
			@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
			@RequestHeader(name = "Authorization") String authHeader) {
		ResponseEntity<OAuth2AccessToken> tokenResponse = oauth2Client.getToken(grantType, username, password,
				authHeader);
		return tokenResponse.getBody().getValue();
	}

	protected String addAuthorityFallback(@RequestHeader(name = "Authorization") String authorization,
			@PathVariable String username, @PathVariable String authority) {
		return "Authority " + authority + " for user " + username + " not added." + "\nCheck service availability!";
	}

	protected String getDefaultGreeting(@RequestHeader(name = "Authorization") String authorization) {
		return "DEFAULT GREETING";
	}
}
