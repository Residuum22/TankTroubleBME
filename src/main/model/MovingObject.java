package main.model;

import main.gui.GameWindow;

public class MovingObject {
    public enum Direction {
        Right,
        Left,
        Up,
        Down
    }

    public Field position;
    public Direction direction;
}
