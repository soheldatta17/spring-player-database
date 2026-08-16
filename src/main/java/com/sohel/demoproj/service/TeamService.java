package com.sohel.demoproj.service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void addPlayer(String player) {
        teamRepository.save(new Player(player));
    }

    @Override
    public List<String> getPlayers() {
        return teamRepository.findAll()
                .stream()
                .map(Player::getName)
                .collect(Collectors.toList());
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