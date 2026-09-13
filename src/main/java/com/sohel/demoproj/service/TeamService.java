package com.sohel.demoproj.service;

import java.util.List;
import java.util.Objects;

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
        teamRepository.save(Objects.requireNonNull(player));
    }

    @Override
    public List<Player> getPlayers() {
        return teamRepository.findAll();
    }

    @Override
    public Player getByEmail(String email) {
        return teamRepository.findByEmail(Objects.requireNonNull(email)).orElse(null);
    }

    @Override
    public void updatePlayer(String email, Player newPlayerData) {
        teamRepository.save(Objects.requireNonNull(newPlayerData));
    }

    @Override
    public void removePlayer(String email) {
        teamRepository.deleteByEmail(Objects.requireNonNull(email));
    }

    @Override
    public void removeAllPlayers() {
        teamRepository.deleteAll();
    }

    @Override
    public boolean playerExists(String email) {
        return teamRepository.existsByEmail(Objects.requireNonNull(email));
    }
}
