package main.model;

public class MovingObject {
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
