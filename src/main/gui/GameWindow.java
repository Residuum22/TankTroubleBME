package main.gui;

import main.TankTrouble;
import main.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GameWindow {
    /**
     * Important note!
     * How to use this class?
     * Make an object e.g: GameWindow game = new GameWindow();
     * Draw battlefield e.g: game.drawBattlefield();
     */
    private final int scale = 28;

    private JPanel contentPanel;
    private JPanel arenaPanel;
    private JButton leaveButton;
    static Battlefield thisGameBattleField = new Battlefield();

    private JPanel[][] jPanels = new JPanel[thisGameBattleField.getMazeDimensionX()][thisGameBattleField.getMazeDimensionY()];

    public GameWindow() {
        JFrame gameWindowFrame = new JFrame("Tank Trouble Game - " + TankTrouble.mainGame.getThisPlayerName());
        gameWindowFrame.setSize(60 * scale, 30 * scale);
        gameWindowFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        gameWindowFrame.add(contentPanel);

        leaveButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                    ":(", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION){
                TankTrouble.mainGame.networkController.leaveGame();
                gameWindowFrame.dispose();
                TankTrouble.waitForGameStartWindow.setWaitForGameStartWindowFrameVisible();
            }
        });
        contentPanel.setVisible(false);
        gameWindowFrame.setVisible(true);
        gameWindowFrame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                for (int i = 0; i < thisGameBattleField.listOfTanks.size(); i++) {
                    if (thisGameBattleField.listOfTanks.get(i).owner.name.equals(TankTrouble.mainGame.getThisPlayerName())) {
                        switch (keyCode) {
                            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN -> thisGameBattleField.listOfTanks.get(i).moveTankToNextPosition(keyCode);
                            case KeyEvent.VK_SPACE -> thisGameBattleField.listOfTanks.get(i).shootMissile();
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void generateBattleField() {
        thisGameBattleField.generateBattleFieldPositioningXZCoordinate();
    }

    public Battlefield getBattleField() {
        return thisGameBattleField;
    }

    public void setBattleField(Battlefield newBattlefield) {
        this.thisGameBattleField = newBattlefield;
    }

    public void drawBattlefield() {
        thisGameBattleField.generateBattleFieldPositioningXYCoordinate();
        Field[][] fields = thisGameBattleField.getFields();

        int mazeDimensionX = thisGameBattleField.getMazeDimensionX();
        int mazeDimensionY = thisGameBattleField.getMazeDimensionY();

        arenaPanel.removeAll();
        arenaPanel.setLayout(new GridLayout(mazeDimensionY, mazeDimensionX));

        ArrayList<Tank> currentTankList = thisGameBattleField.getListOfTanks();
        ArrayList<Missile> currentMissileList = thisGameBattleField.getListOfMissiles();

        for (int i = 0; i < mazeDimensionY; i++) {
            for (int j = 0; j < mazeDimensionX; j++) {
                JPanel temporaryField = new JPanel();
                temporaryField.setMaximumSize(new Dimension(16, 16));
                temporaryField.setMinimumSize(new Dimension(16, 16));
                temporaryField.setPreferredSize(new Dimension(16, 16));
                if (fields[j][i].getType() == Field.FieldType.Wall)
                    temporaryField.setBackground(Color.black);
                else
                    temporaryField.setBackground(Color.white);
                jPanels[j][i] = temporaryField;
                arenaPanel.add(jPanels[j][i]);
            }
        }
        arenaPanel.revalidate();
        contentPanel.setVisible(true);
    }

    public static Battlefield getBattlefield() {
        return thisGameBattleField;
    }

    public void updateScreen() {
        this.updateTank();
        this.updateMissile();
    }

    public void updateTank() {
        for (Tank currentTank :
                TankTrouble.myBattlefield.listOfTanks) {

            Field currentTankField = currentTank.getTankPosition();
            JPanel tmpJPanel = jPanels[currentTankField.getX()][currentTankField.getY()];

            if (currentTank.owner == TankTrouble.mainGame.getThisPlayer())
                tmpJPanel.setBackground(Color.red);
            else
                tmpJPanel.setBackground(Color.blue);

            tmpJPanel.setMaximumSize(new Dimension(16, 16));
            tmpJPanel.setMinimumSize(new Dimension(16, 16));
            tmpJPanel.setPreferredSize(new Dimension(16, 16));

            JLabel mainMenuLogoLabel = currentTank.getThisTankJLabel();
            try {
                mainMenuLogoLabel.getParent().setBackground(Color.white);
            } catch (NullPointerException e) {
                // First run there will be this
            }
            arenaPanel.remove(mainMenuLogoLabel);

            try {
                final String logoPath = "src\\main\\gui\\resources\\";
                BufferedImage mainMenuLogo;
                mainMenuLogo = ImageIO.read(new File(logoPath + "very_very_low_effort_tank.png"));
                mainMenuLogo = rotateTankImage(mainMenuLogo, currentTank);
                mainMenuLogoLabel.setIcon(new ImageIcon(mainMenuLogo.getScaledInstance(24, 24, Image.SCALE_DEFAULT)));
                tmpJPanel.add(mainMenuLogoLabel);
            } catch (IOException ioe) {
                // Should never happen during regular use
            }
        }
        arenaPanel.revalidate();
        arenaPanel.repaint();
    }

    public void updateMissile() {
        for (Missile currentMissile :
                TankTrouble.myBattlefield.listOfMissiles) {

            Field currentMissileField = currentMissile.getMissilePosition();
            JPanel tmpJPanel = jPanels[currentMissileField.getX()][currentMissileField.getY()];

            tmpJPanel.setMaximumSize(new Dimension(16, 16));
            tmpJPanel.setMinimumSize(new Dimension(16, 16));
            tmpJPanel.setPreferredSize(new Dimension(16, 16));

            JLabel mainMenuLogoLabel = currentMissile.getThisMissileJLabel();
            try {
                mainMenuLogoLabel.getParent().setBackground(Color.white);
            } catch (NullPointerException e) {
                // First run there will be this
            }
            arenaPanel.remove(mainMenuLogoLabel);

            try {
                final String logoPath = "src\\main\\gui\\resources\\";
                BufferedImage mainMenuLogo;
                mainMenuLogo = ImageIO.read(new File(logoPath + "very_very_low_effort_missile.png"));
                mainMenuLogoLabel.setIcon(new ImageIcon(mainMenuLogo.getScaledInstance(16, 16, Image.SCALE_DEFAULT)));
                tmpJPanel.add(mainMenuLogoLabel);
            } catch (IOException ioe) {
                // Should never happen during regular use
            }
        }
        arenaPanel.revalidate();
        arenaPanel.repaint();
    }

    public static void setBattlefieldMissile(Missile newMissile) {
        thisGameBattleField.listOfMissiles.add(newMissile);
    }

    private static BufferedImage rotateTankImage(BufferedImage bimg, Tank currentTank) {
        int angle = 0;
        switch (currentTank.direction) {
            case Up -> angle = 180;
            case Down -> angle = 0;
            case Left -> angle = 90;
            case Right -> angle = -90;
        }

        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin),
                newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww - w) / 2, (newh - h) / 2);
        graphic.rotate(Math.toRadians(angle), w / 2, h / 2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }
}
