package com.sohel.demoproj.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.sohel.demoproj.entity.Player;

public interface TeamRepository extends MongoRepository<Player, String> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Optional<Player> findByEmail(String email);
}
