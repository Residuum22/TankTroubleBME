package main;

import main.gui.GameWindow;
import main.gui.MainMenuWindow;
import main.gui.WaitForGameStartWindow;
import main.model.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TankTrouble {
    public static TankTrouble mainGame = new TankTrouble();
    public static MainMenuWindow mainMenuWindow = new MainMenuWindow();
    public static WaitForGameStartWindow waitForGameStartWindow;

    private Room ownRoom = null;
    private final ArrayList<Room> listOfRemoteRooms = new ArrayList<>();
    private Player thisPlayer = new Player();

    public static Battlefield myBattlefield = GameWindow.getBattlefield();

    /**
     * LOL this will be our game.
     */
    public TankTrouble() {

    }

    /**
     * This function adds the player's own room.
     */
    public void addNewOwnRoom(Room newOwnRoom) {
        ownRoom = newOwnRoom;
    }

    /**
     * This function removes the player's own room.
      */
    public void removeOwnRoom() {
        ownRoom = null;
    }

    /**
     * This function adds new set of remote rooms to this class listOfRemoteRooms attribute
     * @param newListOfRemoteRooms The new set of remote rooms, which is avaliable.
     */
    public void addNewListOfRemoteRooms(ArrayList<Room> newListOfRemoteRooms) {
        listOfRemoteRooms.removeAll(listOfRemoteRooms);
        listOfRemoteRooms.addAll(newListOfRemoteRooms);
    }

    /**
     * This function returns remote rooms which is avaliable of the current user on the same network.
     * @return The avaliable room's on the network.
     */
    public ArrayList<Room> getListOfRemoteRooms() {
        return listOfRemoteRooms;
    }

    /**
     * This function return the player own room if has one. If not than return null.
     * @return The player's created room.
     */
    public Room getOwnRoom() {
        return ownRoom;
    }

    /**
     * This function returns the player's username who is playing with this TankTrouble client.
     * @return The current player username
     */
    public String getThisPlayerName() {
        return this.thisPlayer.name;
    }

    /**
     * This function returns the player who is playing with this TankTrouble client.
     * @return The current player
     */
    public Player getThisPlayer() {
        return this.thisPlayer;
    }

    /**
     * This function modify the
     * @param name The new username of this player after the modification
     */
    public void modifyPlayerName(String name) {
        thisPlayer.setName(name);
    }

    /**
     * This is the main function. Currently, is not used just exist as the entry point of this program.
     * @param args Command line arguments. (Should never use)
     */
    public static void main(String[] args) {
        GameWindow asd = new GameWindow();
        asd.drawBattlefield();

        try {
            Thread.sleep(7000);
        }catch (Exception e) {
            //
        }

        Field[][] myFields = myBattlefield.getFields();
        myBattlefield.listOfTanks.add(new Tank());
        Tank myTank = myBattlefield.getListOfTanks().get(0);
        myTank.owner = TankTrouble.mainGame.getThisPlayer();
        myTank.direction = MovingObject.Direction.Down;
        asd.updateTank();

        try {
            Thread.sleep(1000);
        }catch (Exception e) {
            //
        }
        myTank.shootMissile();
        Missile myMissile = myBattlefield.getListOfMissiles().get(0);
        myBattlefield.listOfTanks.add(new Tank());
        Tank enemyTank = myBattlefield.getListOfTanks().get(1);
        enemyTank.position = myFields[myMissile.position.getX()][myMissile.position.getY() - 2];
        asd.updateMissile();
        try {
            Thread.sleep(2000);
        }catch (Exception e) {
            //
        }
        myMissile.updateMissilePosition();
        asd.updateMissile();
        try {
            Thread.sleep(2000);
        }catch (Exception e) {
            //
        }
        myMissile.updateMissilePosition();
        asd.updateMissile();
        try {
            Thread.sleep(2000);
        }catch (Exception e) {
            //
        }
        myMissile.updateMissilePosition();
        asd.updateMissile();
        try {
            Thread.sleep(2000);
        }catch (Exception e) {
            //
        }
        myMissile.updateMissilePosition();
        asd.updateMissile();
        try {
            Thread.sleep(2000);
        }catch (Exception e) {
            //
        }
        asd.updateMissile();
        int missileSize = myBattlefield.getListOfMissiles().size();
        int tankSize = myBattlefield.getListOfTanks().size();



    }
}
