package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IncomingConnectionHandlerThread extends Thread {
    private ServerSocket serverSocket;
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
            while (this.isRunning) {
                try {
                    assert this.serverSocket != null;
                    socket = this.serverSocket.accept();
                } catch (IOException e) {
                    if(this.isRunning) {
                        e.printStackTrace();
                    }
                }

                if(socket != null) {
                    new ServerThread(socket).start();
                }
                System.out.println("Got new connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopListening() {
        this.isRunning = false;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
