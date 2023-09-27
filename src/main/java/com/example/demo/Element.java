package com.example.demo;

import java.awt.*;

public class Element {

    int x;
    int y;
    int width;
    int height;
    Image image;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    void getDimensions(){
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
}
