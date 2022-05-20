package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandlerThread extends Thread {
    public ConnectionHandlerThread() {

    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(NetworkController.Port);
            while (true) {
                try {
                    assert serverSocket != null;
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }

                new ServerThread(socket).start();
                System.out.println("Got new connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
