package com.sohel.demoproj.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.sohel.demoproj.service.TeamService;
import com.sohel.demoproj.entity.Player;

@RestController
@RequestMapping("/team/players")
public class TeamController {

    private static final Logger log = LoggerFactory.getLogger(TeamController.class);
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Player> getPlayers() {
        log.info("API Call: GET /team/players - Fetching all players");
        return teamService.getPlayers();
    }

    @GetMapping("/{email}")
    public Player getByEmail(@PathVariable String email) {
        log.info("API Call: GET /team/players/{} - Fetching player by email", email);
        return teamService.getByEmail(email);
    }

    @PutMapping("/{email}")
    public String updatePlayer(@PathVariable String email, @RequestBody Player newPlayerData) {
        log.info("API Call: PUT /team/players/{} - Updating player data", email);
        if (!teamService.playerExists(email)) {
            log.warn("Update failed: Player with email '{}' does not exist", email);
            return "Player with email " + email + " does not exist";
        }
        teamService.updatePlayer(email, newPlayerData);
        log.info("Successfully updated player with email '{}'", email);
        return "Player updated successfully";
    }

    @PostMapping
    public String addPlayer(@RequestBody Player player) {
        String email = player.getEmail();
        log.info("API Call: POST /team/players - Adding player with email: '{}'", email);
        if (email == null || email.isBlank()) {
            log.warn("Add player failed: Email is required");
            return "Email is required";
        }
        if (teamService.playerExists(email)) {
            log.warn("Add player failed: Player already exists with email '{}'", email);
            return "Player already exists with email: " + email;
        }
        // id is not provided by the user; MongoDB will auto-generate it upon saving
        player.setId(null);
        teamService.addPlayer(player);
        log.info("Successfully added player with email '{}' and system-generated ID: '{}'", email, player.getId());
        return "Player added successfully with system-generated ID: " + player.getId();
    }

    @DeleteMapping("/{email}")
    public String removePlayer(@PathVariable String email) {
        log.info("API Call: DELETE /team/players/{} - Removing player", email);
        if (!teamService.playerExists(email)) {
            log.warn("Delete failed: Player with email '{}' does not exist", email);
            return "Player with email " + email + " does not exist";
        }
        teamService.removePlayer(email);
        log.info("Successfully removed player with email '{}'", email);
        return "Player removed successfully";
    }

    @DeleteMapping("/all")
    public String removeAllPlayers() {
        log.info("API Call: DELETE /team/players/all - Removing all players");
        teamService.removeAllPlayers();
        log.info("Successfully removed all players");
        return "All players removed successfully";
    }

}

