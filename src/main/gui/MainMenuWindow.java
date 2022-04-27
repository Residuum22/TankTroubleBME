package main.gui;

import main.TankTrouble;
import main.gui.ListRoomsWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class MainMenuWindow {
    private JFrame mainMenuWindowFrame;
    private JButton changeUsernameButton;
    private JButton createLobbyButton;
    private JButton searchForLobbyButton;
    private JButton quitButton;
    private JPanel buttonPanel;
    private JPanel logoPanel;
    private JPanel contentPanel;
    private JPanel rightTankPanel;
    private JPanel leftTankPanel;
    private JLabel usernameLabel;


    public MainMenuWindow() {
        mainMenuWindowFrame = new JFrame("Tank Trouble Game");
        mainMenuWindowFrame.setSize(1024, 720);
        mainMenuWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setNewPlayerName();

        quitButton.addActionListener(e -> quit());

        changeUsernameButton.addActionListener(e -> {
            setNewPlayerName();
        });

        createLobbyButton.addActionListener(e -> {
            openCreateRoomWindow();
        });

        searchForLobbyButton.addActionListener(e -> {
            openListRoomsWindow();
        });

        loadMainMenuWindowImages();

        mainMenuWindowFrame.add(contentPanel);
        mainMenuWindowFrame.setVisible(true);
    }

    /**
     * This function loads images into the the MainMenuWindow
     */
    private void loadMainMenuWindowImages() {
        int imageWidth = 128;
        int imageHeight = 128;
        try {
            final String logoPath = "src\\main\\gui\\resources\\";
            BufferedImage mainMenuLogo;
            mainMenuLogo = ImageIO.read(new File(logoPath + "logo.png"));
            JLabel mainMenuLogoLabel = new JLabel(new ImageIcon(mainMenuLogo));
            logoPanel.add(mainMenuLogoLabel);

            BufferedImage leftTankImage;
            leftTankImage = ImageIO.read(new File(logoPath + "left_tank.png"));
            JLabel leftTankImageLabel = new JLabel(new ImageIcon(leftTankImage.getScaledInstance(imageWidth, imageHeight,
                    Image.SCALE_DEFAULT)));
            leftTankPanel.add(leftTankImageLabel);

            BufferedImage rightTankImage;
            rightTankImage = ImageIO.read(new File(logoPath + "right_tank.png"));

            JLabel rightTankImageLabel = new JLabel(new ImageIcon(rightTankImage.getScaledInstance(imageWidth,
                    imageHeight, Image.SCALE_DEFAULT)));
            rightTankPanel.add(rightTankImageLabel);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error occurred during loading main menu images.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            //TODO: Change this pane yes option to quit and add error handling.
        }
    }

    public void openCreateRoomWindow() {
        //TODO
    }

    public void openListRoomsWindow() {
        new ListRoomsWindow();
    }

    public void setNewPlayerName() {
        // Regex for name validation
        String validationRegex = "^[A-Za-z0-9_]{1,20}";
        Pattern usernamePattern = Pattern.compile(validationRegex);
        do {
            try {
                String temporaryUsername = JOptionPane.showInputDialog(null,
                        "Please enter your username!\nUse only lower and upper case characters from the english abc " +
                                "with numbers and underline. The username can't be more than 20 characters.");
                if ((temporaryUsername.length() <= 20) && (temporaryUsername.length() > 0) &&
                        (usernamePattern.matcher(temporaryUsername).matches())) {
                    usernameLabel.setText("Username: " + temporaryUsername);
                    TankTrouble.mainGame.modifyPlayerName(temporaryUsername);
                    createLobbyButton.setEnabled(true);
                    searchForLobbyButton.setEnabled(true);
                    break;
                }
            } catch (NullPointerException e) {
                if (TankTrouble.mainGame.getThisPlayerName() == null) {
                    usernameLabel.setText("Username: Please add username first!");
                }
                return;
            }
        } while (true);
    }

    public void quit() {
        System.exit(0);
    }
}
