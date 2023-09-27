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
        g2d.drawImage(bat.getImage(), bat.getX(), bat.getY(), bat.getWidth(), bat.getHeight(), this);
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), this);
        if (menu.getSelectedLevel() != 0){
            for (int i = 0; i < bricksTC; ) {
                if (bricks[i].isDestr()) {
                    i++;
                } else {
                    g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getWidth(), bricks[i].getHeight(), this);
                    i++;
                }
            }
        }
    }

    private void collisions(){
        if(ball.getY()>=600-ball.getHeight()){
            inGame = false;
            bricksAmount = 0;
            finishTime = LocalDateTime.now();
            points -= Duration.between(startTime,finishTime).toSeconds();
            if(points < 0)
                points = 0;

        }
        if(bricksAmount == 0){
            inGame = false;
            bricksAmount = 0;
            finishTime = LocalDateTime.now();
            points += 1000;
            points -= Duration.between(startTime,finishTime).toSeconds();
            if(points < 0)
                points = 0;


        }

        if(ball.getY() -1 ==bat.getY()-ball.getHeight()){
            if(ball.getX()>=bat.getX()&&ball.getX()<=bat.getX()+(bat.getWidth()/2)){
                ball.setYd(-1);
                ball.setXd(-1);
            }
            else if(ball.getX()>bat.getX()+(bat.getWidth()/2) && ball.getX()<=bat.getX()+bat.getWidth()) {
                ball.setYd(-1);
                ball.setXd(1);
            }
        }
        else if(ball.getY() + ball.getHeight() >= bat.getY() && ball.getY() <= bat.getY() + bat.getHeight()){

            if(ball.getX() + ball.getWidth() - 1 == bat.getX()){
                ball.setXd(ball.getXd()*(-1));
            }
            else if(ball.getX()== bat.getX() + bat.getWidth()){
                ball.setXd(ball.getXd()*(-1));
            }
        }

        //Brick Collisions
        int ballPosY = ball.getY();
        int x = 0;
        for(int i=0; i<bricksTC;i++) {
            //From bottom
            if (ballPosY == bricks[x].getY() + bricks[x].height) {
                if (ball.getX() + ball.getWidth() >= bricks[x].getX() && ball.getX() <= bricks[x].getX() + bricks[x].getWidth() && !bricks[x].isDestr()) {
                    ball.setYd(1);
                    if(bricks[i] instanceof ToughBrick){
                        if(((ToughBrick)bricks[i]).getHp() > 1){
                            ((ToughBrick)bricks[i]).setHp(1);
                        }
                        else{
                            bricks[i].setDestr(true);
                            bricksAmount--;
                            points += 200;
                        }
                    }
                    else {
                        bricks[x].setDestr(true);
                        bricksAmount--;
                        points += 100;
                    }
                }
            }

            //From top
            else if (ballPosY + ball.getHeight() +1 == bricks[x].getY()) {
                if (ball.getX() + ball.getWidth() >= bricks[x].getX() && ball.getX() <= bricks[x].getX() + bricks[x].getWidth() && !bricks[x].isDestr()) {
                    ball.setYd(-1);
                    if(bricks[i] instanceof ToughBrick){
                        if(((ToughBrick)bricks[i]).getHp() > 1){
                            ((ToughBrick)bricks[i]).setHp(1);
                        }
                        else{
                            bricks[i].setDestr(true);
                            bricksAmount--;
                            points += 100;
                        }
                    }
                    else {
                        bricks[x].setDestr(true);
                        bricksAmount--;
                        points += 100;
                    }
                }
            }
            //Sides
            else if (ballPosY + ball.getHeight() >= bricks[x].getY() && ballPosY <= bricks[x].getY() + bricks[x].getHeight()) {
                if (ball.getX() + ball.getWidth()+1 == bricks[x].getX() && !bricks[x].isDestr()) {

                    ball.setXd(ball.getXd() * (-1));
                    if(bricks[i] instanceof ToughBrick){
                        if(((ToughBrick)bricks[i]).getHp() > 1){
                            ((ToughBrick)bricks[i]).setHp(((ToughBrick) bricks[i]).getHp()-1);

                        }
                        else{
                            bricks[i].setDestr(true);
                            bricksAmount--;
                        }
                    }
                    else {
                        bricks[x].setDestr(true);
                        bricksAmount--;
                    }

                } else if (ball.getX() == bricks[x].getX() + bricks[x].getWidth() && !bricks[x].isDestr()) {
                    ball.setXd(ball.getXd() * (-1));
                    if(bricks[i] instanceof ToughBrick){
                        if(((ToughBrick)bricks[i]).getHp() > 1){
                            ((ToughBrick)bricks[i]).setHp(1);
                        }
                        else{
                            bricks[i].setDestr(true);
                            bricksAmount--;
                        }
                    }
                    else {
                        bricks[x].setDestr(true);
                        bricksAmount--;
                    }
                }
            }
            x++;
        }
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

