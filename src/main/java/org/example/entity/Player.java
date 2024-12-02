package org.example.entity;

import org.example.network.NetworkMessage;
import org.example.ui.SpriteRepository;

import java.net.SocketAddress;

public class Player extends GenericPlayer  {
    private String name;
    private String socketAddress;
    private boolean isLocalPlayer;

    public Player(String name, int posX, int posY, SocketAddress socketAddress) {
        super(posX, posY);
        this.name = name;
        this.socketAddress = socketAddress != null ? socketAddress.toString() : null;
    }

    public Player() {
        super();
        this.name = null;
        this.socketAddress = null;
    }

    public String getName() {
        return name;
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

    public void moveGradually(NetworkMessage.PlayerAction action) {
        float targetPosX = this.getPosX();
        float targetPosY = this.getPosY();

        switch (action) {
            case MOVE_LEFT -> {
                targetPosX -= 1;
                setDirection(SpriteRepository.Direction.LEFT);
            }
            case MOVE_RIGHT -> {
                targetPosX += 1;
                setDirection(SpriteRepository.Direction.RIGHT);
            }
            case MOVE_TOP -> {
                targetPosY -= 1;
                setDirection(SpriteRepository.Direction.NORTH);
            }
            case MOVE_BOTTOM -> {
                targetPosY += 1;
                setDirection(SpriteRepository.Direction.BOTTOM);
            }
        }

        int steps = 25;
        int delay = 25;
        float finalTargetPosX = targetPosX;
        float finalTargetPosY = targetPosY;
        setActionStatus("isWalking", true);
        new Thread(() -> {
            for (int i = 0; i < steps; i++) {
                final float deltaX = (finalTargetPosX - this.getPosX()) / (steps - i);
                final float deltaY = (finalTargetPosY - this.getPosY()) / (steps - i);

                this.setPosX(this.getPosX() + deltaX);
                this.setPosY(this.getPosY() + deltaY);

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            this.setPosX(finalTargetPosX);
            this.setPosY(finalTargetPosY);
            setActionStatus("isWalking", false);
        }).start();
    }
}
