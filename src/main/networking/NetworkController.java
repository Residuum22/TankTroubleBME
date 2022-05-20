package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkController extends Thread {
    public static final int roomDiscoveryPort = 9981;

    private DiscoveryService discoveryService = new DiscoveryService();

    public NetworkController() {
        this.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            // new EchoThread(socket).start();
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
}
