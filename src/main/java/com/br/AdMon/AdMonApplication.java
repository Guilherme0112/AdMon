package com.br.AdMon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdMonApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdMonApplication.class, args);
	}

}
