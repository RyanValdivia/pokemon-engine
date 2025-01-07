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

    private final int screenX;
    private final int screenY;

    private int spriteFramerate = 12;

    private final int defaultSpeed = 4;

    private final BufferedImage[][] runningFrames;


    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        this.runningFrames = new BufferedImage[4][4];

        this.screenX = this.gamePanel.getWIDTH() / 2 - (gamePanel.getTILE_SIZE() / 2);
        this.screenY = this.gamePanel.getHEIGHT() / 2 - (gamePanel.getTILE_SIZE() / 2) - 32;

        this.setDefaultValues();
        this.getPlayerImage();
    }

    public void setDefaultValues () {
        this.setX(25 * this.gamePanel.getTILE_SIZE());
        this.setY((7 * this.gamePanel.getTILE_SIZE()) - 32);
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
        if (keyHandler.isZPressed()) {
            this.setSpeed((int) (defaultSpeed * 1.5));
            this.spriteFramerate = 4;
        } else {
            this.setSpeed(defaultSpeed);
            this.spriteFramerate = 12;
        }

        this.setSpriteCounter(this.getSpriteCounter() + 1);
        if (this.getSpriteCounter() > this.spriteFramerate) {
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

            spriteSheet = ImageIO.read(new File("resources/player/player_boy_run.png"));

            frameWidth = spriteSheet.getWidth() / 4;
            frameHeight = spriteSheet.getHeight() / 4;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    this.runningFrames[i][j] = spriteSheet.getSubimage(j * frameWidth, i * frameHeight, frameWidth, frameHeight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2d) {
        BufferedImage[][] frames = this.keyHandler.isZPressed() && !keyHandler.isNothingPressed() ? this.runningFrames : this.getFrames();

        BufferedImage image = switch (this.getDirection()) {
            case DOWN -> frames[0][this.getSpriteNumber()];
            case LEFT -> frames[1][this.getSpriteNumber()];
            case RIGHT -> frames[2][this.getSpriteNumber()];
            case UP -> frames[3][this.getSpriteNumber()];
        };
        g2d.drawImage(image, this.screenX, this.screenY, image.getWidth() * this.gamePanel.getSCALE(), image.getHeight() * this.gamePanel.getSCALE(), null);
    }

    public int getScreenX () {
        return this.screenX;
    }

    public int getScreenY () {
        return this.screenY;
    }
}
