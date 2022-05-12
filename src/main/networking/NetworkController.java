package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkController extends Thread {
    static final int PORT = 5000;

    public NetworkController() {
        this.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            // new EchoThread(socket).start();
            System.out.println("Got new connection");
        }
    }
}
