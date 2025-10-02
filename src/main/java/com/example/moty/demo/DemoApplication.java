package com.example.moty.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;

@SpringBootApplication(exclude = {GrpcServerSecurityAutoConfiguration.class})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
