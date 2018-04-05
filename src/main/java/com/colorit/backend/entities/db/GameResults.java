package com.colorit.backend.entities.db;

import javax.persistence.*;

@Entity(name = "GameResults")
public class GameResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer countGames = 0;
    private Integer countWins = 0;
    private Integer rating = 0;

    @Column(name = "rating", columnDefinition = "INTEGER DEFAULT 0")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Column(name = "countGames", columnDefinition = "INTEGER DEFAULT 0")
    public Integer getCountGames() {
        return countGames;
    }

    public void setCountGames(Integer countGames) {
        this.countGames = countGames;
    }

    @Column(name = "countWins", columnDefinition = "INTEGER DEFAULT 0")
    public Integer getCountWins() {
        return countWins;
    }

    public void setCountWins(Integer countWins) {
        this.countWins = countWins;
    }
}
