package com.sapient.football.league.rest;

import static com.sapient.football.league.util.TestUtils.allCompetitions;
import static com.sapient.football.league.util.TestUtils.hitAndGetContents;
import static com.sapient.football.league.util.TestUtils.standingsForCompetition;
import static com.sapient.football.league.util.TestUtils.teamsInCompetition;
import static com.sapient.football.league.util.TestUtils.verifyJsons;
import static com.sapient.football.league.util.TestUtils.verifyLinks;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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
import com.jayway.jsonpath.JsonPath;
import com.sapient.football.league.service.FootballLeagueService;
import com.sapient.football.league.util.LinkDetail;

@RunWith(SpringRunner.class)
@WebMvcTest(CompetitionController.class)
public class CompetitionControllerTest {
	@SuppressWarnings("unchecked")
	private static final List<List<LinkDetail>> EXPECTED_LINKS = Lists.newArrayList(
																			Lists.newArrayList(
																				new LinkDetail("teams", "/competitions/177/teams"), 
																				new LinkDetail("standings", "/competitions/177/standings")
																			),
																			Lists.newArrayList(
																				new LinkDetail("teams", "/competitions/423/teams"), 
																				new LinkDetail("standings", "/competitions/423/standings")
																			)); 
	
	@SuppressWarnings("unchecked")
	private static final List<List<LinkDetail>> EXPECTED_LINKS_FOR_TEAMS = Lists.newArrayList(
																			Lists.newArrayList(
																				new LinkDetail("self", "/team/3028")
																			),
																			Lists.newArrayList(
																					new LinkDetail("self", "/team/3029")
																			),
																			Lists.newArrayList(
																					new LinkDetail("self", "/team/3032")
																			)); 
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FootballLeagueService service;

	@Test
	public void shouldBeAbleToFetchAllCompetitions() throws Exception {
		List<Map<String, String>> allCompetitions = allCompetitions();
		when(service.getAllCompetitions()).thenReturn(allCompetitions);

		String action = hitAndGetContents("/competitions/", mockMvc);

		verifyJsons(action, allCompetitions);
		verifyLinks(action, EXPECTED_LINKS);
	}
	 
	@Test
	public void shouldBeAbleToFetchAllTeamsInCompetition() throws Exception {
		List<Map<String, Object>> teamsInCompetition = teamsInCompetition();
		when(service.getAllTeamsInACompetition("1")).thenReturn(teamsInCompetition);

		String action = hitAndGetContents("/competitions/1/teams", mockMvc);

		verifyJsons(action, teamsInCompetition);
		verifyLinks(action, EXPECTED_LINKS_FOR_TEAMS);
	}
	
	@Test
	public void shouldReturnNotFoundIfNoTeamsFoundForCometitionId() throws Exception {
		String content = hitAndGetContents("/competitions/2/teams", mockMvc, 404);
		String message = JsonPath.read(content, "$[0].results.message");
		assertThat(message, equalTo("No teams found for Competition Id 2"));
	}
	
	@Test
	public void shouldRethrowWhateverIsThrownByUnderlyingApi() throws Exception {
		when(service.getAllTeamsInACompetition("2")).thenThrow(new RuntimeException("some message"));
		String content = hitAndGetContents("/competitions/2/teams", mockMvc, 503);
		String message = JsonPath.read(content, "$.details[0]");
		assertThat(message, equalTo("some message"));
	}
	
	@Test
	public void shouldBeAbleToFetchAllStandingsForCompetition() throws Exception {
		List<Map<String, String>> standingsForCompetition = standingsForCompetition();
		when(service.getAllStandingsInACompetition("1")).thenReturn(standingsForCompetition);

		String action = hitAndGetContents("/competitions/1/standings", mockMvc);

		verifyJsons(action, standingsForCompetition);
	}
}