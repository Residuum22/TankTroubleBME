package main.gui;

import main.TankTrouble;

import javax.swing.*;

public class CreateRoomWindow {
    private int maxSlotInt;

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
            Object[] inputs = new Object[]{errorMessage, "Room name", roomName, "Max slots", maxSlot};
            int option = JOptionPane.showConfirmDialog(
                    null, inputs, "Create new lobby", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.CANCEL_OPTION) {
                backToMainMenuWindow();
                break;
            }
            try {
                setMaxSlot(Integer.parseInt(maxSlot.getText()));
                createRoom(roomName.getText());
                break;
            } catch (NumberFormatException nfe) {
                errorMessage.setText("Max slot field must contains integer number only!");
            }
        } while (true);
    }

    public void backToMainMenuWindow() {
        TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
    }

    public void setMaxSlot(int slot) {
        maxSlotInt = slot;
    }

    public void createRoom(String roomName) {
        //Todo wait for network controller
        //Todo make waitforgametostartwindowhere
        TankTrouble.waitForGameStartWindow = new WaitForGameStartWindow();
    }

}
