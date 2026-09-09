package com.sohel.demoproj.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sohel.demoproj.entity.Player;

@Repository
public interface TeamRepository extends MongoRepository<Player, String> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Optional<Player> findByEmail(String email);
}

