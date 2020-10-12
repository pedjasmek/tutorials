package com.baeldung.spring.cloud.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@SpringBootApplication
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
//@EnableResourceServer
//@EnableCaching
@Controller
public class FeignClientApplication {
	@Autowired
	private GreetingClient greetingClient;

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private OAuth2Client oauth2Client;

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}

	@RequestMapping("/get-greeting")
	@HystrixCommand(commandKey = "getGreeting", fallbackMethod = "getDefaultGreeting", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public String getGreeting(Model model) {
		String accessToken = getAccessToken("password", "tutorialspoint",
				"my-secret-key", "Basic dHV0b3JpYWxzcG9pbnQ6bXktc2VjcmV0LWtleQ==");
		model.addAttribute("greeting", greetingClient.getGreeting("Bearer " + accessToken));
		return "greeting-view";
	}

	public String getAccessToken(String grantType, String username, String password, String authHeader) {
		ResponseEntity<OAuth2AccessToken> tokenResponse = oauth2Client.getToken(grantType, username,
				password, authHeader);
		return tokenResponse.getBody().getValue();
	}

	public String getDefaultGreeting(Model model) {
		model.addAttribute("greeting", "DEFAULT GREETING");
		return "greeting-view";
	}
}
