package com.voznia.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {

    private final int COUNT_THREADS = 32;

    private final ConcurrentMap<String, Integer> totalWords;
    private ExecutorService threads = null;
    private ServerSocket socket;

    public Server(int port) {
        this.totalWords = new ConcurrentHashMap();
        this.threads = Executors.newFixedThreadPool(COUNT_THREADS);
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Cannot open port " + port + " !");
            e.printStackTrace();
        }
    }

    public void acceptUser() {
        while (true) {
            try {
                threads.execute(new Worker(socket.accept(), totalWords));
            } catch (IOException e) {
                threads.shutdown();
                try {
                    socket.close();
                } catch (IOException ioException) {
                    System.err.println("Any problem to close the server's socket!");
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}
