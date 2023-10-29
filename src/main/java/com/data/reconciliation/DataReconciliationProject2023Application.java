package com.data.reconciliation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableScheduling
public class DataReconciliationProject2023Application {

	private static final Logger log = LoggerFactory.getLogger(DataReconciliationProject2023Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DataReconciliationProject2023Application.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
