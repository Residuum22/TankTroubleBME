package main.gui;

import main.model.Battlefield;
import main.model.Field;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;



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
    Battlefield thisGameBattleField = new Battlefield();

    public GameWindow() {
        JFrame gameWindowFrame = new JFrame("Tank Trouble Game");
        gameWindowFrame.setSize(60 * scale, 30 * scale);
        gameWindowFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        gameWindowFrame.add(contentPanel);

        leaveButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                    ":(", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION){
                //Todo network controller action (put here)
                gameWindowFrame.dispose();
            }
        });
        contentPanel.setVisible(false);
        gameWindowFrame.setVisible(true);
    }


    public void drawBattlefield() {
        thisGameBattleField.generateBattleFieldPositioningXZCoordinate();
        Field[][] fields = thisGameBattleField.getFields();

        int mazeDimensionX = thisGameBattleField.getMazeDimensionX();
        int mazeDimensionY = thisGameBattleField.getMazeDimensionY();

        arenaPanel.setLayout(new GridLayout(mazeDimensionY, mazeDimensionX));
        for (int i = 0; i < mazeDimensionY; i++) {
            for (int j = 0; j < mazeDimensionX; j++) {
                JPanel temporaryField = new JPanel();
                if (fields[i][j].getType() == Field.FieldType.Wall)
                    temporaryField.setBackground(Color.black);
                else
                    temporaryField.setBackground(Color.white);
                arenaPanel.add(temporaryField);
            }
        }
        arenaPanel.revalidate();
        contentPanel.setVisible(true);
    }


    public enum barrierType {
        LWall,
        TWall,
        HLine,
        VLine
    }

    public int[][] generateBarries(barrierType barrier) {
        int dimension = 5;

        int[][] barrierMatrix = new int[dimension][dimension];
        for (int[] matrix : barrierMatrix) {
            matrix = new int[dimension];
        }

        int middle = dimension / 2;

        switch (barrier) {
            case HLine -> {
                Arrays.fill(barrierMatrix[middle], 1);
            }
            case LWall -> {
                Arrays.fill(barrierMatrix[0], 1);
                for (int i = 0; i < barrierMatrix.length; i++) {
                    barrierMatrix[i][0] = 1;
                }
            }
            case TWall -> {
                Arrays.fill(barrierMatrix[0], 1);
                for (int i = 0; i < barrierMatrix.length; i++) {
                    barrierMatrix[i][middle] = 1;
                }
            }
            case VLine -> {
                for (int i = 0; i < barrierMatrix.length; i++) {
                    barrierMatrix[i][middle] = 1;
                }
            }
            default -> {
                return null;
            }
        }
        return barrierMatrix;
    }

}
