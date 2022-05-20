package main.gui;

import main.TankTrouble;
import main.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WaitForGameStartWindow {
    JFrame waitForGameStartWindowFrame;
    private JPanel contentPanel;
    private JButton leaveButton;
    private JButton startGameButton;
    private JPanel playerListPanel;

    public WaitForGameStartWindow() {
        waitForGameStartWindowFrame = new JFrame("Lobby");
        waitForGameStartWindowFrame.setSize(1024, 720);
        waitForGameStartWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        if(!TankTrouble.mainGame.hasOwnRoom()) {
            startGameButton.setVisible(false);
        }

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
        TankTrouble.mainGame.networkController.leaveLobby();
        TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
    }

    public void startGame() {
        // Here i hide the window only so when the game is over set visible again.
        waitForGameStartWindowFrame.setVisible(false);
        TankTrouble.gameWindow = new GameWindow();
        TankTrouble.gameWindow.generateBattleField();
        TankTrouble.gameWindow.drawBattlefield();
        //Todo network controller
        TankTrouble.mainGame.networkController.broadcastGameStarting();
    }

    public void remoteGameStarted() {
        waitForGameStartWindowFrame.setVisible(false);
    }

    public void updateJoinedPlayerList(ArrayList<Player> list) {
        //todo network controller

        int lengthOfPlayerArrayList = list.size();
        playerListPanel.removeAll();
        playerListPanel.setLayout(new GridLayout(lengthOfPlayerArrayList, 1));
        for (Player currentPlayer : list) {
            JLabel playerElement = new JLabel(currentPlayer.name);
            playerElement.setBackground(Color.BLUE);
            playerListPanel.add(playerElement);
        }
        playerListPanel.updateUI();
    }
}
