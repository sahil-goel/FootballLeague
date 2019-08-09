package com.sapient.football.league.util;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.sapient.football.league.model.Results;
import com.sapient.football.league.rest.CompetitionController;
import com.sapient.football.league.rest.CountryController;
import com.sapient.football.league.rest.TeamController;

public class ControllerUtils {
	
	public static LinkBuilder<String> linkToTeamsInCompetition() {
		LinkBuilder<String> linkBuilder = (x) -> linkTo(methodOn(CompetitionController.class).getTeamsInACompetition(x.get("league_id"))).withRel("teams");
		return linkBuilder;
	}
	
	public static LinkBuilder<Object> linkToTeamDetail() {
		LinkBuilder<Object> linkBuilder = (x) -> linkTo(methodOn(TeamController.class).getTeam((String)x.get("team_key"))).withRel("self");
		return linkBuilder;
	}
	
	public static LinkBuilder<String> linkToStandingsInCompetition() {
		LinkBuilder<String> linkBuilder = (x) -> linkTo(methodOn(CompetitionController.class).getStandingsInACompetition(x.get("league_id"))).withRel("standings");
		return linkBuilder;
	}
	
	public static LinkBuilder<String> linkToCompetitionsForCountry() {
		LinkBuilder<String> linkBuilder = (x) -> linkTo(methodOn(CountryController.class).getCompetitionsForCountry(x.get("country_id"))).withRel("competitions");
		return linkBuilder;
	}
	
	@SafeVarargs
	public static <K> List<Results<K>> hateoasResource(List<Map<String, K>> data, LinkBuilder<K>... linkBuilders) {
		List<Results<K>> results = new ArrayList<>();
		for (Map<String, K> map : data) {
			results.add(new Results<>(map, linkBuilders));
		}
		return results;
	}
	
	public static <K> List<Results<K>> emptyResult(K message) {
		List<Results<K>> results = new ArrayList<>();
		results.add(new Results<>(ImmutableMap.of("message", message)));
		return results;
	}
}
