package main.networking;

import main.TankTrouble;
import main.model.Player;

import java.io.*;

public class ServerTransmitThread extends Thread {
    private ObjectOutputStream objectOutputStream;
    private ClientConnection connection;
    private boolean isRunning;

    private enum ServerState {
        stopping,
        waitingForPlayersToJoin,
        startingBattle,
        gameInProgress
    }
    private ServerState serverState;
    private Player player = null;

    public ServerTransmitThread(ObjectOutputStream objectOutputStream, ClientConnection connection, Player player) {
        this.objectOutputStream = objectOutputStream;
        this.connection = connection;
        this.player = player;
        this.isRunning = true;
    }

    @Override
    public void run() {
        try {
            serverState = ServerState.waitingForPlayersToJoin;
            while (this.isRunning) {
                switch (serverState) {
                    case waitingForPlayersToJoin -> {
                        this.sendLobbyUpdate();
                        TankTrouble.waitForGameStartWindow.updateJoinedPlayerList(TankTrouble.mainGame.getOwnRoom().joinedPlayers);
                    }
                    case stopping -> {
                        this.sendClosingMessage();
                        this.isRunning = false;
                    }
                    case startingBattle -> {
                        this.sendStartingMessage();
                        this.serverState = ServerState.gameInProgress;
                    }
                    case gameInProgress -> {

                    }
                }

                synchronized (this) {
                    try {
                        this.wait(serverState == ServerState.waitingForPlayersToJoin ? 100 : 1);
                    } catch (InterruptedException e) {
                        this.isRunning = false;
                    }
                }
            }

            if(this.objectOutputStream != null) {
                this.objectOutputStream.close();
            }
        } catch (IOException e) {
            System.out.println("Client connection closed");
            this.connection.closeConnection();
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
        BattlefieldBuildData battlefieldBuildData = new BattlefieldBuildData(
                TankTrouble.gameWindow.getBattlefield(),
                TankTrouble.gameWindow.getBattlefield().getListOfTanks()
        );
        Message message = new Message(Message.MessageType.serverStartingBattlefieldBuild,
                battlefieldBuildData);
        this.objectOutputStream.writeObject(message);
    }

    public void stopServer() {
        this.serverState = ServerState.stopping;
    }

    public void startGame() {
        this.serverState = ServerState.startingBattle;
    }

    public void sendKeyPress(PlayerKeyPress playerKeyPress) throws IOException {
        Message message = new Message(Message.MessageType.keyPressBroadcast, playerKeyPress);
        this.objectOutputStream.writeObject(message);
    }

    public void sendServerStopping() throws IOException {
        Message message = new Message(Message.MessageType.serverClosing, null);
        this.objectOutputStream.writeObject(message);
    }

    public void sendClientLeave(Message msg) throws IOException {
        this.objectOutputStream.writeObject(msg);
    }
}
