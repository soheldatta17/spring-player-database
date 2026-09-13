package com.sohel.demoproj.service;

import java.util.List;
import com.sohel.demoproj.entity.Player;

public interface TeamServiceInterface {

    void addPlayer(Player player);

    List<Player> getPlayers();

    Player getByEmail(String email);

    void updatePlayer(String email, Player newPlayerData);

    void removePlayer(String email);

    void removeAllPlayers();

    boolean playerExists(String email);
}
