package com.sohel.demoproj.service;

import java.util.List;
import com.sohel.demoproj.entity.Player;

public interface TeamServiceInterface {

    void addPlayer(Player player);

    List<Player> getPlayers();

    void removePlayer(String email);

    void removeAllPlayers();

    boolean playerExists(String email);
}
