package main.networking;

import main.model.Battlefield;
import main.model.Tank;

import java.io.Serializable;
import java.util.ArrayList;

public class BattlefieldBuildData implements Serializable {
    public Battlefield battlefield;
    public ArrayList<Tank> tanks;

    public BattlefieldBuildData() {

    }

    public BattlefieldBuildData(Battlefield battlefield, ArrayList<Tank> tanks) {
        this.battlefield = battlefield;
        this.tanks = tanks;
    }
}
