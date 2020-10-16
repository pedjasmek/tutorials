package com.baeldung.spring.cloud.eureka.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorities")
public class AuthoritiesRestController {

	@Autowired
	private Oauth2Client oauth2Client;

	@PreAuthorize("hasAuthority('ROLE_CUSTOM_ADMIN')")
	@PostMapping("/add/{username}/{authority}")
	public String addAuthorityForUser(@PathVariable("username") String username,
			@PathVariable("authority") String authority) {
		return oauth2Client.addAuthorityForUser(username, authority);
	}

}
