package com.colorit.backend.entities;

public class GameResults {
    private Integer id;
    private Integer countGames = 0;
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
