package com.sohel.demoproj.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sohel.demoproj.service.TeamService;
import com.sohel.demoproj.entity.Player;

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
    public String addPlayers(@RequestBody List<Player> players) {
        for (Player p : players) {
            String name = p.getName();
            if (teamService.playerExists(name)) {
                return "Player already exists: " + name;
            }
            teamService.addPlayer(name);
        }
        return "Players added successfully";
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