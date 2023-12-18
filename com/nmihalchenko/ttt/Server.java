package com.nmihalchenko.ttt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server implements AutoCloseable {
    private final TickTackToe ttt;
    private final ServerSocket serverSocket;
    private final Map<Mark, Socket> playerSockets = new HashMap<>();

    public Server(TickTackToe ttt, int port) throws IOException {
        this.ttt = ttt;
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        playerSockets.put(Mark.X, serverSocket.accept());
        playerSockets.put(Mark.O, serverSocket.accept());

        boolean isOver = false;
        while (!isOver) {
            for (var playerMark : playerSockets.keySet()) {
                var socket = playerSockets.get(playerMark);
                var reader = new DataInputStream(socket.getInputStream());
                var writer = new DataOutputStream(socket.getOutputStream());
                int rowIndex = reader.readInt();
                int colIndex = reader.readInt();
                ttt.put(rowIndex, colIndex, playerMark);
                isOver = ttt.getWinner().isPresent();
                writer.writeBoolean(isOver);
                if (isOver) {
                    System.out.println("Over! Winner is " + playerMark);
                    break;
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        serverSocket.close();
        for (var socket : playerSockets.values()) {
            socket.close();
        }
    }
}
