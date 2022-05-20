package main.gui;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CreateRoomWindow {

    /**
     * This function creates a JOptionPane where the user can give the room name (todo)
     * and the max slots in the room. If the user hit the ok button but the max slot field contains not an integer
     * value than the user will get an error message to correct it's mistake. If the value correct than create a room.
     * and create a new lobby window. If the user hit the cancel button than get back to main manu window.
     */
    public CreateRoomWindow() {
        JTextField roomName = new JTextField();
        JTextField maxSlot = new JFormattedTextField();
        JLabel errorMessage = new JLabel();
        do {
            roomName.setEnabled(false);
            roomName.setText(TankTrouble.mainGame.getThisPlayerName() + "'s room");
            Object[] inputs = new Object[]{errorMessage, "Room name", roomName, "Max slots", maxSlot};
            int option = JOptionPane.showConfirmDialog(
                    null, inputs, "Create new lobby", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.CANCEL_OPTION) {
                backToMainMenuWindow();
                break;
            }
            try {
                createRoom(roomName.getText(), Integer.parseInt(maxSlot.getText()));
                break;
            } catch (NumberFormatException nfe) {
                errorMessage.setText("Max slot field must contains integer number only!");
            }
        } while (true);
    }

    public void backToMainMenuWindow() {
        TankTrouble.mainGame.networkController.startDiscovery();
        TankTrouble.mainGame.networkController.stopExternalDiscoveryService();
        TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
    }

    public void createRoom(String roomName, int slots) {
        //Todo wait for network controller
        Room room = new Room(
                Room.RoomType.Host,
                roomName,
                TankTrouble.mainGame.getThisPlayer(),
                new ArrayList<>(),
                slots,
                null);
        room.joinedPlayers.add(TankTrouble.mainGame.getThisPlayer());
        TankTrouble.mainGame.addNewOwnRoom(room);
        TankTrouble.mainGame.networkController.stopDiscovery();
        TankTrouble.mainGame.networkController.startExternalDiscoveryService();
        TankTrouble.waitForGameStartWindow = new WaitForGameStartWindow();
        TankTrouble.waitForGameStartWindow.updateJoinedPlayerList(TankTrouble.mainGame.getOwnRoom().joinedPlayers);

    }

}
