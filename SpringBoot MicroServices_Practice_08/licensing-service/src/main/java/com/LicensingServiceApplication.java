package com;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.events.models.OrganizationChangeModel;
import com.utils.UserContextInterceptor;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableBinding(Sink.class)
public class LicensingServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(LicensingServiceApplication.class);
	
	@StreamListener(Sink.INPUT)
	public void loggerSink(OrganizationChangeModel orgChange) {
		logger.debug("Receveid an event for organization id {}" , orgChange.getOrganizationId() );
	}
	
  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate(){
	  RestTemplate template = new RestTemplate();
      List interceptors = template.getInterceptors();
      if (interceptors==null){
          template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
      }
      else{
          interceptors.add(new UserContextInterceptor());
          template.setInterceptors(interceptors);
      }

      return template;
  }

  public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
  }
}
