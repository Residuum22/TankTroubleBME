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
    private ObjectInputStream clientObjectInputStream;
    private ObjectOutputStream clientObjectOutputStream;

    private final DiscoveryService discoveryService = new DiscoveryService();
    private final ConnectionHandlerThread connectionHandler = new ConnectionHandlerThread();
    private ClientThread clientThread = null;

    public ArrayList<ServerThread> activeConnections = new ArrayList<>();

    public NetworkController() {

    }


    public void startDiscovery() {
        this.discoveryService.startDiscovery();
    }

    public void stopDiscovery() {
        this.discoveryService.stopDiscovery();
    }

    public void startExternalDiscoveryService() {
        connectionHandler.start();
        this.discoveryService.startExternalDiscoveryService();
    }

    public void stopExternalDiscoveryService() {
        this.discoveryService.stopExternalDiscoveryService();
    }

    public void closeRoom() {
        connectionHandler.interrupt();
        for(ServerThread t : this.activeConnections) {
            t.stopServer();
        }
    }

    public boolean joinRoom(Room room) {
        System.out.println("Joining " + room.name + " room");
        Socket socket;

        try {
            socket = new Socket(room.ip, NetworkController.Port);
            socket.setSoTimeout(100);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            this.clientObjectOutputStream = new ObjectOutputStream(outputStream);
            this.clientObjectInputStream = new ObjectInputStream(inputStream);

            Message msg = new Message(Message.MessageType.joinRequest, TankTrouble.mainGame.getThisPlayer());
            Message result = this.sendMessageGetResponse(msg);

            if(result != null && result.type == Message.MessageType.joinAccepted) {
                this.clientThread = new ClientThread(this.clientObjectInputStream, this.clientObjectOutputStream);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Message sendMessageGetResponse(Message msg) {
        try {
            this.clientObjectOutputStream.writeObject(msg);
            return  (Message) this.clientObjectInputStream.readObject();
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

    public void leaveLobby() {
        if(this.clientThread != null) {
            this.stopClientFunctions();
        }
        if(TankTrouble.mainGame.hasOwnRoom()) {
            this.closeRoom();
        }
        this.stopExternalDiscoveryService();
        this.startDiscovery();
    }

    public void broadcastGameStarting() {
        for(ServerThread t : this.activeConnections) {
            t.startGame();
        }
    }

    public void leaveGame() {
        this.stopClientFunctions();
        this.startDiscovery();
    }

    private void stopClientFunctions() {
        this.clientThread.interrupt();
        if(this.clientObjectInputStream != null) {
            try {
                this.clientObjectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(this.clientObjectOutputStream != null) {
            try {
                this.clientObjectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

