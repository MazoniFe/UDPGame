package org.example.ui;

import org.example.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TileMap extends JPanel {
    private final int tileSize = 40;
    private final int mapWidth;
    private final int mapHeight;
    private final Map<Player, Point> players;

    public TileMap(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.players = new HashMap<>();
        setPreferredSize(new Dimension(mapWidth * tileSize, mapHeight * tileSize));
    }

    public void addPlayer(Player player) {
        players.put(player, new Point(player.getPosX(), player.getPosY()));
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
        for (Map.Entry<Player, Point> entry : players.entrySet()) {
            String playerName = entry.getKey().getName();
            Color playerColor = entry.getKey().getColor().toColor();
            Point position = entry.getValue();
            g.setColor(playerColor);
            g.fillOval(position.x * tileSize + 10, position.y * tileSize + 10, 20, 20);
            g.drawString(playerName, position.x * tileSize + 5, position.y * tileSize + 30);
        }
    }
}
