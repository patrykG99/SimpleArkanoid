package com.example.demo;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table
public class SavedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;
    private String player;
    private LocalDateTime timeStarted;
    private LocalDateTime timeFinished;

    public int getPoints() {
        return points;
    }

    private int points;

    public SavedGame(int gameId, String player, LocalDateTime timeStarted, LocalDateTime timeFinished, int points) {
        this.gameId = gameId;
        this.player = player;
        this.timeStarted = timeStarted;
        this.timeFinished = timeFinished;
        this.points = points;
    }

    public SavedGame(String player, LocalDateTime timeStarted, LocalDateTime timeFinished, int points) {
        this.player = player;
        this.timeStarted = timeStarted;
        this.timeFinished = timeFinished;
        this.points = points;

    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(LocalDateTime timeStarted) {
        this.timeStarted = timeStarted;
    }

    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(LocalDateTime timeFinished) {
        this.timeFinished = timeFinished;
    }

    public SavedGame(){

    }

    @Override
    public String toString() {
        return "SavedGame{" +
                "gameId=" + gameId +
                ", player='" + player + '\'' +
                ", timeStarted=" + timeStarted +
                ", timeFinished=" + timeFinished +
                '}';
    }
}
