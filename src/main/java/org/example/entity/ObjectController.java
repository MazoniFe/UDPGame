package org.example.entity;

import java.util.List;

public class ObjectController {
    private final List<Moveable> movableObjects;
    private final int steps = 25;
    private final int delay = 25;

    public ObjectController(List<Moveable> movableObjects) {
        this.movableObjects = movableObjects;
    }

    public void startMovement() {
        new Thread(() -> {
            while (true) {
                try {
                    for (Moveable object : movableObjects) {
                        if (object.getActionStatus("isWalking")) {
                            moveObject(object);
                        }
                    }
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void startAnimation() {
        new Thread(() -> {
            while (true) {
                try {
                    for (Moveable object : movableObjects) {
                        if (object.getActionStatus("isWalking")) {
                            moveObject(object);
                        }
                    }
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void moveObject(Moveable object) {
        if (!object.getActionStatus("isWalking")) return;

        float targetPosX = object.getPosX() + 1;
        float targetPosY = object.getPosY();

        float deltaX = (targetPosX - object.getPosX()) / steps;
        float deltaY = (targetPosY - object.getPosY()) / steps;

        object.setPosX(object.getPosX() + deltaX);
        object.setPosY(object.getPosY() + deltaY);

        // Verifica se o movimento terminou
        if (Math.abs(targetPosX - object.getPosX()) < 0.1f && Math.abs(targetPosY - object.getPosY()) < 0.1f) {
            object.setPosX(targetPosX);
            object.setPosY(targetPosY);
            object.setActionStatus("isWalking", false); // Fim do movimento
        }
    }
}
