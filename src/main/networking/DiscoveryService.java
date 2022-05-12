package main.networking;

import main.model.Room;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class DiscoveryService extends Thread {
    private final boolean logEnabled = true;
    private final int pingTimeout = 10;
    private final int discoveryRoundDelay = 100;
    private final int roomCheckTimeout = 50;
    private ArrayList<Room> foundRooms;

    public DiscoveryService() {
        this.start();
    }

    @Override
    public void run() {
        ArrayList<String> networks = new ArrayList<>();
        ArrayList<String> reachableHosts = new ArrayList<>();
        ArrayList<Room> foundRoomsTmp = new ArrayList<>();

        networks = checkAvailableNetworks();
        for(int i = 0; i < networks.size(); i++) {
            reachableHosts.addAll(this.checkSubNet(networks.get(i)));
        }
        for(int i = 0; i < reachableHosts.size(); i++) {
            Room room;
            room = this.checkIfRemoteRoomAvailable(reachableHosts.get(i));
            if(room != null) {
                foundRoomsTmp.add(room);
            }
        }
        this.foundRooms = foundRoomsTmp;

        synchronized (this) {
            try {
                this.wait(this.discoveryRoundDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<String> checkAvailableNetworks() {
        ArrayList<String> networks = new ArrayList<>();

        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            for(;interfaces.hasMoreElements();) {
                final NetworkInterface cur = interfaces.nextElement();

                if(cur.isLoopback()) {
                    continue;
                }

                if(this.logEnabled) {System.out.println("interface " + cur.getName());}

                for(final InterfaceAddress addr : cur.getInterfaceAddresses()) {
                    final InetAddress inet_addr = addr.getAddress();

                    if(!(inet_addr instanceof Inet4Address)) {
                        continue;
                    }
                    if(addr.getNetworkPrefixLength() != 24) {
                        continue;
                    }

                    if(logEnabled) {
                        System.out.println("  address: " + inet_addr.getHostAddress() +
                                "/" + addr.getNetworkPrefixLength());
                    }

                    networks.add(inet_addr.getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return networks;
    }

    private ArrayList<String> checkSubNet(String hostAddress) {
        ArrayList<String> reachable = new ArrayList<>();
        String subnet = hostAddress.substring(0, hostAddress.lastIndexOf("."));

        for (int i = 1; i < 255; i++) {
            String host = subnet + "." + i;
            System.out.println("Testing " + host);
            try {
                if (InetAddress.getByName(host).isReachable(pingTimeout)){
                    if(this.logEnabled) {System.out.println(host + " is reachable");}
                    reachable.add(host);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return reachable;
    }

    private Room checkIfRemoteRoomAvailable(String host) {
        Room room = null;

        try (Socket socket = new Socket(host, 9981)) {
            socket.setSoTimeout(this.roomCheckTimeout);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            room = (Room) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return room;
    }
}
