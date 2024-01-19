package com.example.demo;


import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalDateTime;


public class GameWindow extends JPanel {
    private Timer timer;
    private Ball ball;
    private Brick[] bricks = new Brick[30];
    private Bat bat;
    private Brick brickTest = new Brick(0,0);
    private boolean inGame = false;
    private boolean start = true;
    private int bricksAmount = 0;
    Menu menu = new Menu();
    private int points = 0;

    private int bricksTC = 0;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;


    public GameWindow() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                bat.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                bat.keyReleased(e);
            }
        });
        setFocusable(true);
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);

        bat = new Bat();
        ball = new Ball();
        this.add(menu);

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(inGame) {
                    bat.move();
                    ball.move();
                    collisions();
                    repaint();
                }
                else{
                    repaint();
                }
            }
        });
        timer.setDelay(0);
        timer.start();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if(start){
            check();
        }

        if(inGame)
            drawObjects(g2d);

        else if(!start){
            save();
            points = 0;
            menu.setSelectedLevel(0);
            this.add(menu);
            bat = new Bat();
            ball = new Ball();
            start = true;
            inGame = true;
            timer.start();
        }
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(bat.getImage(), (int) bat.getX(), (int) bat.getY(), bat.getWidth(), bat.getHeight(), this);
        g2d.drawImage(ball.getImage(), (int)ball.getX(), (int)ball.getY(), ball.getWidth(), ball.getHeight(), this);
        if (menu.getSelectedLevel() != 0){
            for (int i = 0; i < bricksTC; ) {
                if (bricks[i].isDestr()) {
                    i++;
                } else {
                    g2d.drawImage(bricks[i].getImage(), (int) bricks[i].getX(), (int) bricks[i].getY(), bricks[i].getWidth(), bricks[i].getHeight(), this);
                    i++;
                }
            }
        }
    }

    private void checkGameState() {
        if (ball.getY() >= 600 - ball.getHeight()) {
            endGame(false);
        } else if (bricksAmount == 0) {
            endGame(true);
        }
    }
    private void checkBallBrickCollision() {
        float ballPosY = ball.getY();

        for (int i = 0; i < bricks.length; i++) {
            Brick brick = bricks[i];
            // Add a check to ensure brick is not null
            if (brick != null && !brick.isDestr() && ballCollidesWithBrick(brick)) {
                handleBrickCollision(brick);
            }
        }
    }
    private void collisions() {
        checkGameState();
        checkBallBatCollision();
        checkBallBrickCollision();
    }
    private void checkBallBatCollision() {
        if (ball.getY() + ball.getHeight() >= bat.getY() && ball.getY() <= bat.getY() + bat.getHeight()) {
            if (ball.getX() + ball.getWidth() > bat.getX() && ball.getX() < bat.getX() + bat.getWidth()) {
                // Reverse the Y direction
                ball.setYd(-2f); // You can adjust this value for desired vertical speed

                // Calculate the hit position on the bat
                double hitPosition = (ball.getX() + (double) ball.getWidth() / 2) - (bat.getX() + (double) bat.getWidth() / 2);
                double relativeHitPosition = 4 * hitPosition / (double) bat.getWidth(); // Adjusted for more range

                // Set new direction and speed
                ball.setXd((float) relativeHitPosition); // Here, speed and direction are combined
            }
        }
    }
    private void handleBrickCollision(Brick brick) {
        if (brick == null) {
            return;
        }
        if (ball.getY() + ball.getHeight() - 1 <= brick.getY() ||
                ball.getY() >= brick.getY() + brick.getHeight()) {
            ball.setYd(ball.getYd() * -1);
        } else {
            ball.setXd(ball.getXd() * -1);
        }

        // Handle ToughBrick and regular brick differently
        if (brick instanceof ToughBrick) {
            ToughBrick toughBrick = (ToughBrick) brick;
            if (toughBrick.getHp() > 1) {
                toughBrick.setHp(toughBrick.getHp() - 1);
            } else {
                brick.setDestr(true);
                bricksAmount--;
                points += 200;
            }
        } else {
            brick.setDestr(true);
            bricksAmount--;
            points += 100;
        }
    }
    private boolean ballCollidesWithBrick(Brick brick) {
        if (brick == null || brick.isDestr()) {
            return false;
        }
        return ball.getY() + ball.getHeight() >= brick.getY() &&
                ball.getY() <= brick.getY() + brick.getHeight() &&
                ball.getX() + ball.getWidth() >= brick.getX() &&
                ball.getX() <= brick.getX() + brick.getWidth();
    }

    private void endGame(boolean won) {
        inGame = false;
        finishTime = LocalDateTime.now();
        long duration = Duration.between(startTime, finishTime).toSeconds();

        if (won) {
            points += 1000 - duration;
        } else {
            points -= duration;
        }
        points = Math.max(points, 0);
    }




    private void check(){
        points = 0;
        if(menu.getSelectedLevel()!= 0){
            startTime = LocalDateTime.now();
            int x = 0;
            inGame = true;
            this.remove(menu);
            start = false;
            if (menu.getSelectedLevel() == 1) {
                bricksTC = 15;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        if(x % 2 == 0){
                            bricks[x] = new ToughBrick(10+ j * (brickTest.getWidth() + 210), 100 + i* 40, 2);
                        }
                        else {
                            bricks[x] = new Brick(10 + j * (brickTest.getWidth() + 210), 100 + i * 40);
                        }
                        x++;
                        bricksAmount++;
                    }
                }
            }
            else if (menu.getSelectedLevel() == 2){
                bricksTC = 20;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 10; j++) {
                        bricks[x] = new Brick(10 + j * (brickTest.getWidth() + 8), 100 + i * 40);
                        x++;
                        bricksAmount++;
                    }
                }
            }
            else if(menu.getSelectedLevel() == 3){
                bricksTC = 30;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 10; j++) {
                        bricks[x] = new Brick(10 + j * (brickTest.getWidth() + 8), 100 + i * 40);
                        x++;
                        bricksAmount++;
                    }
                }
            }
        }
    }
    public SavedGame save(){
        RestTemplate template = new RestTemplate();
        SavedGame test =  new SavedGame(menu.getName(),startTime,LocalDateTime.now(), points);
        return template.postForObject("http://localhost:8080/games",test,SavedGame.class);




    }


}

