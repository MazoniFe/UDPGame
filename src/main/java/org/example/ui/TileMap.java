
package org.example.ui;

import org.example.entity.Animation;
import org.example.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TileMap extends JPanel {
    private final int tileSize = 60;
    private final int mapWidth;
    private final int mapHeight;
    private final Map<Player, Point> players = new ConcurrentHashMap<>();


    public TileMap(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
        setPreferredSize(new Dimension(mapWidth * tileSize, mapHeight * tileSize));
    }

    public void addPlayer(Player player) {
        players.put(player, new Point((int) player.getPosX(), (int) player.getPosY()));
        repaint();
    }

    public void removePlayer(Player player) {
        players.remove(player);
        repaint();
    }

    public void clearPlayers() {
        players.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        drawPlayers(g);
    }

    private void drawTiles(Graphics g) {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                g.setColor(Color.BLACK);
                g.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    private void drawPlayers(Graphics g) {
        Map<Player, Point> playersSnapshot = new HashMap<>(players);
        for (Map.Entry<Player, Point> entry : playersSnapshot.entrySet()) {
            Player player = entry.getKey();

            float posX = player.getPosX();
            float posY = player.getPosY();

            Animation playerAnimation = SpriteRepository.getAnimation(player.getSpriteType(), player.getDirection());

            int centerX = (int) (posX * tileSize);
            int centerY = (int) (posY * tileSize);
            int spriteSize = 50;

            if (playerAnimation != null) {
                Image currentFrame = playerAnimation.getCurrentFrame();
                if (currentFrame != null) {
                    centerX = (int) (posX * tileSize + (tileSize - spriteSize) / 2);
                    centerY = (int) (posY * tileSize + (tileSize - spriteSize) / 2);
                    g.drawImage(currentFrame, centerX, centerY, spriteSize, spriteSize, null);
                }
            }

            g.setColor(player.getIsLocalPlayer() ? Color.GREEN : Color.BLACK);

            g.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics metrics = g.getFontMetrics();
            int textWidth = metrics.stringWidth(player.getName());
            int textX = centerX + (spriteSize - textWidth) / 2;
            g.drawString(player.getName(), textX, centerY - 5);
        }
    }



}
