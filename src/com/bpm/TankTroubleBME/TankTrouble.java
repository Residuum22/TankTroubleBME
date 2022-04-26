package com.bpm.TankTroubleBME;

import com.bpm.TankTroubleBME.model.Player;
import com.bpm.TankTroubleBME.model.Room;
import com.bpm.TankTroubleBME.view.MainMenuWindow;

import java.util.List;

public class TankTrouble {
    public static TankTrouble mainGame;
    public static MainMenuWindow mainMenuWindow;

    private Room ownRoom;
    private List<Room> listOfRemoteRooms;
    private final Player thisPlayer = new Player();

    public TankTrouble() {

    }

    public void addNewOwnRoom() {
    }

    public void addNewListOfRemoteRooms() {
    }

    public List<Room> getListOfRemoteRooms() {
        return listOfRemoteRooms;
    }

    public Room getOwnRoom() {
        return ownRoom;
    }

    public void modifyPlayerName(String name) {
        thisPlayer.setName(name);
    }

    public String getThisPlayerName() {
        return this.thisPlayer.name;
    }

    public static void main(String[] args) {
        mainGame = new TankTrouble();
        mainMenuWindow = new MainMenuWindow();
    }

}
