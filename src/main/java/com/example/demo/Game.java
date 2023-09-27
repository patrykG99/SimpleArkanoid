package com.example.demo;

import javax.swing.*;

public class Game extends JFrame {
    public Game(){
        add(new GameWindow());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setTitle("ARKANOID");
    }
}
