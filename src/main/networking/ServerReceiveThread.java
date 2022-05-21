package main.networking;

import main.TankTrouble;
import main.gui.GameWindow;
import main.model.Battlefield;
import main.model.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ServerReceiveThread extends Thread {
    private final ObjectInputStream objectInputStream;
    private final ClientConnection connection;
    private boolean isRunning;

    public ServerReceiveThread(ObjectInputStream objectInputStream, ClientConnection connection) {
        this.objectInputStream = objectInputStream;
        this.connection = connection;
        this.isRunning = true;
    }

    // keyPressFromClient
    @Override
    public void run() {
        try {
            while (this.isRunning) {
                try {
                    Message msg = (Message) this.objectInputStream.readObject();

                    switch (msg.type) {
                        case keyPressFromClient -> {
                            TankTrouble.mainGame.networkController.broadcastKeyPress((KeyEvent) msg.data, this.connection);
                        }
                    }
                } catch (SocketException | EOFException e) {
                    if(this.isRunning) {
                        e.printStackTrace();
                    }
                } catch (SocketTimeoutException ignored) {

                }

                synchronized (this) {
                    try {
                        this.wait(1);
                    } catch (InterruptedException e) {
                        this.isRunning = false;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        this.isRunning = false;
    }
}
