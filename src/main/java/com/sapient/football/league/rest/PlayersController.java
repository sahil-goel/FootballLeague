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

@Api(value="PlayersController", description="REST urls to get information about player.")
@RestController
@RequestMapping(value = "/players")
public class PlayersController {
	
	@Autowired
	private FootballLeagueService footballLeagueService;
	
	@ApiOperation(value = "Get player detail", response = List.class)
	@GetMapping(value = "/{player_id}")
	public ResponseEntity<List<Results<String>>> getPlayerDetails(@PathVariable("player_id") String playerId) {
		List<Map<String, String>> players = footballLeagueService.getPlayerDetails(playerId);
		if (CollectionUtils.isEmpty(players)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ControllerUtils.<String>emptyResult("No player found for player id " + players));
		}
		return ResponseEntity.ok(hateoasResource(players));
	}
}
