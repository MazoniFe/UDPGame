package org.example;

import org.example.network.UDPServer;

import java.net.SocketException;

public class MainServer {

    public static void main(String[] args) throws SocketException {
        UDPServer server = new UDPServer(5001);
        server.receiveMessages();
    }
}
