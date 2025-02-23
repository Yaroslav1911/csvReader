package com.testcase.csvReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;

@SpringBootApplication
@Cacheable
public class CsvReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvReaderApplication.class, args);
	}

}
