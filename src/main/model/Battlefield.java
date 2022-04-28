package main.model;

import java.util.ArrayList;
import java.util.Random;

public class Battlefield {
    int mazeDimensionX = 60;
    int mazeDimensionY = 30;
    int[][] matrix = new int[mazeDimensionY][mazeDimensionX];

    int currentDimY = 0;
    int currentDimX = 0;

    ArrayList<Field> fields = new ArrayList<>();
    // todo missile
    // todo tanks

    public Battlefield() {

    }

    public void generateBattleFieldPositioningXZCoordinate() {
        //Generate wall
        for (int i = 0; i < mazeDimensionX; i++) {
            for (int j = 0; j < mazeDimensionY; j++){
                if (i == 0 || j == 0 || i == mazeDimensionX - 1 || j == mazeDimensionY - 1)
                    matrix[j][i] = 1;
                else
                    matrix[j][i] = 0;
            }
        }

        int intRandomX, intRandomY;
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            intRandomX = rand.nextInt(mazeDimensionX);
            intRandomY = rand.nextInt(mazeDimensionY);
            matrix[intRandomY][intRandomX] = 1;
        }
        //printMaze();
    }

    private void printMaze() {
        for (int[] current : matrix) {
            for (int current2 : current)
            {
                if (current2 == 1)
                    System.out.print('⬛');
                else
                    System.out.print('⬜');
            }
            System.out.print('\n');
        }

    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getMazeDimensionX() {
        return mazeDimensionX;
    }

    public int getMazeDimensionY() {
        return mazeDimensionY;
    }
}
