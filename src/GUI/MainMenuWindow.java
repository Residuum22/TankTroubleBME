package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuWindow extends JFrame {
    private JButton createRoomButton;
    private JButton createLobbyButton;
    private JButton searchForLobbyButton;
    private JButton quitButton;
    private JPanel buttonPanel;
    private JPanel logoPanel;
    private JPanel contentPanel;
    private JPanel rightTankPanel;
    private JPanel leftTankPanel;

    public MainMenuWindow() {
        this.setTitle("Tank Trouble Game");
        this.setSize(1024, 720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadMainMenuWindowImages();

        this.add(contentPanel);
        this.setVisible(true);
    }

    /**
     * This function loads images into the the MainMenuWindow
     */
    private void loadMainMenuWindowImages() {
        int imageWidth = 128;
        int imageHeight = 128;
        try {
            BufferedImage mainMenuLogo;
            mainMenuLogo = ImageIO.read(new File("img/logo.png"));
            JLabel mainMenuLogoLabel = new JLabel(new ImageIcon(mainMenuLogo));
            logoPanel.add(mainMenuLogoLabel);

            BufferedImage leftTankImage;
            leftTankImage = ImageIO.read(new File("img/left_tank.png"));
            JLabel leftTankImageLabel = new JLabel(new ImageIcon(leftTankImage.getScaledInstance(imageWidth, imageHeight,
                    Image.SCALE_DEFAULT)));
            leftTankPanel.add(leftTankImageLabel);

            BufferedImage rightTankImage;
            rightTankImage = ImageIO.read(new File("img/right_tank.png"));

            JLabel rightTankImageLabel = new JLabel(new ImageIcon(rightTankImage.getScaledInstance(imageWidth,
                    imageHeight, Image.SCALE_DEFAULT)));
            rightTankPanel.add(rightTankImageLabel);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error occurred during loading main menu images.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            //TODO: Change this pane yes option to quit and add error handling.
        }
    }
}
