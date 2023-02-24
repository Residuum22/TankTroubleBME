package main.networking;

import main.model.Player;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class PlayerKeyPress implements Serializable {
    public Player player;
    public int key;

    public PlayerKeyPress(Player player, int key) {
        this.player = player;
        this.key = key;
    }
}
