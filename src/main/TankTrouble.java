package main;

import main.gui.GameWindow;
import main.gui.WaitForGameStartWindow;
import main.model.Battlefield;
import main.model.Player;
import main.model.Room;
import main.gui.MainMenuWindow;
import main.networking.DiscoveryService;
import main.networking.NetworkController;

import java.util.ArrayList;
import java.util.List;

public class TankTrouble {
    public static TankTrouble mainGame = new TankTrouble();
    public static MainMenuWindow mainMenuWindow = new MainMenuWindow();
    public static WaitForGameStartWindow waitForGameStartWindow;
    public static GameWindow gameWindow;

    private Room ownRoom = null;
    private ArrayList<Room> listOfRemoteRooms = new ArrayList<>();
    private final Player thisPlayer = new Player();

    public NetworkController networkController = new NetworkController();

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

    public boolean hasOwnRoom() {
        return ownRoom != null;
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
//        GameWindow asd = new GameWindow();
//        asd.drawBattlefield();
//        Battlefield asd = new Battlefield();
//        int[][] int1 = asd.generateBarrier(Battlefield.barrierType.LWall);
//        int[][] int2 = asd.generateBarrier(Battlefield.barrierType.HLine);
//        int[][] int3 = asd.generateBarrier(Battlefield.barrierType.VLine);
//        int[][] int4 = asd.generateBarrier(Battlefield.barrierType.TWall);
    }
}
