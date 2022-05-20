package main.networking;

import main.TankTrouble;
import main.gui.GameWindow;
import main.model.Battlefield;
import main.model.Player;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ClientThread extends Thread{
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean isRunning;

    public ClientThread(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        InputStream inputStream;
        OutputStream outputStream;
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
        this.isRunning = true;
        this.start();
    }

    @Override
    public void run() {
        Message result = null;
        Message msg;

        try {
            while (this.isRunning) {
                try {
                    msg = (Message) this.objectInputStream.readObject();

                    switch (msg.type) {
                        case lobbyUpdate -> {
                            if(TankTrouble.waitForGameStartWindow != null) {
                                TankTrouble.waitForGameStartWindow.updateJoinedPlayerList((ArrayList<Player>) msg.data);
                            }
                        }
                        case serverClosing -> {
                            JOptionPane.showMessageDialog(null, "Server stopped");
                            TankTrouble.waitForGameStartWindow.leaveRoom();
                        }
                        case serverStartingBattlefieldBuild -> {
                            TankTrouble.waitForGameStartWindow.remoteGameStarted();
                            TankTrouble.gameWindow = new GameWindow();
                            TankTrouble.gameWindow.setBattleField((Battlefield) msg.data);
                            TankTrouble.gameWindow.drawBattlefield();
                        }
                    }
                } catch (SocketException | SocketTimeoutException e) {

                }

                synchronized (this) {
                    try {
                        this.wait(50);
                    } catch (InterruptedException e) {
                        this.isRunning = false;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
