package com.sapient.football.league.rest;

import static com.sapient.football.league.util.ControllerUtils.hateoasResource;
import static com.sapient.football.league.util.ControllerUtils.linkToStandingsInCompetition;
import static com.sapient.football.league.util.ControllerUtils.linkToTeamDetail;
import static com.sapient.football.league.util.ControllerUtils.linkToTeamsInCompetition;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.football.league.model.Results;
import com.sapient.football.league.service.FootballLeagueService;
import com.sapient.football.league.util.ControllerUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="CompetitionController", description="REST urls to get information about competitions.")
@RestController
@RequestMapping(value="/competitions")
public class CompetitionController {
	
	@Autowired
	private FootballLeagueService footballLeagueService;
	
	
	@ApiOperation(value = "Get all competitions", response = List.class, notes="This is a HATEOAS url that also provide links to teams and standings for each competition")
	@GetMapping(value = "/")
	public ResponseEntity<List<Results<String>>> getCompetitions() {
		List<Map<String, String>> competitions = footballLeagueService.getAllCompetitions();
		return ok(hateoasResource(competitions , linkToTeamsInCompetition(), linkToStandingsInCompetition()));
	}
	
	@ApiOperation(value = "Get all teams in a competition", response = List.class, notes="This URL takes a competition Id as input parameter and return all teams in it. It also provides link to each team detail")
	@GetMapping(value = "/{competition_id}/teams")
	public ResponseEntity<List<Results<Object>>> getTeamsInACompetition(@PathVariable("competition_id") String competitionid) {
		List<Map<String, Object>> competitions = footballLeagueService.getAllTeamsInACompetition(competitionid);
		if (CollectionUtils.isEmpty(competitions)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ControllerUtils.<Object>emptyResult("No teams found for Competition Id " + competitionid));
		}
		return ok(hateoasResource(competitions, linkToTeamDetail()));
	}
	
	@ApiOperation(value = "Get all standings in a competition", response = List.class, notes="This URL takes a competition Id as input parameter and return all standings of it")
	@GetMapping(value = "/{competition_id}/standings")
	public ResponseEntity<List<Results<String>>> getStandingsInACompetition(@PathVariable("competition_id") String competitionid) {
		List<Map<String, String>> competitions = footballLeagueService.getAllStandingsInACompetition(competitionid);
		if (CollectionUtils.isEmpty(competitions)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ControllerUtils.<String>emptyResult("No standings found for Competition Id " + competitionid));
		}
		return ok(hateoasResource(competitions));
	}
}
