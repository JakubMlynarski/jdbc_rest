package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);
	public static void main(String args[]) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {

		log.info("Creating tables");
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");


		jdbcTemplate.execute(
				"CREATE TABLE customers(" +
				"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), index INT)");

		List<Object[]> splitUpNames = Arrays.asList("Jakub Młynarski 63998", "Robert Nowak 46637", "Adam Kowalski 23678").stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());

		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name, index) VALUES (?, ?, ?)", splitUpNames);
	}
}
