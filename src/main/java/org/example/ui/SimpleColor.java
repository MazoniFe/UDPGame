package org.example.ui;

import java.awt.*;

public class SimpleColor {

    private int red;
    private int green;
    private int blue;
    private int alpha;

    public SimpleColor(int r, int g, int b, int a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    public SimpleColor() {

    }

    public int getRed() { return red; }
    public int getGreen() { return green; }
    public int getBlue() { return blue; }
    public int getAlpha() { return alpha; }

    public void setRed(int r) { this.red = r; }
    public void setGreen(int g) { this.green = g; }
    public void setBlue(int b) { this.blue = b; }
    public void setAlpha(int a) { this.alpha = a; }

    public Color toColor() {
        return new Color(red, green, blue, alpha);
    }
}
