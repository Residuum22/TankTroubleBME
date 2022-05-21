package main.model;

import java.io.Serializable;
import java.net.Inet4Address;

public class Player implements Serializable {
    public String name;
    public Inet4Address ip;

    public Player() {
        this.name = null;
        this.ip = null;
    }
    public Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIp(Inet4Address ip) {
        this.ip = ip;
    }
}
