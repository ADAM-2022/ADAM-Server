package com.adam.server;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/")})
@ConfigurationPropertiesScan
public class AdamServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AdamServerApplication.class, args);
  }
}
