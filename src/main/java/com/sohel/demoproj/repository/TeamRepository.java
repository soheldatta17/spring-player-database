package com.sohel.demoproj.repository;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class TeamRepository {
    ArrayList<String> players = new ArrayList<>();
    public void addPlayer(String player) {
        players.add(player);
    }
    public ArrayList<String> getPlayers() {
        return players;
    }

    public void removePlayer(String player) {
        players.remove(player);
    }

    public boolean playerExists(String player) {
        return players.contains(player);
    }
}
