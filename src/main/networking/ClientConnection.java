package main.networking;

import main.TankTrouble;
import main.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection {
    Socket socket;
    ServerReceiveThread receiveThread;
    ServerTransmitThread transmitThread;
    Player player;
    public ClientConnection(Socket socket,
                            ObjectInputStream objectInputStream,
                            ObjectOutputStream objectOutputStream,
                            Player player) {
        this.receiveThread = new ServerReceiveThread(objectInputStream, this);
        this.transmitThread = new ServerTransmitThread(objectOutputStream, this, player);
        this.receiveThread.start();
        this.transmitThread.start();
        this.player = player;
    }

    public void closeConnection() {
        ArrayList<Player> players = TankTrouble.mainGame.getOwnRoom().joinedPlayers;
        for(int i = 0; i < players.size(); i++) {
            if(players.get(i).ip == this.player.ip) {
                TankTrouble.mainGame.getOwnRoom().joinedPlayers.remove(i);
                break;
            }
        }
        TankTrouble.waitForGameStartWindow.updateJoinedPlayerList(TankTrouble.mainGame.getOwnRoom().joinedPlayers);

        if(this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.receiveThread = null;
        this.transmitThread = null;
    }

    public void stopServer() {
        this.transmitThread.stopServer();
        this.receiveThread.stopServer();
    }
}
