package org.example.entity;

import org.example.network.NetworkMessage;
import org.example.ui.SimpleColor;

import java.awt.*;
import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int posX;
    private int posY;
    private char code;
    private final SimpleColor color;

    public Player(String name, int posX, int posY, char code, SimpleColor color) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.code = code;
        this.color = color;
    }

    public Player() {
        this.name = null;
        this.posX = 0;
        this.posY = 0;
        this.code = '\u0000';
        this.color  = new SimpleColor();
    }

    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public char getCode() {
        return code;
    }

    public SimpleColor getColor() {
        return color;
    }

    public void move(NetworkMessage.PlayerAction action) {
        switch (action) {
            case MOVE_LEFT -> {
                if (this.getPosX() - 1 > 0) {
                    this.setPosX(this.getPosX() - 1);
                }
            }
            case MOVE_RIGHT -> {
                if (this.getPosX() + 1 < 25) {
                    this.setPosX(this.getPosX() + 1);
                }
            }
            case MOVE_TOP -> {
                if (this.getPosY() - 1 > 0) {
                    this.setPosY(this.getPosY() - 1);
                }
            }
            case MOVE_BOTTOM -> {
                if (this.getPosY() + 1 < 25) {
                    this.setPosY(this.getPosY() + 1);
                }
            }
        }
    }

    // Setters
    public void setName(String nome) {
        this.name = nome;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setCode(char code) {
        this.code = code;
    }
}
