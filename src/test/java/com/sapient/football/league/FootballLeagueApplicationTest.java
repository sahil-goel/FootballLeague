package com.sapient.football.league;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sapient.football.league.rest.CompetitionController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FootballLeagueApplicationTest {
	
	@Autowired
    private CompetitionController controller;
	
	@Test
	public void contextLoads() {
		assertThat(controller, notNullValue());
	}
}
