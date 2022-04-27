package main.gui;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Objects;

public class ListRoomsWindow {
    //todo clear this line if network controller is done
    private ArrayList<Room> roomArrayList = new ArrayList();

    private JFrame listRoomsWindowFrame;
    private JPanel contentPanel;
    private JButton joinToLobbyButton;
    private JButton backButton;
    private JPanel listOfRoomPanel;
    private JButton searchForRoomsButton;

    private ArrayList<JRadioButton> jRadioButtonsArrayList = new ArrayList<>();

    public ListRoomsWindow() {
        listRoomsWindowFrame = new JFrame("Tank Trouble Game");
        listRoomsWindowFrame.setSize(1024, 720);
        listRoomsWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //todo clear this line if network controller is done
        updateRoomList(generateRoomList());

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


    private void addActionListeners() {
        listRoomsWindowFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
            }
        });

        backButton.addActionListener(e -> {
            backToMainMenuWindow();
        });

        searchForRoomsButton.addActionListener(e -> {
            joinToLobbyButton.setEnabled(false);
            for (JRadioButton currentButton : jRadioButtonsArrayList) {
                listOfRoomPanel.remove(currentButton);
            }
            //Todo should i do now? Send Join request but there is no network controller
            //Multithredding queue?
            listRoomInThePanel();
            listOfRoomPanel.updateUI();
        });

        joinToLobbyButton.addActionListener(e -> {
            //Todo: same as the last message
            joinChosenRoom();
        });
    }

    public void updateRoomList(ArrayList<Room> newRoomList) {
        roomArrayList.removeAll(roomArrayList);
        roomArrayList.addAll(newRoomList);
        //Todo how to send signal to this task to update gui? So mani question
    }

    private void listRoomInThePanel() {
        int lengthOfRoomArrayList = roomArrayList.size();
        listOfRoomPanel.setLayout(new GridLayout(lengthOfRoomArrayList, 1));
        jRadioButtonsArrayList.removeAll(jRadioButtonsArrayList);
        for (Room room : roomArrayList) {
            JRadioButton roomElement = new JRadioButton(room.owner.name);
            roomElement.addActionListener(jRadioButtonActionListener);
            roomElement.setBackground(Color.white);
            jRadioButtonsArrayList.add(roomElement);
            listOfRoomPanel.add(roomElement);
        }
    }

    public void joinChosenRoom() {

    }

    public ArrayList<Room> getRoomArrayList() {
        return this.roomArrayList;
    }

    public void backToMainMenuWindow() {
        listRoomsWindowFrame.dispose();
        TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
    }

    //todo clear this line if network controller is done
    private ArrayList<Room> generateRoomList() {
        ArrayList<Room> tmp = new ArrayList<>();
        Room room1 = new Room(new Player("Mark's room"));
        Room room2 = new Room(new Player("Peti's room"));
        Room room3 = new Room(new Player("Boti's room"));
        tmp.add(room1);
        tmp.add(room2);
        tmp.add(room3);
        return tmp;
    }
}
