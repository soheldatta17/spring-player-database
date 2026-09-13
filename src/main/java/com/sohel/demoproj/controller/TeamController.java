package com.sohel.demoproj.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sohel.demoproj.service.TeamService;
import com.sohel.demoproj.entity.Player;

@RestController
@RequestMapping("/team/players")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Player> getPlayers() {
        return teamService.getPlayers();
    }

    @GetMapping("/{email}")
    public Player getByEmail(@PathVariable String email) {
        return teamService.getByEmail(email);
    }

    @PutMapping("/{email}")
    public String updatePlayer(@PathVariable String email, @RequestBody Player newPlayerData) {
        if (!teamService.playerExists(email)) {
            return "Player with email " + email + " does not exist";
        }
        teamService.updatePlayer(email, newPlayerData);
        return "Player updated successfully";
    }

    @PostMapping
    public String addPlayer(@RequestBody Player player) {
        String email = player.getEmail();
        if (email == null || email.isBlank()) {
            return "Email is required";
        }
        if (teamService.playerExists(email)) {
            return "Player already exists with email: " + email;
        }
        // id is not provided by the user; MongoDB will auto-generate it upon saving
        player.setId(null);
        teamService.addPlayer(player);
        return "Player added successfully with system-generated ID: " + player.getId();
    }

    @DeleteMapping("/{email}")
    public String removePlayer(@PathVariable String email) {
        if (!teamService.playerExists(email)) {
            return "Player with email " + email + " does not exist";
        }
        teamService.removePlayer(email);
        return "Player removed successfully";
    }

    @DeleteMapping("/all")
    public String removeAllPlayers() {
        teamService.removeAllPlayers();
        return "All players removed successfully";
    }

}
