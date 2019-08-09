package com.sapient.football.league.feign;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface FootballApiClient {

	@GetMapping(path= "?action=get_standings")
	List<Map<String, String>> getAllStandingsInACompetition(@RequestParam("APIkey") String apiKey, @RequestParam("league_id") String competitionId);

	@GetMapping(path= "?action=get_leagues")
	List<Map<String, String>> getAllCompetitions(@RequestParam("APIkey") String apiKey);

	@GetMapping(path= "?action=get_leagues")
	List<Map<String, String>> getCompetitionsForCountry(@RequestParam("APIkey") String apiKey, @RequestParam("country_id") String countryId);

	@GetMapping(path= "?action=get_teams")
	List<Map<String, Object>> getAllTeamsInACompetition(@RequestParam("APIkey") String apiKey, @RequestParam("league_id") String competitionId);

	@GetMapping(path= "?action=get_countries")
	List<Map<String, String>> getAllCountries(@RequestParam("APIkey") String apiKey);

	@GetMapping(path= "?action=get_players")
	List<Map<String, String>> getPlayerDetails(@RequestParam("APIkey") String apiKey, @RequestParam("player_id") String playerId);

	@GetMapping(path= "?action=get_teams")
	List<Map<String, Object>> getTeam(@RequestParam("APIkey") String apiKey, @RequestParam("team_id") String teamId);
}
