package org.example.ui;

import org.example.entity.Player;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameGUI {
    private JFrame frame;
    private TileMap tileMap;
    private JList<String> playerList;
    private DefaultListModel<String> playerListModel;

    public GameGUI() {
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Player Tilemap");
        tileMap = new TileMap(25, 25);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tileMap, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void updatePlayerList(List<Player> players) {
        tileMap.clearPlayers();
        for (Player player : players) {
            tileMap.addPlayer(player);
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public TileMap getTileMap() {
        return tileMap;
    }
}
