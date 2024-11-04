package org.example.entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import org.example.network.NetworkMessage;
import org.example.network.UDPClient;

import java.io.IOException;

public class InputController implements KeyListener {
    private UDPClient client;

    private Player player;

    public InputController(JFrame frame, UDPClient client, Player player) {
        this.client = client;
        this.player = player;
        frame.addKeyListener(this); // Adiciona o KeyListener ao JFrame
    }

    public static void getInputController(JFrame frame, UDPClient client, Player player) {
        new InputController(frame, client, player);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> {
                    NetworkMessage message = new NetworkMessage(
                            NetworkMessage.MessageType.UPDATE,
                            NetworkMessage.PlayerAction.MOVE_LEFT
                    );
                    client.sendMessage(message);
                }
                case KeyEvent.VK_D -> {
                    NetworkMessage message = new NetworkMessage(
                            NetworkMessage.MessageType.UPDATE,
                            NetworkMessage.PlayerAction.MOVE_RIGHT
                    );
                    client.sendMessage(message);
                }
                case KeyEvent.VK_W -> {
                    NetworkMessage message = new NetworkMessage(
                            NetworkMessage.MessageType.UPDATE,
                            NetworkMessage.PlayerAction.MOVE_TOP
                    );
                    client.sendMessage(message);
                }
                case KeyEvent.VK_S -> {
                    NetworkMessage message = new NetworkMessage(
                            NetworkMessage.MessageType.UPDATE,
                            NetworkMessage.PlayerAction.MOVE_BOTTOM
                    );
                    client.sendMessage(message);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Pode adicionar lógica aqui para quando as teclas forem liberadas, se necessário
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não necessário para este exemplo
    }
}
