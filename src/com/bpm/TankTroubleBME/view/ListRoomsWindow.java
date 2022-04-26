package com.bpm.TankTroubleBME.view;

import com.bpm.TankTroubleBME.model.Player;
import com.bpm.TankTroubleBME.model.Room;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class ListRoomsWindow {
    //todo clear this line if network controller is done
    private ArrayList<Room> roomArrayList = new ArrayList();

    //Todo add search button

    private JFrame listRoomsWindowFrame;
    private JPanel contentPanel;
    private JButton joinToLobbyButton;
    private JButton backButton;
    private JPanel listOfRoomPanel;

    public ListRoomsWindow() {
        listRoomsWindowFrame = new JFrame("Tank Trouble Game");
        listRoomsWindowFrame.setSize(1024, 720);
        listRoomsWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        //todo clear this line if network controller is done
        generateRoomList();


        backButton.addActionListener(e -> {
            backToMainMenuWindow();
        });

        listRoomsWindowFrame.add(contentPanel);
        listRoomsWindowFrame.setVisible(true);
    }

    public void updateRoomList(ArrayList<Room> newRoomList) {
        roomArrayList.removeAll(roomArrayList);
        roomArrayList.addAll(newRoomList);
        this.listRoomInThePanel();
    }

    private void listRoomInThePanel() {
        int lengthOfRoomArrayList = roomArrayList.size();
        listOfRoomPanel.setLayout(new GridLayout(lengthOfRoomArrayList, 1));
        for (Room room : roomArrayList) {
            JRadioButton roomElement = new JRadioButton(room.owner.name);
            roomElement.setBackground(Color.white);
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
    }

    //todo clear this line if network controller is done
    private void generateRoomList() {
        Room room1 = new Room(new Player("Mark's room"));
        Room room2 = new Room(new Player("Peti's room"));
        Room room3 = new Room(new Player("Boti's room"));
        roomArrayList.add(room1);
        roomArrayList.add(room2);
        roomArrayList.add(room3);
    }
}
