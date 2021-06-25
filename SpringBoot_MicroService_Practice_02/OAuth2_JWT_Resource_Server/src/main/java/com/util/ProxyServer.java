package com.util;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProxyServer {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${resourceServer.client-jwkKeyUri}")
	private String uri;
	
	@Value("${resourceServer.client-id}")
	private String clintId;
	
	@Value("${resourceServer.client-secret}")
	private String clientSecret;
	
	public String getPublicKey() {
		
		HttpEntity<Object> requestEntity = new HttpEntity<>(createHeader());
			
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		
		try {
			root = mapper.readTree(response.getBody());
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		JsonNode value = root.path("value");

		return value.asText();
	}
	
	private HttpHeaders  createHeader() {	
		return new HttpHeaders() {
			private static final long serialVersionUID = 1L;
		{		
			Encoder encoder = Base64.getEncoder();
	         String auth = clintId + ":" + clientSecret;
	         byte[] encodedAuth = encoder.encode( 
	            auth.getBytes(Charset.forName("US-ASCII")) );
	         String authHeader = "Basic " + new String( encodedAuth );
	         set( "Authorization", authHeader );
	      }};
	}
}
