package main.networking;

import main.TankTrouble;
import main.gui.GameWindow;
import main.model.Battlefield;
import main.model.Player;

import javax.swing.*;
import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ClientTransmitThread extends Thread {
    private final ObjectOutputStream objectOutputStream;
    private Queue<Object> messageQueue = null;
    private boolean isRunning;

    public ClientTransmitThread(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
        this.messageQueue = new LinkedList<>();
        this.isRunning = true;
        this.start();
    }

    @Override
    public void run() {

        while (this.isRunning) {
            if(this.messageQueue != null) {
                while (!this.messageQueue.isEmpty()) {
                    try {
                        Message msg = (Message) this.messageQueue.poll();
                        this.objectOutputStream.writeObject(msg);
                        if(msg.type == Message.MessageType.clientLeaving) {
                            this.isRunning = false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            synchronized (this) {
                try {
                    this.wait(1);
                } catch (InterruptedException e) {
                    this.isRunning = false;
                }
            }
        }
    }

    public void sendMessage(Message msg) {
        this.messageQueue.add(msg);
    }
}
