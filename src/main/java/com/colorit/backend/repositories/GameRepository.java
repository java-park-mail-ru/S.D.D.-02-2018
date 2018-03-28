package com.colorit.backend.repositories;

import com.colorit.backend.entities.db.GameResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameResults, Long> {
}