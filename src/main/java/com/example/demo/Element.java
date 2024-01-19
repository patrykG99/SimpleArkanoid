package com.example.demo;

import java.awt.*;

public class Element {

    float x; // Changed to float
    float y; // Changed to float
    int width;
    int height;
    Image image;

    public float getX() { // Return type changed to float
        return x;
    }

    public float getY() { // Return type changed to float
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

    void getDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
}
