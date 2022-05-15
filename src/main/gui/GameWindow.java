package main.gui;

import main.TankTrouble;
import main.model.Battlefield;
import main.model.Field;
import main.model.Missile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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
                            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN ->
                                    thisGameBattleField.listOfTanks.get(i).moveTankToNextPosition(keyCode);
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
        thisGameBattleField.generateBattleFieldPositioningXYCoordinate();
        Field[][] fields = thisGameBattleField.getFields();

        int mazeDimensionX = thisGameBattleField.getMazeDimensionX();
        int mazeDimensionY = thisGameBattleField.getMazeDimensionY();

        arenaPanel.removeAll();
        arenaPanel.setLayout(new GridLayout(mazeDimensionY, mazeDimensionX));
        for (int i = 0; i < mazeDimensionY; i++) {
            for (int j = 0; j < mazeDimensionX; j++) {
                JPanel temporaryField = new JPanel();
                if (fields[j][i].getType() == Field.FieldType.Wall)
                    temporaryField.setBackground(Color.black);
                else
                    temporaryField.setBackground(Color.white);
                arenaPanel.add(temporaryField);
            }
        }
        arenaPanel.revalidate();
        contentPanel.setVisible(true);
    }

    public static Battlefield getBattlefield() {
        return thisGameBattleField;
    }

    public static void setBattlefieldMissile( Missile newMissile ) {
        thisGameBattleField.listOfMissiles.add(newMissile);
    }
}
