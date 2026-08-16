package com.sohel.demoproj.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sohel.demoproj.repository.TeamRepository;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void addPlayer(String player) {
        teamRepository.addPlayer(player);
    }

    public List<String> getPlayers() {
        return teamRepository.getPlayers();
    }

    public void removePlayer(String player) {
        teamRepository.removePlayer(player);
    }
    
    public boolean playerExists(String player) {
        return teamRepository.playerExists(player);
    }
}
