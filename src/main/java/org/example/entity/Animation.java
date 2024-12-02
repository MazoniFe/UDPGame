package org.example.entity;

import java.awt.*;
import java.util.List;

public class Animation {
    private List<Image> frames;
    private int currentFrameIndex = 0;
    private long lastFrameTime = 0;
    private long frameDelay; // Tempo entre os quadros

    public Animation(List<Image> frames, long frameDelay) {
        this.frames = frames;
        this.frameDelay = frameDelay;
    }

    public Image getCurrentFrame() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= frameDelay) {
            lastFrameTime = currentTime;
            currentFrameIndex = (currentFrameIndex + 1) % frames.size(); // Loop pela lista de frames
        }
        return frames.get(currentFrameIndex);
    }
}
