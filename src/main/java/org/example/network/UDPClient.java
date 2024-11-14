package org.example.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.MainPlayer;
import org.example.entity.Player;

import java.io.IOException;
import java.net.*;
import java.util.List;

public class UDPClient {

    private final DatagramSocket clientSocket;
    private final InetAddress serverAddress;
    private final int serverPort;
    private ObjectMapper objectMapper;

    List<Player> connectedPlayers;

    public UDPClient(String serverIp, int serverPort) throws IOException {
        this.clientSocket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName(serverIp);
        this.serverPort = serverPort;
        this.objectMapper = new ObjectMapper();
    }

    public void close() {
        clientSocket.close();
    }

    public void sendMessage(NetworkMessage message) throws IOException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        byte[] sendData = jsonMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        clientSocket.send(sendPacket);
    }

    public void receiveMessages() {
        Thread receiveThread = new Thread(() -> {
            byte[] receiveData = new byte[2048];
            while (true) {
                try {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                    NetworkMessage networkMessage = objectMapper.readValue(receivedMessage, NetworkMessage.class);

                    if (networkMessage.getType() == NetworkMessage.MessageType.STATUS_RESPONSE) {
                        List<Player> players = objectMapper.convertValue(networkMessage.getData(), new TypeReference<List<Player>>() {});
                        InetAddress localAddress = InetAddress.getLocalHost();
                        String localIP = localAddress.getHostAddress();
                        int localPort = clientSocket.getLocalPort();

                        InetSocketAddress localSocketAddress = new InetSocketAddress(localIP, localPort);

                        players.forEach(player ->  {
                            if (NetworkUtils.isLocalPlayer(localSocketAddress, player.getSocketAddress())) {
                                player.setIsLocalPlayer(true);
                            } else {
                                player.setIsLocalPlayer(false);
                            }
                        });
                        MainPlayer.updatePlayerList(players);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        receiveThread.start();
    }
}
