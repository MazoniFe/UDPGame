package org.example.entity;

import org.example.network.NetworkMessage;
import org.example.ui.SpriteRepository;

public interface Moveable {
    void moveGradually(NetworkMessage.PlayerAction action);
    void setDirection(SpriteRepository.Direction direction);
    float getPosX();
    float getPosY();
    void setPosX(float x);
    void setPosY(float y);
    void setActionStatus(String action, boolean status);
    boolean getActionStatus(String action);
}
