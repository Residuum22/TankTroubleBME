package main.model;

import main.gui.GameWindow;

public class Missile extends MovingObject {
    public void updateMissilePosition() {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        switch (direction) {
            case Right -> {
                if (presentFields[this.position.getX() + 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX() + 1][this.position.getY()];
                } else {
                    //todo Check if there is a tank on that pixel
                }
            }
            case Left -> {
                if (presentFields[this.position.getX() - 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX() - 1][this.position.getY()];
                } else {
                    //todo Check if there is a tank on that pixel
                }
            }
            case Up -> {
                if (presentFields[this.position.getX()][this.position.getY() - 1].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX()][this.position.getY() - 1];
                } else {
                    //todo Check if there is a tank on that pixel
                }
            }
            case Down -> {
                if (presentFields[this.position.getX()][this.position.getY() + 1].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX()][this.position.getY() + 1];
                } else {
                    //todo Check if there is a tank on that pixel
                }
            }
        }
    }
}
