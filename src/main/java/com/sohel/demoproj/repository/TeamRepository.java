package com.sohel.demoproj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sohel.demoproj.entity.Player;

@Repository
public interface TeamRepository extends JpaRepository<Player, Long> {
    boolean existsByName(String name);

    void deleteByName(String name);

    List<Player> findByName(String name);
}
