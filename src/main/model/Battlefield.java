package main.model;

import java.util.Random;

public class Battlefield {
    /**
     * Attention! Attention!
     * TODO we must review this issue in the test phase.
     * This variable will be specific to the screen
     *
     */
    int mazeDimensionX = 60;
    int mazeDimensionY = 30;
    int numberOfBarrier = 50;

    Field[][] fields = new Field[mazeDimensionY][mazeDimensionX];
    // todo missile
    // todo tanks

    /**
     * This function genereate a wall around the arena. So it itarates all around the arena and search for the 0 and max
     * coordinates. Where this algorithm's condition is true, there the field became wall.
     * In the second phase this function generate barriers in the arena. The barrier coordinate is randomly generated.
     */
    public void generateBattleFieldPositioningXZCoordinate() {
        //Generate wall around the arena.
        for (int i = 0; i < mazeDimensionX; i++) {
            for (int j = 0; j < mazeDimensionY; j++) {
                fields[j][i] = new Field();
                if (i == 0 || j == 0 || i == mazeDimensionX - 1 || j == mazeDimensionY - 1) {
                    fields[j][i].setType(Field.FieldType.Wall);
                } else {
                    fields[j][i].setType(Field.FieldType.Road);
                }
                fields[j][i].setX(i);
                fields[j][i].setY(j);
            }
        }

        // Generate random barriers in the arena.
        int intRandomX, intRandomY;
        Random rand = new Random();
        for (int i = 0; i < numberOfBarrier; i++) {
            intRandomX = rand.nextInt(mazeDimensionX);
            intRandomY = rand.nextInt(mazeDimensionY);
            fields[intRandomY][intRandomX].setType(Field.FieldType.Wall);
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
}
