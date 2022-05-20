package main.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private boolean isRunning = true;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean result = false;
        boolean stopThread = false;

        while (isRunning) {
            try {
                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Message msg = (Message) objectInputStream.readObject();
                switch (msg.type) {
                    case joinRequest -> {
                        result = NetworkController.handleExternalJoinRequest(msg.data, socket.getInetAddress());
                    }
                }

                socket.getOutputStream().write(result ? 1 : 0);
                if(stopThread) {
                    this.interrupt();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            /*
            synchronized (this) {
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
             */
        }
    }
}
