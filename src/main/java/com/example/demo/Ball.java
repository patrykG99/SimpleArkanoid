package com.example.demo;

import javax.swing.*;

public class Ball extends Element {

    private float xd; // changed from int to float
    private float yd; // changed from int to float
    private float speed = 2.0f; // Constant speed

    public Ball(){
        xd = 2.0f; // float literal
        yd = -2.0f; // float literal
        image = new ImageIcon("src/images/ball.png").getImage();
        getDimensions();
        x = 400f ;
        y = 500f;
    }

    public void setVelocity(float angle, float speed) {
        this.speed = speed;
        xd = (float)(Math.cos(angle));
        yd = (float)(Math.sin(angle));
        normalizeVelocity(); // Normalize and scale the velocity
    }
    private void normalizeVelocity() {
        float magnitude = (float)Math.sqrt(xd * xd + yd * yd);
        xd = (xd / magnitude) * speed;
        yd = (yd / magnitude) * speed;
    }

    public float getXd() { // return type changed to float
        return xd;
    }

    public float getYd() { // return type changed to float
        return yd;
    }

    public void setXd(float xd) { // parameter type changed to float
        this.xd = xd;
    }

    public void setYd(float yd) { // parameter type changed to float
        this.yd = yd;
    }

    public void move(){
        x += xd;
        y += yd;

        // Left boundary
        if (x <= 0) {
            xd = Math.abs(xd);
        }
        // Right boundary (adjust according to your window width)
        if (x >= 600) {
            xd = -Math.abs(xd);
        }
        // Top boundary
        if (y <= 0){
            yd = Math.abs(yd);
        }
        // Add bottom boundary if needed
    }
}
