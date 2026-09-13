package com.sohel.demoproj.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sohel.demoproj.entity.Player;
import com.sohel.demoproj.repository.TeamRepository;

@Service
public class TeamService implements TeamServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(TeamService.class);
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    @CacheEvict(value = {"playersList", "playerByEmail"}, allEntries = true)
    public void addPlayer(Player player) {
        log.debug("TeamService: Saving player to MongoDB and evicting Redis cache entries");
        teamRepository.save(Objects.requireNonNull(player));
    }

    @Override
    @Cacheable(value = "playersList", key = "'all'")
    public List<Player> getPlayers() {
        log.debug("TeamService: Cache miss for 'playersList::all'. Querying MongoDB for all players...");
        return teamRepository.findAll();
    }

    @Override
    @Cacheable(value = "playerByEmail", key = "#email", unless = "#result == null")
    public Player getByEmail(String email) {
        log.debug("TeamService: Cache miss for 'playerByEmail::{}'. Querying MongoDB...", email);
        return teamRepository.findByEmail(Objects.requireNonNull(email)).orElse(null);
    }

    @Override
    @CacheEvict(value = {"playersList", "playerByEmail"}, allEntries = true)
    public void updatePlayer(String email, Player newPlayerData) {
        log.debug("TeamService: Updating player '{}' in MongoDB and evicting Redis cache entries", email);
        teamRepository.save(Objects.requireNonNull(newPlayerData));
    }

    @Override
    @CacheEvict(value = {"playersList", "playerByEmail"}, allEntries = true)
    public void removePlayer(String email) {
        log.debug("TeamService: Deleting player '{}' from MongoDB and evicting Redis cache entries", email);
        teamRepository.deleteByEmail(Objects.requireNonNull(email));
    }

    @Override
    @CacheEvict(value = {"playersList", "playerByEmail"}, allEntries = true)
    public void removeAllPlayers() {
        log.debug("TeamService: Deleting all players from MongoDB and evicting Redis cache entries");
        teamRepository.deleteAll();
    }

    @Override
    public boolean playerExists(String email) {
        log.debug("TeamService: Checking existence of email '{}' in MongoDB", email);
        return teamRepository.existsByEmail(Objects.requireNonNull(email));
    }
}


