package org.example.entity;
import org.example.network.NetworkMessage;
import org.example.ui.SpriteRepository;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class GenericPlayer implements Moveable {
    private float posX;
    private float posY;
    private final Map<String, Boolean> actions =  new HashMap<>();
    private Animation currentAnimation;
    private EnumMap<SpriteRepository.Direction, Animation> animations;
    private SpriteRepository.SpriteType spriteType = SpriteRepository.SpriteType.GOKU;
    private SpriteRepository.Direction direction = SpriteRepository.Direction.BOTTOM;


    public GenericPlayer(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.actions.put("isWalking", false);
        this.actions.put("isAttacking", false);
        animations = new EnumMap<>(SpriteRepository.Direction.class);
    }

    public GenericPlayer() {

    }

    @Override
    public void moveGradually(NetworkMessage.PlayerAction action) {
        if (!getActionStatus("isWalking")) {
            setActionStatus("isWalking", true);

            float targetPosX = this.posX;
            float targetPosY = this.posY;

            switch (action) {
                case MOVE_LEFT:
                    targetPosX -= 1;
                    setDirection(SpriteRepository.Direction.LEFT);
                    break;
                case MOVE_RIGHT:
                    targetPosX += 1;
                    setDirection(SpriteRepository.Direction.RIGHT);
                    break;
                case MOVE_TOP:
                    targetPosY -= 1;
                    setDirection(SpriteRepository.Direction.NORTH);
                    break;
                case MOVE_BOTTOM:
                    targetPosY += 1;
                    setDirection(SpriteRepository.Direction.BOTTOM);
                    break;
            }

            int steps = 25;
            int delay = 25;
            float finalTargetPosX = targetPosX;
            float finalTargetPosY = targetPosY;

            // Simulando o movimento gradual
            new Thread(() -> {
                for (int i = 0; i < steps; i++) {
                    float deltaX = (finalTargetPosX - this.posX) / (steps - i);
                    float deltaY = (finalTargetPosY - this.posY) / (steps - i);
                    this.posX += deltaX;
                    this.posY += deltaY;

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                this.posX = finalTargetPosX;
                this.posY = finalTargetPosY;
                setActionStatus("isWalking", false); // Movimento concluído
            }).start();
        }
    }

    @Override
    public void setDirection(SpriteRepository.Direction direction) {
        this.direction = direction; // Corrigido: definir a direção corretamente
    }

    @Override
    public float getPosX() {
        return this.posX; // Retorna a posição atual no eixo X
    }

    @Override
    public float getPosY() {
        return this.posY;
    }

    @Override
    public void setPosX(float x) {
        this.posX = x; // Define a posição no eixo X
    }

    @Override
    public void setPosY(float y) {
        this.posY = y; // Define a posição no eixo Y
    }

    @Override
    public void setActionStatus(String action, boolean status) {
        actions.put(action, status); // Atualiza o status da ação
    }

    public SpriteRepository.SpriteType getSpriteType() {
        return spriteType;
    }

    public SpriteRepository.Direction getDirection() {
        return direction;
    }

    @Override
    public boolean getActionStatus(String action) {
        return actions.getOrDefault(action, false); // Retorna o status da ação
    }
}
