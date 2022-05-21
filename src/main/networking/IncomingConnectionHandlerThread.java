package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IncomingConnectionHandlerThread extends Thread {
    ServerSocket serverSocket = null;
    private boolean isRunning;

    public IncomingConnectionHandlerThread() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        Socket socket = null;
        this.isRunning = true;

        try {
            this.serverSocket = new ServerSocket(NetworkController.Port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (this.isRunning) {
            try {
                socket = this.serverSocket.accept();
            } catch (IOException e) {
                if(this.isRunning) {
                    e.printStackTrace();
                }
            }

            if(socket != null) {
                new RoomConnectionHelper(socket);
            }
            System.out.println("Got new connection");
        }
    }

    public void stopListening() {
        this.isRunning = false;
        if(this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
