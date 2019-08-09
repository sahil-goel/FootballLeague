package com.sapient.football.league.rest;

import static com.sapient.football.league.util.ControllerUtils.hateoasResource;
import static com.sapient.football.league.util.ControllerUtils.*;
import static com.sapient.football.league.util.ControllerUtils.linkToTeamsInCompetition;

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

@Api(value="CountryController", description="REST urls to get information about participating countries.")
@RestController
@RequestMapping("/countries")
public class CountryController {

	@Autowired
	private FootballLeagueService footballLeagueService;

	@ApiOperation(value = "Get all participating countries", response = List.class, notes="This is a HATEOAS url that also provide links to various competitions that country is participating in")
	@GetMapping(value = "/")
	public ResponseEntity<List<Results<String>>> getCountries() {
		List<Map<String, String>> countries = footballLeagueService.getAllCountries();
		return ResponseEntity.ok(hateoasResource(countries, linkToCompetitionsForCountry()));
	}
	
	@ApiOperation(value = "Get all competitions where country is participating", response = List.class)
	@GetMapping(value = "/{countryId}")
	public ResponseEntity<List<Results<String>>> getCompetitionsForCountry(@PathVariable("countryId") String id) {
		List<Map<String, String>> competitions = footballLeagueService.getCompetitionsForCountry(id);
		if (CollectionUtils.isEmpty(competitions)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ControllerUtils.<String>emptyResult("No competitions found for country Id " + id));
		}
		return ResponseEntity.ok(hateoasResource(competitions , linkToTeamsInCompetition(), linkToStandingsInCompetition()));
	}
}
