package com.example.demo;

import javax.swing.*;

public class Ball extends Element {

    private int xd;
    private int yd;


    public Ball(){
        xd = 1;
        yd = -1;
        image = new ImageIcon("src/images/ball.png").getImage();
        getDimensions();
        x = 400;
        y = 500;
    }

    public int getXd() {
        return xd;
    }

    public void setXd(int xd) {
        this.xd = xd;
    }

    public void setYd(int yd) {
        this.yd = yd;
    }

    public void move(){
        x += xd;
        y += yd;
        x += xd;
        y += yd;

        if(x == 0)
            xd = 1;
        if(y == 0)
            yd = 1;
        if(x>=600-width)
            xd = -1;
    }
}
