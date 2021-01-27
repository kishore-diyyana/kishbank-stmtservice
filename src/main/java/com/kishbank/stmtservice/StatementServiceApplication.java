package com.kishbank.stmtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * kish bank receives monthly deliveries of customer statement records.
 * This information is delivered in JSON Format.
 * These records need to be validated.
 * @author Kishore Diyyana
 */
@SpringBootApplication
public class StatementServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatementServiceApplication.class, args);
	}
}
