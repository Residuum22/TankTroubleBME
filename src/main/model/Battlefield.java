package main.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;


public class Battlefield implements Serializable {
    /**
     * Attention! Attention!
     * TODO we must review this issue in the test phase.
     * This variable will be specific to the screen
     */
    int mazeDimensionX = 60;
    int mazeDimensionY = 30;
    int numberOfBarrier = 30;
    int barrierDimension = 5;

    Field[][] fields = new Field[mazeDimensionX][mazeDimensionY];
    public ArrayList<Missile> listOfMissiles = new ArrayList<>();
    public ArrayList<Tank> listOfTanks = new ArrayList<>();

    //todo network controller event handling

    public enum barrierType {
        LWall,
        TWall,
        HLine,
        VLine
    }

    /**
     * This function genereate a wall around the arena. So it itarates all around the arena and search for the 0 and max
     * coordinates. Where this algorithm's condition is true, there the field became wall.
     * In the second phase this function generate barriers in the arena. The barrier coordinate is randomly generated.
     */
    public void generateBattleFieldPositioningXYCoordinate() {
        //Generate wall around the arena.
        for (int i = 0; i < mazeDimensionX; i++) {
            for (int j = 0; j < mazeDimensionY; j++) {
                fields[i][j] = new Field();
                if (i == 0 || j == 0 || i == mazeDimensionX - 1 || j == mazeDimensionY - 1) {
                    fields[i][j].setType(Field.FieldType.Wall);
                } else {
                    fields[i][j].setType(Field.FieldType.Road);
                }
                fields[i][j].setX(i);
                fields[i][j].setY(j);
            }
        }

        int intRandomX, intRandomY, barrierTypeNum;
        Random rand = new Random();
        for (int i = 0; i < numberOfBarrier; i++) {
            intRandomX = rand.nextInt(mazeDimensionX);
            intRandomY = rand.nextInt(mazeDimensionY);

            barrierTypeNum = rand.nextInt(4);
            switch (barrierTypeNum) {
                case 0 -> mergeSubcoordsIntoTheBattleField(intRandomX, intRandomY, generateBarrier(barrierType.LWall));
                case 1 -> mergeSubcoordsIntoTheBattleField(intRandomX, intRandomY, generateBarrier(barrierType.TWall));
                case 2 -> mergeSubcoordsIntoTheBattleField(intRandomX, intRandomY, generateBarrier(barrierType.HLine));
                case 3 -> mergeSubcoordsIntoTheBattleField(intRandomX, intRandomY, generateBarrier(barrierType.VLine));
            }
        }
    }

    public Field[][] getFields() {
        return fields;
    }

    public int getMazeDimensionX() {
        return mazeDimensionX;
    }

    public int getMazeDimensionY() {
        return mazeDimensionY;
    }

    public ArrayList<Tank> getListOfTanks() {
        return listOfTanks;
    }

    public ArrayList<Missile> getListOfMissiles() {
        return listOfMissiles;
    }

    public int[][] generateBarrier(barrierType barrier) {


        int[][] barrierMatrix = new int[barrierDimension][barrierDimension];

        int middle = barrierDimension / 2;

        switch (barrier) {
            case HLine -> {
                Arrays.fill(barrierMatrix[middle], 1);
            }
            case LWall -> {
                Arrays.fill(barrierMatrix[0], 1);
                for (int i = 0; i < barrierDimension; i++) {
                    barrierMatrix[i][0] = 1;
                }
            }
            case TWall -> {
                Arrays.fill(barrierMatrix[0], 1);
                for (int i = 0; i < barrierDimension; i++) {
                    barrierMatrix[i][middle] = 1;
                }
            }
            case VLine -> {
                for (int i = 0; i < barrierDimension; i++) {
                    barrierMatrix[i][middle] = 1;
                }
            }
        }
        return barrierMatrix;
    }

    public void mergeSubcoordsIntoTheBattleField(int starX, int starY, int[][] subCoord) {
        for (int i = starX, subi = 0; (i < this.mazeDimensionX) && (subi < barrierDimension); i++, subi++) {
            for (int j = starY, subj = 0; j < this.mazeDimensionY && subj < barrierDimension; j++, subj++) {
                if (subCoord[subi][subj] == 1) {
                    fields[i][j].setType(Field.FieldType.Wall);
                }
            }
        }
    }
}
