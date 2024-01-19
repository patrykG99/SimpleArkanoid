package com.example.demo;

import javax.swing.*;
import java.awt.event.KeyEvent;


public class Bat extends Element {
    private float xx;
    public Bat(){
        image = new ImageIcon("src/images/bat.png").getImage();
        getDimensions();
        x = 400f;
        y = 550f;
    }

    void move(){
        x += xx;
        x += xx;


        if(x <= 0)
            x = 0;

        if(x >= 600 - width)
            x = 600 - width;
    }

    void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT)
            xx = -1;
        if(keyCode == KeyEvent.VK_RIGHT)
            xx = 1;
    }
    void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_LEFT)
            xx = 0;

        if(keyCode == KeyEvent.VK_RIGHT)
            xx = 0;
    }
}
