package com.br.AdMon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
@EnableScheduling
public class AdMonApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();

		// MySQL
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Envio de e-mails
		System.setProperty("EMAIL", dotenv.get("EMAIL"));
		System.setProperty("SENHA", dotenv.get("SENHA"));

		// Roda o app
		SpringApplication.run(AdMonApplication.class, args);
	}
}
