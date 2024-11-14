package org.example;

import org.example.entity.InputController;
import org.example.entity.Player;
import org.example.network.NetworkMessage;
import org.example.network.UDPClient;
import org.example.ui.GameGUI;
import org.example.ui.SimpleColor;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Scanner;

public class MainPlayer {
    private static Player player;
    private static UDPClient client;
    private static GameGUI gameGUI;

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();

        String localIP = getLocalIPAddress();

        client = new UDPClient(localIP, 5001); // Use o IP real aqui
        client.receiveMessages();

        player = new Player(name, 0, 0, null);
        client.sendMessage(new NetworkMessage(NetworkMessage.MessageType.CONNECT, player));

        gameGUI = new GameGUI();
        InputController.getInputController(gameGUI.getFrame(), client, player);
        requestServerStatus(client);
    }

    public static String getLocalIPAddress() throws IOException {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress();
    }

    public static void updatePlayerList(List<Player> players) {
        gameGUI.updatePlayerList(players);
    }

    public static void requestServerStatus(UDPClient client) throws IOException, InterruptedException {
        while (true) {
            NetworkMessage message = new NetworkMessage(NetworkMessage.MessageType.STATUS_REQUEST, "GET STATUS");
            client.sendMessage(message);
            Thread.sleep(10);
        }
    }
}
