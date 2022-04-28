package main.gui;

import main.model.Battlefield;

import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private final int scale = 28;

    private JPanel contentPanel;
    private JPanel arenaPanel;
    Battlefield thisGameBattleField = new Battlefield();

    public GameWindow() {
        JFrame gameWindowFrame = new JFrame("Tank Trouble Game");
        gameWindowFrame.setSize(60 * scale, 30 * scale);
        gameWindowFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        gameWindowFrame.add(contentPanel);
        contentPanel.setVisible(false);
        gameWindowFrame.setVisible(true);
    }


    public void drawBattlefield() {
        thisGameBattleField.generateBattleFieldPositioningXZCoordinate();
        int[][] matrix = thisGameBattleField.getMatrix();

        int mazeDimensionX = thisGameBattleField.getMazeDimensionX();
        int mazeDimensionY = thisGameBattleField.getMazeDimensionY();

        arenaPanel.setLayout(new GridLayout(mazeDimensionY, mazeDimensionX));
        for (int i = 0; i < mazeDimensionY; i++) {
            for (int j = 0; j < mazeDimensionX; j++) {
                // Todo store it in a list with index.
                JPanel temporaryField = new JPanel();
                if (matrix[i][j] == 1)
                    temporaryField.setBackground(Color.black);
                else
                    temporaryField.setBackground(Color.white);
                arenaPanel.add(temporaryField);
            }
        }
        arenaPanel.revalidate();
        contentPanel.setVisible(true);
    }
}
