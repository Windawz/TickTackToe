package com.nmihalchenko.ttt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client implements AutoCloseable {
    private final Socket socket;
    private final Scanner scanner;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        boolean isOver = false;
        while (!isOver) {
            var reader = new DataInputStream(socket.getInputStream());
            var writer = new DataOutputStream(socket.getOutputStream());
            int rowIndex = scanner.nextInt();
            int colIndex = scanner.nextInt();
            writer.writeInt(rowIndex);
            writer.writeInt(colIndex);
            isOver = reader.readBoolean();
            if (isOver) {
                System.out.println("Over!");
            }
        }
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }
}
