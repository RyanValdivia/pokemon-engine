package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    private final GamePanel gamePanel;
    private final KeyHandler keyHandler;


    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.setDefaultValues();
        this.getPlayerImage();
    }

    public void setDefaultValues () {
        this.setX(100);
        this.setY(100);
        this.setSpeed(4);
        this.setDirection(Direction.DOWN);
        this.setSpriteCounter(0);
        this.setSpriteNumber(0);
    }

    public void update () {
        if (keyHandler.isNothingPressed()) {
            this.setSpriteNumber(0);
            return;
        }

        if (keyHandler.isUpPressed()) {
            this.setY(this.getY() - this.getSpeed());
            this.setDirection(Direction.UP);
        }
        if (keyHandler.isDownPressed()) {
            this.setY(this.getY() + this.getSpeed());
            this.setDirection(Direction.DOWN);
        }
        if (keyHandler.isLeftPressed()) {
            this.setX(this.getX() - this.getSpeed());
            this.setDirection(Direction.LEFT);
        }
        if (keyHandler.isRightPressed()) {
            this.setX(this.getX() + this.getSpeed());
            this.setDirection(Direction.RIGHT);
        }

        this.setSpriteCounter(this.getSpriteCounter() + 1);
        if (this.getSpriteCounter() > 12) {
            this.setSpriteNumber((this.getSpriteNumber() + 1) % 4);
            this.setSpriteCounter(0);
        }
    }

    public void getPlayerImage () {
        try {

            BufferedImage spriteSheet = ImageIO.read(new File("resources/player/player_boy.png"));

            int frameWidth = spriteSheet.getWidth() / 4;
            int frameHeight = spriteSheet.getHeight() / 4;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    this.getFrames()[i][j] = spriteSheet.getSubimage(j * frameWidth, i * frameHeight, frameWidth, frameHeight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2d) {
        BufferedImage image = switch (this.getDirection()) {
            case DOWN -> this.getFrames()[0][this.getSpriteNumber()];
            case LEFT -> this.getFrames()[1][this.getSpriteNumber()];
            case RIGHT -> this.getFrames()[2][this.getSpriteNumber()];
            case UP -> this.getFrames()[3][this.getSpriteNumber()];
        };
        g2d.drawImage(image, this.getX(), this.getY(), image.getWidth() * this.gamePanel.getSCALE(), image.getHeight() * this.gamePanel.getSCALE(), null);
    }
}
