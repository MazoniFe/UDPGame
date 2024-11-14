package org.example.entity;

import org.example.network.NetworkMessage;
import org.example.ui.SpriteRepository;

import java.io.Serializable;
import java.net.SocketAddress;

public class Player implements Serializable {
    private String name;
    private int posX;
    private int posY;
    private String socketAddress;
    private boolean isLocalPlayer;

    private SpriteRepository.SpriteType spriteType = SpriteRepository.SpriteType.GOKU;
    private SpriteRepository.Direction direction = SpriteRepository.Direction.BOTTOM;

    public Player(String name, int posX, int posY, SocketAddress socketAddress) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.socketAddress = socketAddress != null ? socketAddress.toString() : null;
    }

    public Player() {
        this.name = null;
        this.posX = 0;
        this.posY = 0;
        this.socketAddress = null;
    }

    public String getName() {
        return name;
    }

    public SpriteRepository.Direction getDirection() {
        return direction;
    }

    public int getPosX() {
        return posX;
    }

    public String getSocketAddress() {
        return this.socketAddress;
    }

    public void setIsLocalPlayer(boolean value) {
        isLocalPlayer = value;
    }

    public boolean getIsLocalPlayer() {
        return isLocalPlayer;
    }

    public void setSocketAddress(String address) {
        this.socketAddress = address;
    }


    public int getPosY() {
        return posY;
    }

    public SpriteRepository.SpriteType getSpriteType() {
        return spriteType;
    }

    public void move(NetworkMessage.PlayerAction action) {
        switch (action) {
            case MOVE_LEFT -> {
                this.setPosX(this.getPosX() - 1);
                setDirection(SpriteRepository.Direction.LEFT);
            }
            case MOVE_RIGHT -> {
                this.setPosX(this.getPosX() + 1);
                setDirection(SpriteRepository.Direction.RIGHT);
            }
            case MOVE_TOP -> {
                this.setPosY(this.getPosY() - 1);
                setDirection(SpriteRepository.Direction.NORTH);
            }
            case MOVE_BOTTOM -> {
                this.setPosY(this.getPosY() + 1);
                setDirection(SpriteRepository.Direction.BOTTOM);
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

    public void setDirection(SpriteRepository.Direction direction) {
        this.direction = direction;
    }
}
