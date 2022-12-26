package com.ravel.votacaoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Entity;

@SpringBootApplication(scanBasePackages = "com.ravel.votacaoapi")
@EntityScan(basePackages = "com.ravel.votacaoapi")
public class VotacaoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoApiApplication.class, args);
	}

}
