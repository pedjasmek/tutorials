package com.baeldung.spring.cloud.eureka.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private Oauth2Client userDetailsClient;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return (CustomUserDetails) userDetailsClient.getClientDetails(username);
	}

}
