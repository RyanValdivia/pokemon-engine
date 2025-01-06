package entity;

import java.awt.image.BufferedImage;

public class Entity {
    private int x, y;
    private int speed;

    private final BufferedImage[][] frames;

    private int spriteCounter;
    private int spriteNumber;

    private Direction direction;

    public Entity () {
        this.frames = new BufferedImage[4][4];
        this.spriteCounter = 0;
        this.spriteNumber = 0;
        this.x = 0;
        this.y = 0;
        this.speed = 0;
    }

    public int getX () {
        return x;
    }

    public void setX (int x) {
        this.x = x;
    }

    public int getY () {
        return y;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getSpeed () {
        return speed;
    }

    public void setSpeed (int speed) {
        this.speed = speed;
    }

    public BufferedImage[][] getFrames () {
        return frames;
    }

    public int getSpriteCounter () {
        return spriteCounter;
    }

    public void setSpriteCounter (int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNumber () {
        return spriteNumber;
    }

    public void setSpriteNumber (int spriteNumber) {
        this.spriteNumber = spriteNumber;
    }

    public Direction getDirection () {
        return direction;
    }

    public void setDirection (Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
