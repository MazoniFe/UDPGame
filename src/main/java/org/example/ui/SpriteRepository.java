package org.example.ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class SpriteRepository {
    public enum Direction { NORTH, LEFT, RIGHT, BOTTOM }
    public enum SpriteType { GOKU, ELF }

    private static final Map<SpriteType, Map<Direction, Image>> spriteImages = new HashMap<>();

    static {
        try {

            //SPRITES GOKU
            Map<Direction, String> gokuPaths = new EnumMap<>(Direction.class);
            gokuPaths.put(Direction.NORTH, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_TOP_1.png");
            gokuPaths.put(Direction.LEFT, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_LEFT_1.png");
            gokuPaths.put(Direction.RIGHT, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_RIGHT_1.png");
            gokuPaths.put(Direction.BOTTOM, "D:\\Projetos\\Java\\ProjetoUDP\\src\\main\\java\\org\\example\\assets\\goku\\IDLE_BOTTOM_1.png");

            spriteImages.put(SpriteType.GOKU, loadImages(gokuPaths));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Direction, Image> loadImages(Map<Direction, String> paths) throws IOException {
        Map<Direction, Image> images = new EnumMap<>(Direction.class);
        for (Map.Entry<Direction, String> entry : paths.entrySet()) {
            images.put(entry.getKey(), ImageIO.read(new File(entry.getValue())));
        }
        return images;
    }

    public static Image getSpriteImage(SpriteType type, Direction direction) {
        return spriteImages.getOrDefault(type, new EnumMap<>(Direction.class)).get(direction);
    }
}
