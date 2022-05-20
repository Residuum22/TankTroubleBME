package main.networking;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;

public class NetworkController extends Thread {
    public static final int roomDiscoveryPort = 9981;
    public static final int Port = 5000;

    private DiscoveryService discoveryService = new DiscoveryService();

    public NetworkController() {
        this.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(this.Port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
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
        return this.sendJoinRequest(room);
    }

    public boolean sendJoinRequest(Room room) {
        Message msg = new Message(Message.MessageType.joinRequest, TankTrouble.mainGame.getThisPlayer());
        return this.sendMessage(room.ip, this.Port, msg);
    }

    private boolean sendMessage(Inet4Address ip, int port, Message msg) {
        try (Socket socket = new Socket(ip, port)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(msg);

            socket.setSoTimeout(500);
            return socket.getInputStream().read() == 1;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean handleExternalJoinRequest(Object data, InetAddress address) {
        System.out.println("Received new connection request");

        Room ownRoom = TankTrouble.mainGame.getOwnRoom();
        Player player = (Player) data;

        if(ownRoom.joinedPlayers.size() < ownRoom.slots) {
            player.ip = (Inet4Address) address;
            ownRoom.joinedPlayers.add(player);

            return true;
        }

        return false;
    }

}
