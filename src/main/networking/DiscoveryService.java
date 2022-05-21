package main.networking;

import main.TankTrouble;
import main.model.Room;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class DiscoveryService extends Thread {
    private final boolean logEnabled = false;
    private final int pingTimeout = 5;
    private final int discoveryRoundDelay = 100;
    private final int roomCheckTimeout = 50;
    private ArrayList<Room> foundRooms;
    private boolean discoveryActive = true;
    private boolean externalDiscoveryActive = false;
    ServerSocket serverSocket = null;
    Socket socket = null;

    public DiscoveryService() {
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            if(this.discoveryActive) {
                ArrayList<String> networks;
                ArrayList<String> reachableHosts = new ArrayList<>();
                ArrayList<Room> foundRoomsTmp = new ArrayList<>();

                if (this.logEnabled) {
                    System.out.println("Checking available network interfaces...");
                }
                networks = checkAvailableNetworks();

                if (this.logEnabled) {
                    System.out.println("Checking available hosts on interfaces...");
                }
                for (String network : networks) {
                    reachableHosts.addAll(this.checkSubNet(network));
                }

                if (this.logEnabled) {
                    System.out.println("Checking if rooms are available on hosts...");
                }
                for (String reachableHost : reachableHosts) {
                    Room room;
                    room = this.checkIfRemoteRoomAvailable(reachableHost);
                    if (room != null) {
                        foundRoomsTmp.add(room);
                    }
                }
                this.foundRooms = foundRoomsTmp;
                TankTrouble.mainGame.addNewListOfRemoteRooms(this.foundRooms);

                if (this.logEnabled) {
                    System.out.println("Discovery cycle done.");
                }

                try {
                    Thread.sleep(this.discoveryRoundDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(this.externalDiscoveryActive) {
                if (this.logEnabled) {
                    System.out.println("Listening to external room discovery requests...");
                }

                try {
                    this.serverSocket = new ServerSocket(NetworkController.roomDiscoveryPort);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    this.socket = this.serverSocket.accept();

                    if (this.logEnabled) {
                        System.out.println("Sending room data to someone...");
                    }

                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(TankTrouble.mainGame.getOwnRoom());

                    this.socket.close();
                    this.serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Server stopped or I/O error: " + e);
                }
            }
        }
    }

    private ArrayList<String> checkAvailableNetworks() {
        ArrayList<String> networks = new ArrayList<>();

        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                final NetworkInterface cur = interfaces.nextElement();

                if(cur.isLoopback()) {
                    continue;
                }

                if(this.logEnabled) {System.out.println("interface " + cur.getName());}

                for(final InterfaceAddress address : cur.getInterfaceAddresses()) {
                    final InetAddress inet_addr = address.getAddress();

                    if(!(inet_addr instanceof Inet4Address)) {
                        continue;
                    }
                    if(address.getNetworkPrefixLength() != 24) {
                        continue;
                    }

                    if(logEnabled) {
                        System.out.println("  address: " + inet_addr.getHostAddress() +
                                "/" + address.getNetworkPrefixLength());
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
            if(this.logEnabled) {
                System.out.println("Testing " + host);
            }
            try {
                if (InetAddress.getByName(host).isReachable(pingTimeout)) {
                    if (this.logEnabled) {
                        System.out.println(host + " is reachable");
                    }
                    reachable.add(host);
                }
            } catch (ConnectException e) {
                if(this.logEnabled) {
                    System.out.println("Connection refused on " + host);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return reachable;
    }

    private Room checkIfRemoteRoomAvailable(String host) {
        Room room = null;

        try (Socket socket = new Socket(host, NetworkController.roomDiscoveryPort)) {
            socket.setSoTimeout(this.roomCheckTimeout);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            room = (Room) objectInputStream.readObject();
            room.ip = (Inet4Address) Inet4Address.getByName(host);
        } catch (ClassNotFoundException | IOException e) {
            if(this.logEnabled) {
                System.out.println("Connection refused or timed out on " + host);
            }
        }

        return room;
    }

    public void startDiscovery() {
        this.discoveryActive = true;
    }

    public void stopDiscovery() {
        this.discoveryActive = false;
    }

    public void startExternalDiscoveryService() {
        this.externalDiscoveryActive = true;
    }

    public void stopExternalDiscoveryService() {
        this.externalDiscoveryActive = false;

        try {
            if(serverSocket != null) {
                this.serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
