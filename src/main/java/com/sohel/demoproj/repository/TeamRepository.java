package com.sohel.demoproj.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sohel.demoproj.entity.Player;

@Repository
public interface TeamRepository extends MongoRepository<Player, String> {
    boolean existsByName(String name);

    void deleteByName(String name);

    List<Player> findByName(String name);
}
