package main.model;

import java.io.Serializable;

public class MovingObject implements Serializable {
    public enum Direction {
        Right,
        Left,
        Up,
        Down
    }

    public Field position;
    public Direction direction;
    public boolean destroyed;
    public Player owner;
}
