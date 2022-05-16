package main.gui;

import main.TankTrouble;
import main.model.Battlefield;
import main.model.Field;
import main.model.Missile;
import main.model.Tank;

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

    private ArrayList<JPanel> jPanelArrayList = new ArrayList<>();

    public GameWindow() {
        JFrame gameWindowFrame = new JFrame("Tank Trouble Game");
        gameWindowFrame.setSize(60 * scale, 30 * scale);
        gameWindowFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        gameWindowFrame.add(contentPanel);

        leaveButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                    ":(", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                //Todo network controller action (put here)
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


    public void drawBattlefield() {
        jPanelArrayList.removeAll(jPanelArrayList);

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
                jPanelArrayList.add(temporaryField);
            }
        }

        //todo postprocess
        for (Tank currentTank :
                currentTankList) {

            Field currentTankField = currentTank.getTankPosition();
            JPanel tmpJPanel = new JPanel();
            tmpJPanel.setMaximumSize(new Dimension(16, 16));
            tmpJPanel.setMinimumSize(new Dimension(16, 16));
            tmpJPanel.setPreferredSize(new Dimension(16, 16));
            try {
                final String logoPath = "src\\main\\gui\\resources\\";
                BufferedImage mainMenuLogo;
                mainMenuLogo = ImageIO.read(new File(logoPath + "very_very_low_effort_tank.png"));
                JLabel mainMenuLogoLabel = new JLabel(new ImageIcon(mainMenuLogo.getScaledInstance(16,16, Image.SCALE_DEFAULT)));
                tmpJPanel.add(mainMenuLogoLabel);
            } catch (IOException ioe) {
                //todo
            }
            jPanelArrayList.set((currentTankField.getX() - 1) * (currentTankField.getY() - 1), tmpJPanel);

            //todo put
        }

        for (Missile currentMissile :
                currentMissileList) {

        }

        for (JPanel thisField:
             jPanelArrayList) {
            arenaPanel.add(thisField);
        }

        arenaPanel.revalidate();
        contentPanel.setVisible(true);
    }

    public static Battlefield getBattlefield() {
        return thisGameBattleField;
    }

    public static void setBattlefieldMissile(Missile newMissile) {
        thisGameBattleField.listOfMissiles.add(newMissile);
    }
}
