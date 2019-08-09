package com.sapient.football.league.rest;

import static com.sapient.football.league.util.TestUtils.hitAndGetContents;
import static com.sapient.football.league.util.TestUtils.verifyJsons;
import static com.sapient.football.league.util.TestUtils.verifyLinks;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.common.collect.Lists;
import com.sapient.football.league.service.FootballLeagueService;
import com.sapient.football.league.util.LinkDetail;
import com.sapient.football.league.util.TestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTest {
	private static final List<List<LinkDetail>> EXPECTED_LINKS = Arrays.asList(
																			Lists.newArrayList(
																				new LinkDetail("teams", "/competitions/177/teams"), 
																				new LinkDetail("standings", "/competitions/177/standings")
																			)); 
	
	@SuppressWarnings("unchecked")
	private static final List<List<LinkDetail>> EXPECTED_LINKS_FOR_COMPETITIONS = Lists.newArrayList(
																			Lists.newArrayList(
																				new LinkDetail("competitions", "/countries/46")
																			),
																			Lists.newArrayList(
																					new LinkDetail("competitions", "/countries/124")
																			)); 
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FootballLeagueService service;

	@Test
	public void shouldBeAbleToFetchAllCountries() throws Exception {
		List<Map<String, String>> allCountries = TestUtils.allCountries();
		when(service.getAllCountries()).thenReturn(allCountries);

		String action = hitAndGetContents("/countries/", mockMvc);

		verifyJsons(action, allCountries);
		verifyLinks(action, EXPECTED_LINKS_FOR_COMPETITIONS);
	}
	 
	@Test
	public void shouldBeAbleToFetchAllCompetitionsForACountry() throws Exception {
		List<Map<String, String>> competitionsForCountry = TestUtils.competionsForCountry();
		when(service.getCompetitionsForCountry("1")).thenReturn(competitionsForCountry);

		String action = hitAndGetContents("/countries/1", mockMvc);

		verifyJsons(action, competitionsForCountry);
		verifyLinks(action, EXPECTED_LINKS);
	}
}