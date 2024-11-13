package org.example;

import org.example.entity.InputController;
import org.example.entity.Player;
import org.example.network.NetworkMessage;
import org.example.network.UDPClient;
import org.example.ui.GameGUI;
import org.example.ui.SimpleColor;

import javax.swing.*;
import java.io.IOException;
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
        System.out.print("Code: ");
        String code = scanner.nextLine();
        System.out.println("Color: ");
        System.out.println("BLUE: -> 1 ");
        System.out.println("RED -> 2");
        System.out.println("YELLOW -> 3");
        System.out.println("BLACK -> 4");
        SimpleColor color = null;
        while (color == null) {
            System.out.print("Choose a color (1-4): ");
            int colorChoice = scanner.nextInt();

            switch (colorChoice) {
                case 1 -> color = new SimpleColor(0, 0, 255, 255);     // Azul
                case 2 -> color = new SimpleColor(255, 0, 0, 255);     // Vermelho
                case 3 -> color = new SimpleColor(255, 255, 0, 255);   // Amarelo
                case 4 -> color = new SimpleColor(0, 0, 0, 255);       // Preto
                default -> System.out.println("Invalid choice. Please choose a number between 1 and 4.");
            }
        }

        client = new UDPClient("127.0.0.1", 5001);
        client.receiveMessages();
        player = new Player(name, 0, 0, code.charAt(0), color);
        client.sendMessage(new NetworkMessage(NetworkMessage.MessageType.CONNECT, player));

        gameGUI = new GameGUI();
        InputController.getInputController(gameGUI.getFrame(), client, player);
        requestServerStatus(client);
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
