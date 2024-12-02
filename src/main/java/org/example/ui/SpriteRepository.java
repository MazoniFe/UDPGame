package org.example.ui;

import org.example.entity.Animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SpriteRepository {
    public enum Direction { NORTH, LEFT, RIGHT, BOTTOM }
    public enum SpriteType { GOKU, ELF }

    private static final Map<SpriteType, Map<Direction, Animation>> spriteAnimations = new EnumMap<>(SpriteType.class);

    static {
        try {
            Map<Direction, String> gokuPaths = new EnumMap<>(Direction.class);
            gokuPaths.put(Direction.NORTH, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_TOP_1.png");
            gokuPaths.put(Direction.LEFT, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_LEFT_1.png");
            gokuPaths.put(Direction.RIGHT, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_RIGHT_1.png");
            gokuPaths.put(Direction.BOTTOM, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_BOTTOM_1.png");

            spriteAnimations.put(SpriteType.GOKU, loadAnimations(gokuPaths, 500));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Direction, Animation> loadAnimations(Map<Direction, String> paths, long frameDelay) throws IOException {
        Map<Direction, Animation> animations = new EnumMap<>(Direction.class);
        for (Map.Entry<Direction, String> entry : paths.entrySet()) {
            List<Image> frames = new ArrayList<Image>();
            frames.add(ImageIO.read(new File(entry.getValue())));
            animations.put(entry.getKey(), new Animation(frames, frameDelay));
        }
        return animations;
    }

    public static Animation getAnimation(SpriteType type, Direction direction) {
        return spriteAnimations.getOrDefault(type, new EnumMap<>(Direction.class)).get(direction);
    }

    private static Map<Direction, Image> loadImages(Map<Direction, String> paths) throws IOException {
        Map<Direction, Image> images = new EnumMap<>(Direction.class);
        for (Map.Entry<Direction, String> entry : paths.entrySet()) {
            images.put(entry.getKey(), ImageIO.read(new File(entry.getValue())));
        }
        return images;
    }
}