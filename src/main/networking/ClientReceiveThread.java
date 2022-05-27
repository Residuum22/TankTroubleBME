package main.networking;

import main.TankTrouble;
import main.gui.GameWindow;
import main.model.Battlefield;
import main.model.Player;
import main.model.Tank;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ClientReceiveThread extends Thread{
    private final ObjectInputStream objectInputStream;
    private boolean isRunning;

    public ClientReceiveThread(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
        this.isRunning = true;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (this.isRunning) {
                try {
                    Message msg = (Message) this.objectInputStream.readObject();

                    switch (msg.type) {
                        case lobbyUpdate -> {
                            if(TankTrouble.waitForGameStartWindow != null) {
                                ArrayList<Player> players = (ArrayList<Player>) msg.data;
                                TankTrouble.waitForGameStartWindow.updateJoinedPlayerList(players);
                            }
                        }
                        case serverClosing -> {
                            JOptionPane.showMessageDialog(null, "Server stopped.");
                            if(TankTrouble.waitForGameStartWindow != null) {
                                TankTrouble.waitForGameStartWindow.leaveRoom();
                            }
                            if(TankTrouble.gameWindow != null) {
                                TankTrouble.gameWindow.getGameWindowFrame().dispose();
                            }
                        }
                        case serverStartingBattlefieldBuild -> {
                            BattlefieldBuildData battlefieldBuildData = (BattlefieldBuildData) msg.data;
                            TankTrouble.waitForGameStartWindow.remoteGameStarted();
                            if(TankTrouble.gameWindow != null) {
                                TankTrouble.gameWindow.getGameWindowFrame().dispose();
                                TankTrouble.gameWindow.clearBattlefield();
                            }
                            TankTrouble.gameWindow = new GameWindow();
                            TankTrouble.gameWindow.setBattleField(battlefieldBuildData.battlefield);
                            TankTrouble.gameWindow.getBattlefield().setListOfTanks(battlefieldBuildData.tanks);
                            TankTrouble.gameWindow.drawBattlefield();
                            TankTrouble.gameWindow.updateTank();
                            TankTrouble.gameWindow.getBattlefield().addPlayerTankControl();
                        }
                        case keyPressBroadcast -> {
                            PlayerKeyPress playerKeyPress = (PlayerKeyPress) msg.data;
                            for(Tank tank : TankTrouble.gameWindow.getBattlefield().getListOfTanks()) {
                                if(tank.owner.id == playerKeyPress.player.id) {
                                    switch (playerKeyPress.key) {
                                        case KeyEvent.VK_UP:
                                        case KeyEvent.VK_DOWN:
                                        case KeyEvent.VK_LEFT:
                                        case KeyEvent.VK_RIGHT:
                                            tank.moveTankToNextPosition(playerKeyPress.key);
                                            TankTrouble.gameWindow.updateTank();
                                            break;
                                        case KeyEvent.VK_SPACE:
                                            tank.shootMissile();
                                            break;
                                    }
                                }
                            }
                        }
                        case clientLeaving -> {
                            Player leaver = (Player) msg.data;
                            ArrayList<Tank> tanks = TankTrouble.gameWindow.getBattlefield().getListOfTanks();

                            for (Tank tank : tanks) {
                                if(tank.owner.id == leaver.id) {
                                    tank.destroyed = true;
                                }
                            }
                            TankTrouble.gameWindow.updateTank();
                            TankTrouble.gameWindow.removeTankFromList();
                        }
                    }
                } catch (SocketException e) {
                    if(this.isRunning) {
                        e.printStackTrace();
                    }
                } catch (SocketTimeoutException | EOFException ignored) {

                }

                synchronized (this) {
                    try {
                        this.wait(10);
                    } catch (InterruptedException e) {
                        this.isRunning = false;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopReceive() {
        this.isRunning = false;
    }
}
