package main.model;

import java.io.Serializable;

/**
 * This class stores the field element in the battlefield. This class store the concrete coordiante with the field type
 * which can be wall or road.
 */
public class Field implements Serializable {
    public enum FieldType {
        Road,
        Wall
    }

    private int x, y;
    private FieldType type;

    public void setType(FieldType type) {
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
