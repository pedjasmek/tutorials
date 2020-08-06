package com.baeldung.spring.cloud.eureka.client;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomUserDeserializer.class)
public class CustomUserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
