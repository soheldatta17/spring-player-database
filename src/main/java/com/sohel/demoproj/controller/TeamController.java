package com.sohel.demoproj.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sohel.demoproj.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/players")
    public List<String> getPlayers() {
        return teamService.getPlayers();
    }

    @PostMapping("/players")
    public String addPlayer(@RequestBody String player) {
        if (teamService.playerExists(player)) {
            return "Player already exists";
        }
        teamService.addPlayer(player);
        return "Player added successfully";
    }

    @DeleteMapping("/players/{player}")
    public String removePlayer(@PathVariable String player) {
        if (!teamService.playerExists(player)) {
            return "Player does not exist";
        }
        teamService.removePlayer(player);
        return "Player removed successfully";
    }
}