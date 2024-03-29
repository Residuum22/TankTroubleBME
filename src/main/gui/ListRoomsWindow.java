package main.gui;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

public class ListRoomsWindow {
    private final JFrame listRoomsWindowFrame;
    private JPanel contentPanel;
    private JButton joinToLobbyButton;
    private JButton backButton;
    private JPanel listOfRoomPanel;
    private JButton searchForRoomsButton;

    private final ArrayList<JRadioButton> jRadioButtonsArrayList = new ArrayList<>();

    public ListRoomsWindow() {
        listRoomsWindowFrame = new JFrame("Tank Trouble Game");
        listRoomsWindowFrame.setSize(1024, 720);
        listRoomsWindowFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addActionListeners();

        listRoomsWindowFrame.add(contentPanel);
        listRoomsWindowFrame.setVisible(true);
    }

    /**
     * This function will be called when the user hit the one of the radio button. This function set all button to not
     * selected state if one button is selected.
     */
    private final ActionListener jRadioButtonActionListener = e -> {
        joinToLobbyButton.setEnabled(true);
        for (JRadioButton currentButton : jRadioButtonsArrayList) {
            if (!Objects.equals(currentButton.getActionCommand(), e.getActionCommand()))
                currentButton.setSelected(false);
        }
    };

    /**
     * This function adds the action listeners to the buttons and the window closing event.
     * Buttons:
     *  backButton: Go back to main menu.
     *  searchForRoomsButton: Disable join button, and remove JRadioButtons. After that list all new remote rooms than
     *                         update JRadioButtons.
     *  joinToLobbyButton: Call joins to chosen room button
     */
    private void addActionListeners() {

        backButton.addActionListener(e -> {
            backToMainMenuWindow();
        });

        searchForRoomsButton.addActionListener(e -> {
            joinToLobbyButton.setEnabled(false);
            for (JRadioButton currentButton : jRadioButtonsArrayList) {
                listOfRoomPanel.remove(currentButton);
            }
            listRoomInThePanel();
            listOfRoomPanel.updateUI();
        });

        joinToLobbyButton.addActionListener(e -> {
            for (JRadioButton currentRadioButton : jRadioButtonsArrayList) {
                // Search for selected room (there will be not too much client on the network so for cycle is enough)
                if (currentRadioButton.isSelected()) {
                    for (Room currentRoom : TankTrouble.mainGame.getListOfRemoteRooms()) {
                        if (currentRoom.name.equals(currentRadioButton.getActionCommand())){
                            joinChosenRoom(currentRoom);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * This function lists the remote rooms to the content panel as a JRadioButton
     */
    private void listRoomInThePanel() {
        ArrayList<Room> roomArrayList = TankTrouble.mainGame.getListOfRemoteRooms();
        int lengthOfRoomArrayList = roomArrayList.size();
        listOfRoomPanel.setLayout(new GridLayout(lengthOfRoomArrayList, 1));
        for (Room room : roomArrayList) {
            JRadioButton roomElement = new JRadioButton(room.name);
            roomElement.addActionListener(jRadioButtonActionListener);
            roomElement.setBackground(Color.white);
            jRadioButtonsArrayList.add(roomElement);
            listOfRoomPanel.add(roomElement);
        }
    }

    /**
     * This function will be called when the user click to the join to lobby button. This function calls the join
     * request in the network controller.
     * @param chosenRoom This parameter is the chosen room instance.
     */
    public void joinChosenRoom(Room chosenRoom) {
        boolean joinSuccessful = TankTrouble.mainGame.networkController.joinRoom(chosenRoom);

        if(joinSuccessful) {
            listRoomsWindowFrame.dispose();
            TankTrouble.waitForGameStartWindow = new WaitForGameStartWindow();
            return;
        }

        JOptionPane.showMessageDialog(null, "Server declined join request.");
    }

    /**
     *  This function dispose this ListRoomsWindow and set MainMenuWindow set to visible.
     */
    public void backToMainMenuWindow() {
        listRoomsWindowFrame.dispose();
        TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
    }
}
