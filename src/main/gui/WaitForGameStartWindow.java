package main.gui;

import main.TankTrouble;
import main.model.Player;
import main.model.Room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WaitForGameStartWindow {
    JFrame waitForGameStartWindowFrame;
    private JPanel contentPanel;
    private JButton leaveButton;
    private JButton startGameButton;
    private JPanel playerListPanel;

    ArrayList<Player> joinedPlayerList = new ArrayList<>();

    public WaitForGameStartWindow() {
        waitForGameStartWindowFrame = new JFrame("Lobby");
        waitForGameStartWindowFrame.setSize(1024, 720);
        waitForGameStartWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        leaveButton.addActionListener(e -> {
            leaveRoom();
        });

        startGameButton.addActionListener(e -> {
            startGame();
        });

        waitForGameStartWindowFrame.add(contentPanel);
        waitForGameStartWindowFrame.setVisible(true);
    }

    public void setWaitForGameStartWindowFrameVisible() {waitForGameStartWindowFrame.setVisible(true);}

    public void leaveRoom() {
        //Todo network controller
        waitForGameStartWindowFrame.dispose();
        TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
    }

    public void startGame() {
        // Here i hide the window only so when the game is over set visible again.
        waitForGameStartWindowFrame.setVisible(false);
        GameWindow gameWindow = new GameWindow();
        gameWindow.drawBattlefield();
        //Todo network controller
    }

    public void updateJoinedPlayerList(ArrayList<Player> updatedList) {
        joinedPlayerList.removeAll(joinedPlayerList);
        joinedPlayerList.addAll(updatedList);

        int lengthOfPlayerArrayList = joinedPlayerList.size();
        playerListPanel.setLayout(new GridLayout(lengthOfPlayerArrayList, 1));
        for (Player currentPlayer : joinedPlayerList) {
            JLabel playerElement = new JLabel(currentPlayer.name);
            playerElement.setBackground(Color.white);
            playerListPanel.add(playerElement);
        }
    }
}
