package com.sohel.demoproj.service;

import java.util.List;

public interface TeamServiceInterface {

    void addPlayer(String player);

    List<String> getPlayers();

    void removePlayer(String player);

    boolean playerExists(String player);
}