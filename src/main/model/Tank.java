package main.model;

import main.TankTrouble;
import main.gui.GameWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Random;

public class Tank extends MovingObject implements Serializable {
    private JLabel thisTankJLabel = new JLabel();

    public Tank(Player tankOwner) {
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

        this.owner = tankOwner;
    }

    public void addControl() {
        if (owner == TankTrouble.mainGame.getThisPlayer()) {
            thisTankJLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveRight");
            thisTankJLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveLeft");
            thisTankJLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "moveUp");
            thisTankJLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "moveDown");
            thisTankJLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "shootMissile");

            thisTankJLabel.getActionMap().put("moveRight", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    TankTrouble.networkController.sendKeyPress(KeyEvent.VK_RIGHT);
                    moveTankToNextPosition(KeyEvent.VK_RIGHT);
                    TankTrouble.gameWindow.updateTank();
                }
            });
            thisTankJLabel.getActionMap().put("moveLeft", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    TankTrouble.networkController.sendKeyPress(KeyEvent.VK_LEFT);
                    moveTankToNextPosition(KeyEvent.VK_LEFT);
                    TankTrouble.gameWindow.updateTank();
                }
            });
            thisTankJLabel.getActionMap().put("moveUp", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    TankTrouble.networkController.sendKeyPress(KeyEvent.VK_UP);
                    moveTankToNextPosition(KeyEvent.VK_UP);
                    TankTrouble.gameWindow.updateTank();
                }
            });
            thisTankJLabel.getActionMap().put("moveDown", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    TankTrouble.networkController.sendKeyPress(KeyEvent.VK_DOWN);
                    moveTankToNextPosition(KeyEvent.VK_DOWN);
                    TankTrouble.gameWindow.updateTank();
                }
            });
            thisTankJLabel.getActionMap().put("shootMissile", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    TankTrouble.networkController.sendKeyPress(KeyEvent.VK_SPACE);
                    shootMissile();
                }
            });
        }
    }

    public void shootMissile() {
        Battlefield presentBattlefield = GameWindow.getBattlefield();
        Field[][] presentFields = presentBattlefield.getFields();
        int TankXCoordinate = this.position.getX();
        int TankYCoordinate = this.position.getY();
        int missileNumber = 0;
        for (Missile missileObject : presentBattlefield.listOfMissiles) {
            if (missileObject.owner.equals(TankTrouble.mainGame.getThisPlayer())) {
                missileNumber++;
            }
        }
        if (missileNumber < 3) {
            Missile missile = new Missile();
            missile.direction = this.direction;
            missile.owner = TankTrouble.mainGame.getThisPlayer();
            switch (direction) {
                case Right -> {
                    if (presentFields[TankXCoordinate + 1][TankYCoordinate].getType() == Field.FieldType.Road) {
                        missile.position = presentFields[TankXCoordinate][TankYCoordinate];
                        GameWindow.setBattlefieldMissile(missile);
                    } else {
                        missile = null;
                    }
                }
                case Left -> {
                    if (presentFields[TankXCoordinate - 1][TankYCoordinate].getType() == Field.FieldType.Road) {
                        missile.position = presentFields[TankXCoordinate][TankYCoordinate];
                        GameWindow.setBattlefieldMissile(missile);
                    } else {
                        missile = null;
                    }
                }
                case Up -> {
                    if (presentFields[TankXCoordinate][TankYCoordinate - 1].getType() == Field.FieldType.Road) {
                        missile.position = presentFields[TankXCoordinate][TankYCoordinate];
                        GameWindow.setBattlefieldMissile(missile);
                    } else {
                        missile = null;
                    }
                }
                case Down -> {
                    if (presentFields[TankXCoordinate][TankYCoordinate + 1].getType() == Field.FieldType.Road) {
                        missile.position = presentFields[TankXCoordinate][TankYCoordinate];
                        GameWindow.setBattlefieldMissile(missile);
                    } else {
                        missile = null;
                    }
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
                        this.position = presentFields[this.position.getX() - 1][this.position.getY()];
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
        int option = JOptionPane.showConfirmDialog(null, "Do you want to spectate?",
                "DESTROYED ... ", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.NO_OPTION) {
            TankTrouble.gameWindow.getGameWindowFrame().dispose();
            TankTrouble.mainMenuWindow.setMainMenuWindowFrameVisible();
            //todo networkcontroller
        }
    }

    public JLabel getThisTankJLabel() {return this.thisTankJLabel;}

}
