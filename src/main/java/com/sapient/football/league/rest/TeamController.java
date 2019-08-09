package com.sapient.football.league.rest;

import static com.sapient.football.league.util.ControllerUtils.hateoasResource;

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

@Api(value="TeamController", description="REST urls to get information about team.")
@RestController
@RequestMapping(value = "/team")
public class TeamController {
	@Autowired
	private FootballLeagueService footballLeagueService;
	
	@ApiOperation(value = "Get team detail", response = List.class)
	@GetMapping(value = "/{team_id}")
	public ResponseEntity<List<Results<Object>>> getTeam(@PathVariable("team_id") String teamId) {
		List<Map<String, Object>> team = footballLeagueService.getTeam(teamId);
		if (CollectionUtils.isEmpty(team)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ControllerUtils.<Object>emptyResult("No team found for team Id " + teamId));
		}
		return ResponseEntity.ok(hateoasResource(team));
	}
}
