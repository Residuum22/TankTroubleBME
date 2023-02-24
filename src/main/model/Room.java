package main.model;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    public enum RoomType {
        Host,
        Guest
    }

    public RoomType type;
    public String name;
    public Player owner;
    public ArrayList<Player> joinedPlayers;
    public int slots;
    public Inet4Address ip;

    public Room(Player owner) {
        this.owner = owner;
        this.name = owner.name + "'s room";
    }

    public Room(RoomType type, String name, Player owner, ArrayList<Player> joinedPlayers, int slots, Inet4Address ip) {
        this.type = type;
        this.name = name;
        this.owner = owner;
        this.joinedPlayers = joinedPlayers;
        this.slots = slots;
        this.ip = ip;
    }
}
