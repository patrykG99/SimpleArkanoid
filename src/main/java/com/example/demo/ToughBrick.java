package com.example.demo;

import javax.swing.*;

public class ToughBrick extends Brick{

    private int hp;

    public ToughBrick(int x, int y, int hp){
        super(x,y);
        image = new ImageIcon("src/images/tough.png").getImage();
        this.hp = hp;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}

