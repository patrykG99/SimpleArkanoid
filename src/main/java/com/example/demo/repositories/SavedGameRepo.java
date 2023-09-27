package com.example.demo.repositories;

import com.example.demo.SavedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SavedGameRepo extends JpaRepository<SavedGame, Integer> {

}
