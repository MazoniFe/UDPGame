package org.example.network;

import java.net.InetSocketAddress;

public class NetworkUtils {

    public static boolean isLocalPlayer(InetSocketAddress localSocketAddress, String playerSocketAddress) {
        try {
            String[] parts = playerSocketAddress.replace("/", "").split(":");
            if (parts.length == 2) {
                String playerIP = parts[0];
                int playerPort = Integer.parseInt(parts[1]);

                InetSocketAddress playerSocket = new InetSocketAddress(playerIP, playerPort);

                return playerSocket.equals(localSocketAddress);
            } else {
                System.out.println("Formato inválido de endereço: " + playerSocketAddress);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro ao verificar endereço do jogador: " + e.getMessage());
            return false;
        }
    }
}