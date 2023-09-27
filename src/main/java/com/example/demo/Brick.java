package com.example.demo;

import javax.swing.*;

public class Brick extends Element {

    boolean destr = false;

    public Brick(int x, int y){
        this.x = x;
        this.y = y;
        image = new ImageIcon("src/images/brick.png").getImage();
        getDimensions();
    }

    public boolean isDestr() {
        return destr;
    }

    public void setDestr(boolean destr) {
        this.destr = destr;
    }
}
