package com.baeldung.spring.cloud.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@SpringBootApplication
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
@Controller
public class FeignClientApplication {
	@Autowired
	private GreetingClient greetingClient;

	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(FeignClientApplication.class, args);
	}

	@RequestMapping("/get-greeting")
	@HystrixCommand(commandKey = "getGreeting", fallbackMethod = "getDefaultGreeting", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public String getGreeting(Model model) {
		model.addAttribute("greeting", greetingClient.getGreeting());
		return "greeting-view";
	}

	public String getDefaultGreeting(Model model) {
		model.addAttribute("greeting", "DEFAULT GREETING");
		return "greeting-view";
	}
}
