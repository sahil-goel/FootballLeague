package com.sapient.football.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FootballLeagueApplication {
	public static void main(String[] args) {
		SpringApplication.run(FootballLeagueApplication.class, args);
	}
}
