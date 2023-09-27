package com.example.demo.services;

import com.example.demo.SavedGame;
import com.example.demo.repositories.SavedGameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SavedGamesService {
    private final SavedGameRepo gameRepo;

    @Autowired
    public SavedGamesService(SavedGameRepo gameRepo){
        this.gameRepo = gameRepo;

    }
    public List<SavedGame> getGames(){
        return gameRepo.findAll();

    }
    public void addNewGame(SavedGame game){
        gameRepo.save(game);


    }
}
