package main.networking;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private boolean isRunning = true;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Message result = null;

        try {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            Message msg = (Message) objectInputStream.readObject();
            switch (msg.type) {
                case joinRequest -> {
                    result = NetworkController.handleExternalJoinRequest(msg.data, socket.getInetAddress());
                }
                case lobbyUpdateRequest -> {
                    result = NetworkController.handleLobbyUpdateRequest();
                }
            }
            objectOutputStream.writeObject(result);

            while (this.isRunning) {


                synchronized (this) {
                    try {
                        this.wait(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
