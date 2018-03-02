package com.colorit.backend.entities;

import javax.persistence.*;

@Entity
public class GameResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "countGames", columnDefinition = "INTEGER DEFAULT 0")
    private Integer countGames = 0;
    @Column(name = "countWins", columnDefinition = "INTEGER DEFAULT 0")
    private Integer countWins = 0;

    public Integer getCountGames() {
        return countGames;
    }

    public void setCountGames(Integer countGames) {
        this.countGames = countGames;
    }

    public Integer getCountWins() {
        return countWins;
    }

    public void setCountWins(Integer countWins) {
        this.countWins = countWins;
    }
}
