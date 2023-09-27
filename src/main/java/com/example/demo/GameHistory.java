package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameHistory extends JPanel {
    RestTemplate template = new RestTemplate();
    JScrollPane jScrollPane;

    public GameHistory(){
        createHistoryTable();
        SwingUtilities.updateComponentTreeUI(this);
        setFocusable(true);
        setPreferredSize(new Dimension(550, 300));
    }
    public void createHistoryTable(){
        ResponseEntity<SavedGame[]> response = template.getForEntity("http://localhost:8080/games", SavedGame[].class);
        SavedGame[] savedGames = response.getBody();
        for(int i = 0; i< savedGames.length; i++)
            System.out.println(savedGames[i]);
        String[] columnNames = {"Gracz", "Punkty", "Czas gry", "Data"};
        Object[][] data = new Object[savedGames.length][4];
        for(int i = 0; i < savedGames.length; i++){
            data[i][0] = savedGames[i].getPlayer();
            data[i][1] = savedGames[i].getPoints();
            Duration duration = Duration.between(savedGames[i].getTimeStarted(),savedGames[i].getTimeFinished());
            data[i][2] = LocalTime.MIDNIGHT.plus(duration).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            data[i][3] = savedGames[i].getTimeStarted().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        JTable table = new JTable(data, columnNames);
        jScrollPane= new JScrollPane(table);

        jScrollPane.setPreferredSize(new Dimension(550,300));
        this.add(jScrollPane);
    }
}
