package com.bpm.TankTroubleBME.model;

import com.bpm.TankTroubleBME.model.Player;

import java.net.Inet4Address;
import java.util.List;

public class Room {
    enum RoomType {
        Host,
        Guest
    }

    public RoomType type;
    public String name;
    public Player owner;
    public List<Player> joinedPlayers;
    public int slots;
    public Inet4Address ip;
}
