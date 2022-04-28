package main.model;

public class Field {
    enum FieldType {
        Road,
        Wall
    }

    Field() {

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
