package main.model;

import main.gui.GameWindow;

import javax.swing.*;

public class Missile extends MovingObject {
    JLabel thisMissleJLabel = new JLabel();

    public Missile () {

    }

    public void updateMissilePosition() {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        switch (this.direction) {
            case Right -> {
                if (presentFields[this.position.getX() + 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX() + 1][this.position.getY()];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfTanks.get(i).destroyTank();
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    presentBattlefield.listOfMissiles.get(j).destroyMissile();
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfMissiles.get(i).destroyMissile();
                        }
                    }
                }
            }
            case Left -> {
                if (presentFields[this.position.getX() - 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX() - 1][this.position.getY()];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfTanks.get(i).destroyTank();
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    presentBattlefield.listOfMissiles.get(j).destroyMissile();
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfMissiles.get(i).destroyMissile();
                        }
                    }
                }
            }
            case Up -> {
                if (presentFields[this.position.getX()][this.position.getY() - 1].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX()][this.position.getY() - 1];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfTanks.get(i).destroyTank();
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    presentBattlefield.listOfMissiles.get(j).destroyMissile();
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfMissiles.get(i).destroyMissile();
                        }
                    }
                }
            }
            case Down -> {
                if (presentFields[this.position.getX()][this.position.getY() + 1].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX()][this.position.getY() + 1];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfTanks.get(i).destroyTank();
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++) {
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    presentBattlefield.listOfMissiles.get(j).destroyMissile();
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            presentBattlefield.listOfMissiles.get(i).destroyMissile();
                        }
                    }
                }
            }
        }
    }

    public Field getMissilePosition() {return this.position;}

    public JLabel getThisMissileJLabel() {return this.thisMissleJLabel;}

    public void destroyMissile() {
        this.destroyed = true;
    }
}
