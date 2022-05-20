package main.networking;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class NetworkController extends Thread {
    public static final int roomDiscoveryPort = 9981;
    public static final int Port = 5000;
    private Socket remoteRoomSocket;

    private final DiscoveryService discoveryService = new DiscoveryService();

    public NetworkController() {
        this.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(NetworkController.Port);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    }

    public void startDiscovery() {
        this.discoveryService.startDiscovery();
    }

    public void stopDiscovery() {
        this.discoveryService.stopDiscovery();
    }

    public void startExternalDiscoveryService() {
        this.discoveryService.startExternalDiscoveryService();
    }

    public void stopExternalDiscoveryService() {
        this.discoveryService.stopExternalDiscoveryService();
    }

    public boolean joinRoom(Room room) {
        System.out.println("Joining " + room.name + " room");
        Socket socket;

        try {
            socket = new Socket(room.ip, NetworkController.Port);
            Message msg = new Message(Message.MessageType.joinRequest, TankTrouble.mainGame.getThisPlayer());
            Message result = this.sendMessageGetResponse(socket, msg);

            if(result != null && result.type == Message.MessageType.joinAccepted) {
                this.remoteRoomSocket = socket;
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Message sendMessageGetResponse(Socket socket, Message msg) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            socket.setSoTimeout(100);
            objectOutputStream.writeObject(msg);

            return (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Message handleExternalJoinRequest(Object data, InetAddress address) {
        System.out.println("Received new connection request");

        Room ownRoom = TankTrouble.mainGame.getOwnRoom();
        Player player = (Player) data;

        if(ownRoom.joinedPlayers.size() < ownRoom.slots) {
            player.ip = (Inet4Address) address;
            ownRoom.joinedPlayers.add(player);
            return new Message(Message.MessageType.joinAccepted, null);
        }

        return new Message(Message.MessageType.joinDeclined, null);
    }

    public ArrayList<Player> updateLobby() {
        Message msg = new Message(Message.MessageType.lobbyUpdateRequest, null);
        Message response = this.sendMessageGetResponse(this.remoteRoomSocket, msg);
        return (ArrayList<Player>) response.data;
    }

    public static Message handleLobbyUpdateRequest() {
        System.out.println("Sending lobby update");
        return new Message(Message.MessageType.lobbyUpdateResponse, TankTrouble.mainGame.getOwnRoom().joinedPlayers);
    }

}
