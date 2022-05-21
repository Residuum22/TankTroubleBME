package main.networking;

import main.TankTrouble;
import main.model.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class RoomConnectionHelper {
    public RoomConnectionHelper(Socket socket) {
        Message response;
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            Message msg = (Message) objectInputStream.readObject();
            if (msg.type == Message.MessageType.joinRequest) {
                if (NetworkController.handleExternalJoinRequest(msg.data, socket.getInetAddress())) {
                    response = new Message(Message.MessageType.joinAccepted, null);
                    objectOutputStream.writeObject(response);
                    ClientConnection connection = new ClientConnection(socket, objectInputStream, objectOutputStream, (Player) msg.data);
                    TankTrouble.mainGame.networkController.activeClientConnections.add(connection);
                } else {
                    response = new Message(Message.MessageType.joinDeclined, null);
                    objectOutputStream.writeObject(response);
                    socket.close();
                }
            } else {
                System.out.println("Received unknown message type");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
