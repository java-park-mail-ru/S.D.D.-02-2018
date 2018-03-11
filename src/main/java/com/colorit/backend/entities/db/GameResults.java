package com.colorit.backend.entities.db;

public class GameResults {
    private Integer id;
    private Integer countGames = 0;
    private Integer countWins = 0;
    private Integer rating;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

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
