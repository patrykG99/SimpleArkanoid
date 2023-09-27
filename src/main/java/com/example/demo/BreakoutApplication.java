package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class BreakoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreakoutApplication.class, args);
		System.setProperty("java.awt.headless", "false");
		EventQueue.invokeLater(() ->{
			Game game = new Game();
			game.setVisible(true);
		});
	}
}
