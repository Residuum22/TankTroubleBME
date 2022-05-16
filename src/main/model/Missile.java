package main.model;

import main.gui.GameWindow;

import javax.swing.*;

public class Missile extends MovingObject {
    JLabel thisMissleJLabel = new JLabel();

    public Missile () {
        this.direction = null;
        this.position = null;
    }

    public void updateMissilePosition() {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        int deletedMissileIndex = 0;
        int deletedTankIndex = 0;
        switch (this.direction) {
            case Right -> {
                if (presentFields[this.position.getX() + 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX() + 1][this.position.getY()];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            deletedTankIndex = i;
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    deletedMissileIndex = j;
                                }
                            }
                            presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                            presentBattlefield.listOfTanks.get(i).destroyTank(this);
                        }
                    }
                    if (presentBattlefield.listOfTanks.get(deletedTankIndex).destroyed){
                        presentBattlefield.listOfTanks.remove(deletedTankIndex);
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            deletedMissileIndex = i;
                        }
                    }
                    presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                }
            }
            case Left -> {
                if (presentFields[this.position.getX() - 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX() - 1][this.position.getY()];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            deletedTankIndex = i;
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    deletedMissileIndex = j;
                                }
                            }
                            presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                            presentBattlefield.listOfTanks.get(i).destroyTank(this);
                        }
                    }
                    if (presentBattlefield.listOfTanks.get(deletedTankIndex).destroyed){
                        presentBattlefield.listOfTanks.remove(deletedTankIndex);
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            deletedMissileIndex = i;
                        }
                    }
                    presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                }
            }
            case Up -> {
                if (presentFields[this.position.getX()][this.position.getY() - 1].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX()][this.position.getY() - 1];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        System.out.print(presentBattlefield.listOfTanks.get(i).position);
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            deletedTankIndex = i;
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    deletedMissileIndex = j;
                                }
                            }
                            presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                            presentBattlefield.listOfTanks.get(i).destroyTank(this);
                        }
                    }
                    if (presentBattlefield.listOfTanks.get(deletedTankIndex).destroyed){
                        presentBattlefield.listOfTanks.remove(deletedTankIndex);
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            deletedMissileIndex = i;
                        }
                    }
                    presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                }
            }
            case Down -> {
                if (presentFields[this.position.getX()][this.position.getY() + 1].getType() == Field.FieldType.Road) {
                    this.position = presentFields[this.position.getX()][this.position.getY() + 1];
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(this.position)) {
                            deletedTankIndex = i;
                            for (int j = 0; j < presentBattlefield.listOfMissiles.size(); j++){
                                if (presentBattlefield.listOfMissiles.get(j).position.equals(this.position)) {
                                    deletedMissileIndex = j;
                                }
                            }
                            presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                            presentBattlefield.listOfTanks.get(i).destroyTank(this);
                        }
                    }
                    if (presentBattlefield.listOfTanks.get(deletedTankIndex).destroyed){
                        presentBattlefield.listOfTanks.remove(deletedTankIndex);
                    }
                } else {
                    for (int i = 0; i < presentBattlefield.listOfMissiles.size(); i++) {
                        if (presentBattlefield.listOfMissiles.get(i).position.equals(this.position)) {
                            deletedMissileIndex = i;
                        }
                    }
                    presentBattlefield.listOfMissiles.remove(deletedMissileIndex);
                }
            }
        }
    }

    public Field getMissilePosition() {return this.position;}

    public JLabel getThisMissileJLabel() {return this.thisMissleJLabel;}
}
