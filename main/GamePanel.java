package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int SCALE = 2;
    private final int BITS = 32;

    private final int TILE_SIZE = SCALE * BITS;

    private final static int FPS = 60;

    private final static int MAX_COLS = 15;
    private final static int MAX_ROWS = 11;

    private final int WIDTH = TILE_SIZE * MAX_COLS;
    private final int HEIGHT = TILE_SIZE * MAX_ROWS;

    private Thread gameThread;

    private final Player player;

    private final TileManager tileManager;

    // World settings

    public final int maxWorldCols = 51;
    public final int maxWorldRows = 15;

    public final int worldWidth = TILE_SIZE * maxWorldCols;
    public final int worldHeight = TILE_SIZE * maxWorldRows;


    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        KeyHandler keyHandler = new KeyHandler();
        this.player = new Player(this, keyHandler);

        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.tileManager = new TileManager(this);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    public void run () {
        double interval = 1_000_000_000.0 / FPS;
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime = 0;

        long timer = 0;
        long frames = 0;

        while (this.gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                frames++;
            }

            if (timer >= 1_000_000_000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        this.player.update();
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        this.tileManager.draw(g2d);
        this.player.draw(g2d);

        g2d.dispose();
    }

    public int getSCALE () {
        return this.SCALE;
    }

    public int getTILE_SIZE () {
        return this.TILE_SIZE;
    }

    public int getWIDTH () {
        return this.WIDTH;
    }

    public int getHEIGHT () {
        return this.HEIGHT;
    }

    public Player getPlayer () {
        return this.player;
    }
}
