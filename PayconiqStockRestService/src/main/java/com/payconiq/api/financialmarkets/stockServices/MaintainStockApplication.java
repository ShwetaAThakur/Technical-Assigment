package com.payconiq.api.financialmarkets.stockServices;

/**
 *  This is Spring boot application for a Stock Rest Service. 
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.payconiq.api.financialmarkets.stockServices.respository")
public class MaintainStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaintainStockApplication.class, args);
	}
}
