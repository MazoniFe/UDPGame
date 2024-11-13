package org.example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Player;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UDPServer {
    private DatagramSocket serverSocket;
    private ObjectMapper objectMapper;
    private HashMap<SocketAddress, Player> connectedPlayers = new HashMap<>();

    public UDPServer(int port) throws SocketException {
        serverSocket = new DatagramSocket(port);
        objectMapper = new ObjectMapper();
        System.out.println("Server Started. Listening for Clients on port " + port + "...");
    }

    public void receiveMessages() {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket;

        while (true) {
            try {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                SocketAddress clientSocketAddress = receivePacket.getSocketAddress();
                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                NetworkMessage networkMessage = objectMapper.readValue(clientMessage, NetworkMessage.class);

                if (networkMessage.getType() == NetworkMessage.MessageType.CONNECT) {
                    Player clientPlayer = objectMapper.convertValue(networkMessage.getData(), Player.class);
                    connectedPlayers.put(clientSocketAddress, clientPlayer);
                }

                if (networkMessage.getType() == NetworkMessage.MessageType.STATUS_REQUEST) {
                    List<Player> playersList = new ArrayList<>(connectedPlayers.values());
                    NetworkMessage newNetworkMessage = new NetworkMessage(NetworkMessage.MessageType.STATUS_RESPONSE, playersList);
                    sendMessage(newNetworkMessage, receivePacket.getAddress(), receivePacket.getPort());
                }

                if (networkMessage.getType() == NetworkMessage.MessageType.UPDATE) {
                    NetworkMessage.PlayerAction action = objectMapper.convertValue(networkMessage.getData(), NetworkMessage.PlayerAction.class);
                    if(!this.hasPlayerOnPosition(connectedPlayers.get(clientSocketAddress), action)){
                        connectedPlayers.get(clientSocketAddress).move(action);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasPlayerOnPosition(Player player, NetworkMessage.PlayerAction action) {
        int newPosX = player.getPosX();
        int newPosY = player.getPosY();

        switch (action) {
            case MOVE_LEFT -> {
                newPosX--;
            }
            case MOVE_TOP -> {
                newPosY--;
            }
            case MOVE_RIGHT -> {
                newPosX++;
            }
            case MOVE_BOTTOM -> {
                newPosY++;
            }
        }

        int finalNewPosX = newPosX;
        int finalNewPosY = newPosY;
        return connectedPlayers.values().stream()
                .anyMatch(item -> item.getPosX() == finalNewPosX && item.getPosY() == finalNewPosY);
    }


    public void sendMessage(NetworkMessage message, InetAddress clientAddress, int clientPort) throws IOException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        byte[] sendData = jsonMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
        serverSocket.send(sendPacket);
    }
}
