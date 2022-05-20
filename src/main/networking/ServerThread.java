package main.networking;

import main.TankTrouble;
import main.model.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean isRunning;

    private enum ServerState {
        stopping,
        waitingForPlayersToJoin,
        gameInProgess, startingBattle
    }
    private ServerState serverState;
    private Player remotePlayer = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.isRunning = true;
    }

    @Override
    public void run() {
        Message result = null;

        try {
            InputStream inputStream = socket.getInputStream();
            this.objectInputStream = new ObjectInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(outputStream);

            Message msg = (Message) objectInputStream.readObject();
            if(msg.type == Message.MessageType.joinRequest) {
                result = NetworkController.handleExternalJoinRequest(msg.data, socket.getInetAddress());
                objectOutputStream.writeObject(result);
                this.remotePlayer = (Player) msg.data;
                if(result.type != Message.MessageType.joinAccepted) {
                    this.isRunning = false;
                    socket.close();
                }
            }

            TankTrouble.mainGame.networkController.activeConnections.add(this);
            serverState = ServerState.waitingForPlayersToJoin;
            while (this.isRunning) {
                switch (serverState) {
                    case waitingForPlayersToJoin -> {
                        this.sendLobbyUpdate();
                        TankTrouble.waitForGameStartWindow.updateJoinedPlayerList(TankTrouble.mainGame.getOwnRoom().joinedPlayers);
                    }
                    case stopping -> {
                        this.sendClosingMessage();
                        TankTrouble.mainGame.networkController.activeConnections.remove(this);
                        this.isRunning = false;
                    }
                    case startingBattle -> {
                        this.sendStartingMessage();
                        this.serverState = ServerState.gameInProgess;
                    }
                    case gameInProgess -> {

                    }
                }

                synchronized (this) {
                    try {
                        this.wait(50);
                    } catch (InterruptedException e) {
                        this.isRunning = false;
                    }
                }
            }

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client connection closed");
            ArrayList<Player> players = TankTrouble.mainGame.getOwnRoom().joinedPlayers;
            for(int i = 0; i < players.size(); i++) {
                if(players.get(i).ip == this.remotePlayer.ip) {
                    TankTrouble.mainGame.getOwnRoom().joinedPlayers.remove(i);
                    break;
                }
            }
            TankTrouble.waitForGameStartWindow.updateJoinedPlayerList(TankTrouble.mainGame.getOwnRoom().joinedPlayers);
        }
    }

    private void sendClosingMessage() throws IOException {
        Message message = new Message(Message.MessageType.serverClosing, null);
        this.objectOutputStream.writeObject(message);
    }

    private void sendLobbyUpdate() throws IOException {
        Message message = new Message(Message.MessageType.lobbyUpdate, TankTrouble.mainGame.getOwnRoom().joinedPlayers);
        this.objectOutputStream.writeObject(message);
    }

    private void sendStartingMessage() throws IOException {
        Message message = new Message(Message.MessageType.serverStartingBattlefieldBuild,
                TankTrouble.gameWindow.getBattleField());
        this.objectOutputStream.writeObject(message);
    }

    public void stopServer() {
        this.serverState = ServerState.stopping;
    }

    public void startGame() {
        this.serverState = ServerState.startingBattle;
    }
}
