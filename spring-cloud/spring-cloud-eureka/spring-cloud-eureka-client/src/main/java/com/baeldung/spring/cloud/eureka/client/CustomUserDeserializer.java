package com.baeldung.spring.cloud.eureka.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class CustomUserDeserializer extends JsonDeserializer<CustomUserDetails> {

	@Override
	public CustomUserDetails deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jp.getCodec();
	    JsonNode node = oc.readTree(jp);
	    String username = node.get("username").asText();
	    String password = node.get("password").asText();
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, String>> authorities = mapper.convertValue(node.get("authorities"), ArrayList.class);
		List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.get("authority"))).collect(Collectors.toList());
		CustomUserDetails user = new CustomUserDetails(username, password, grantedAuthorities);
		return user;
	}

}
