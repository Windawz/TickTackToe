package com.nmihalchenko.ttt;

import java.io.IOException;
import java.util.InputMismatchException;

public class Main {
    private static final int DEFAULT_PORT = 27195;

    public static void main(String[] args) {
        boolean isServer = args.length > 0 && args[0].trim().equalsIgnoreCase("server");
        boolean isClient = args.length > 0 && args[0].trim().equalsIgnoreCase("client");
        if (!isServer && !isClient) {
            throw new IllegalStateException("Neither server nor client");
        }
        int port = DEFAULT_PORT;
        if (args.length > 1) {
            try {
                int parsedPort = Integer.parseInt(args[1].trim());
                if (parsedPort > 0) {
                    port = parsedPort;
                }
            } catch (InputMismatchException ignored) { }
        }

        if (isServer) {
            try (Server server = new Server(new TickTackToe(), port)) {
                server.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (isClient) {
            try (Client client = new Client(port)) {
                client.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
