package com.sohel.demoproj.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sohel.demoproj.entity.Player;
import com.sohel.demoproj.repository.TeamRepository;

@Service
public class TeamService implements TeamServiceInterface {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public void addPlayer(Player player) {
        teamRepository.save(player);
    }

    @Override
    public List<Player> getPlayers() {
        return teamRepository.findAll();
    }

    @Override
    public void removePlayer(String player) {
        teamRepository.deleteByName(player);
    }

    @Override
    public boolean playerExists(String player) {
        return teamRepository.existsByName(player);
    }
}