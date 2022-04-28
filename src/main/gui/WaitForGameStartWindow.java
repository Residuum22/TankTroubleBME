package main.gui;

import javax.swing.*;

public class WaitForGameStartWindow {
    private JPanel contentPanel;
    private JButton leaveButton;
    private JButton startGameButton;
    private JPanel playerListPanel;

    public WaitForGameStartWindow() {
        JFrame waitForGameStartWindowFrame = new JFrame("Lobby");
        waitForGameStartWindowFrame.setSize(1024, 720);
        waitForGameStartWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        waitForGameStartWindowFrame.add(contentPanel);
        waitForGameStartWindowFrame.setVisible(true);

    }
}
