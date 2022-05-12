package main.networking;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class DiscoveryService extends Thread {
    private final boolean logEnabled = true;
    private final int pingTimeout = 10;
    private final int discoveryRoundDelay = 100;

    public DiscoveryService() {
        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            for(;interfaces.hasMoreElements( );) {
                final NetworkInterface cur = interfaces.nextElement();

                if (cur.isLoopback()) {
                    continue;
                }

                System.out.println("interface " + cur.getName());

                for (final InterfaceAddress addr : cur.getInterfaceAddresses()) {
                    final InetAddress inet_addr = addr.getAddress();

                    if(!( inet_addr instanceof Inet4Address)) {
                        continue;
                    }
                    if(addr.getNetworkPrefixLength() != 24) {
                        continue;
                    }

                    if(logEnabled) {
                        System.out.println("  address: " + inet_addr.getHostAddress() +
                                           "/" + addr.getNetworkPrefixLength());
                    }

                    String subnet = inet_addr.getHostAddress();
                    subnet = subnet.substring(0, subnet.lastIndexOf("."));

                    for (int i = 1; i < 255; i++){
                        String host = subnet + "." + i;
                        System.out.println("Testing " + host);
                        try {
                            if (InetAddress.getByName(host).isReachable(pingTimeout)){
                                System.out.println(host + " is reachable");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
