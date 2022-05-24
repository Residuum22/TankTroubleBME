package main.networking;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class NetworkController extends Thread {
    public static final int roomDiscoveryPort = 9981;
    public static final int Port = 5000;
    private ObjectInputStream clientObjectInputStream;
    private ObjectOutputStream clientObjectOutputStream;

    private final DiscoveryService discoveryService = new DiscoveryService();
    private IncomingConnectionHandlerThread incomingConnectionHandlerThread = null;
    private ClientReceiveThread clientReceiveThread = null;
    private ClientTransmitThread clientTransmitThread = null;
    public ArrayList<ClientConnection> activeClientConnections = new ArrayList<>();

    public NetworkController() {

    }

    public void startDiscovery() {
        this.discoveryService.startDiscovery();
    }

    public void stopDiscovery() {
        this.discoveryService.stopDiscovery();
    }

    public void startExternalDiscoveryService() {
        this.incomingConnectionHandlerThread = new IncomingConnectionHandlerThread();
        this.incomingConnectionHandlerThread.start();
        this.discoveryService.startExternalDiscoveryService();
    }

    public void stopExternalDiscoveryService() {
        this.discoveryService.stopExternalDiscoveryService();
    }

    public void closeRoom() {
        this.incomingConnectionHandlerThread.stopListening();
        for(ClientConnection t : this.activeClientConnections) {
            t.stopServer();
        }
        this.activeClientConnections.clear();
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
            Message result = null;
            try {
                this.clientObjectOutputStream.writeObject(msg);
                result = (Message) this.clientObjectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if(result != null && result.type == Message.MessageType.joinAccepted) {
                this.clientReceiveThread = new ClientReceiveThread(this.clientObjectInputStream);
                this.clientTransmitThread = new ClientTransmitThread(this.clientObjectOutputStream);
                TankTrouble.networkController.stopDiscovery();
                return true;
            }
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

    public void leaveLobby() {
        if(this.clientReceiveThread != null) {
            this.stopClientFunctions();
        }
        if(TankTrouble.mainGame.hasOwnRoom()) {
            this.closeRoom();
        }
        this.stopExternalDiscoveryService();
        this.startDiscovery();
    }

    public void broadcastGameStarting() {
        for(ClientConnection t : this.activeClientConnections) {
            t.transmitThread.startGame();
        }
    }

    public void leaveGame() {
        this.stopClientFunctions();
        this.startDiscovery();
    }

    private void stopClientFunctions() {
        if(this.clientReceiveThread != null) {
            this.clientReceiveThread.stopReceive();
            this.clientReceiveThread.interrupt();
        }
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

    public void broadcastKeyPress(KeyEvent key, ClientConnection source) throws IOException {
        for(ClientConnection c : this.activeClientConnections) {
            if(c != source) {
                c.transmitThread.sendKeyPress(key);
            }
        }
    }

    public void sendKeyPress(int key) {
        PlayerKeyPress playerKeyPress = new PlayerKeyPress(TankTrouble.mainGame.getThisPlayer(), key);
        Message msg = new Message(Message.MessageType.keyPressFromClient, playerKeyPress);
        this.clientTransmitThread.sendMessage(msg);
    }
}

