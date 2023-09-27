package com.example.demo.controllers;


import com.example.demo.SavedGame;
import com.example.demo.services.SavedGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("games")
public class SavedGameController {
    private final SavedGamesService gamesService;

    @Autowired
    public SavedGameController(SavedGamesService gamesService){
        this.gamesService = gamesService;
    }

    @GetMapping
    public List<SavedGame> getSavedGames(){
        return gamesService.getGames();
    }

    @PostMapping
    public void addNewGame(@RequestBody SavedGame game){
        gamesService.addNewGame(game);
    }



}
