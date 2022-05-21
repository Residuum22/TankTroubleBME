package main.model;

import main.gui.GameWindow;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank extends MovingObject {
    public Player owner;

    private JLabel thisTankJLabel = new JLabel();

    public Tank() {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        Random rand = new Random();
        int RandomXCoordinate = rand.nextInt(presentBattlefield.getMazeDimensionX());
        int RandomYCoordinate = rand.nextInt(presentBattlefield.getMazeDimensionY());
        if (presentFields[RandomXCoordinate][RandomYCoordinate].getType() != Field.FieldType.Road) {
            while (presentFields[RandomXCoordinate][RandomYCoordinate].getType() == Field.FieldType.Wall) {
                RandomXCoordinate = rand.nextInt(presentBattlefield.getMazeDimensionX());
                RandomYCoordinate = rand.nextInt(presentBattlefield.getMazeDimensionY());
            }
        }
        this.position = presentFields[RandomXCoordinate][RandomYCoordinate];

        int directionTypeNum = rand.nextInt(4);
        switch (directionTypeNum) {
            case 0 -> this.direction = Direction.Right;
            case 1 -> this.direction = Direction.Left;
            case 2 -> this.direction = Direction.Up;
            case 3 -> this.direction = Direction.Down;
        }

        this.destroyed = false;
        this.owner = null;
    }

    public void shootMissile() {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        int TankXCoordinate = this.position.getX();
        int TankYCoordinate = this.position.getY();
        Missile missile = new Missile();
        missile.direction = this.direction;
        switch (direction) {
            case Right -> {
                if (presentFields[TankXCoordinate + 1][TankYCoordinate].getType() == Field.FieldType.Road) {
                    missile.position = presentFields[TankXCoordinate + 1][TankYCoordinate];
                    GameWindow.setBattlefieldMissile(missile);
                } else {
                    missile = null;
                }
            }
            case Left -> {
                if (presentFields[TankXCoordinate - 1][TankYCoordinate].getType() == Field.FieldType.Road) {
                    missile.position = presentFields[TankXCoordinate - 1][TankYCoordinate];
                    GameWindow.setBattlefieldMissile(missile);
                } else {
                    missile = null;
                }
            }
            case Up -> {
                if (presentFields[TankXCoordinate][TankYCoordinate - 1].getType() == Field.FieldType.Road) {
                    missile.position = presentFields[TankXCoordinate][TankYCoordinate - 1];
                    GameWindow.setBattlefieldMissile(missile);
                } else {
                    missile = null;
                }
            }
            case Down -> {
                if (presentFields[TankXCoordinate][TankYCoordinate + 1].getType() == Field.FieldType.Road) {
                    missile.position = presentFields[TankXCoordinate][TankYCoordinate + 1];
                    GameWindow.setBattlefieldMissile(missile);
                } else {
                    missile = null;
                }
            }
        }
    }

    public void moveTankToNextPosition(int KeyCode) {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        boolean thereIsTank = false;
        if (KeyCode == KeyEvent.VK_RIGHT) {
            if (this.direction == Direction.Right) {
                if (presentFields[this.position.getX() + 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(presentFields[this.position.getX() + 1][this.position.getY()])) {
                            thereIsTank = true;
                            break;
                        }
                    }
                    if(!thereIsTank) {
                        this.position = presentFields[this.position.getX() + 1][this.position.getY()];
                    }
                }
            } else {
                this.direction = Direction.Right;
            }
        } else if (KeyCode == KeyEvent.VK_LEFT) {
            if (this.direction == Direction.Left) {
                if (presentFields[this.position.getX() - 1][this.position.getY()].getType() == Field.FieldType.Road) {
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(presentFields[this.position.getX() - 1][this.position.getY()])) {
                            thereIsTank = true;
                            break;
                        }
                    }
                    if(!thereIsTank) {
                        this.position = presentFields[this.position.getX() - 1][this.position.getY()];;
                    }
                }
            } else {
                this.direction = Direction.Left;
            }
        } else if (KeyCode == KeyEvent.VK_UP) {
            if (this.direction == Direction.Up) {
                if (presentFields[this.position.getX()][this.position.getY() - 1].getType() == Field.FieldType.Road) {
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(presentFields[this.position.getX()][this.position.getY() - 1])) {
                            thereIsTank = true;
                            break;
                        }
                    }
                    if(!thereIsTank) {
                        this.position = presentFields[this.position.getX()][this.position.getY() - 1];
                    }
                }
            } else {
                this.direction = Direction.Up;
            }
        } else if (KeyCode == KeyEvent.VK_DOWN) {
            if (this.direction == Direction.Down) {
                if (presentFields[this.position.getX()][this.position.getY() + 1].getType() == Field.FieldType.Road) {
                    for (int i = 0; i < presentBattlefield.listOfTanks.size(); i++) {
                        if (presentBattlefield.listOfTanks.get(i).position.equals(presentFields[this.position.getX()][this.position.getY() + 1])) {
                            thereIsTank = true;
                            break;
                        }
                    }
                    if(!thereIsTank) {
                        this.position = presentFields[this.position.getX()][this.position.getY() + 1];
                    }
                }
            } else {
                this.direction = Direction.Down;
            }
        }
    }

    public Field getTankPosition() {
        return this.position;
    }

    public Direction getTankDirection() {
        return this.direction;
    }

    public void destroyTank() {
        this.destroyed = true;
        //todo question "Leave or stay?"
    }

    public JLabel getThisTankJLabel() {return this.thisTankJLabel;}

}
