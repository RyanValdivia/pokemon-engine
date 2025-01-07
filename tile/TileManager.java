package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TileManager {
    private final GamePanel gamePanel;
    private final Tile[] tiles;
    private int[][] mapTiles;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tiles = new Tile[3];
        loadMap("resources/maps/map2.txt");
        getTileImage();
    }

    public void getTileImage () {
        try {
            this.tiles[0] = new Tile();
            this.tiles[0].setImage(ImageIO.read(new File("resources/tiles/grass_tile.png")));

            this.tiles[1] = new Tile();
            this.tiles[1].setImage(ImageIO.read(new File("resources/tiles/wall_tile.png")));
            this.tiles[1].setCollision(true);

            this.tiles[2] = new Tile();
            this.tiles[2].setImage(ImageIO.read(new File("resources/tiles/water_tile.png")));
            this.tiles[2].setCollision(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap (String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            this.mapTiles = new int[this.gamePanel.maxWorldRows][this.gamePanel.maxWorldCols];

            String line;

            int row = 0;

            while ((line = br.readLine()) != null && row < this.gamePanel.maxWorldRows) {
                String[] tokens = line.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    this.mapTiles[row][i] = Integer.parseInt(tokens[i]);
                }
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void draw (Graphics2D g2d) {

        for (int i = 0; i < this.mapTiles.length; i++) {
            for (int j = 0; j < this.mapTiles[i].length; j++) {
                int index = this.mapTiles[i][j];

                BufferedImage image = this.tiles[index].getImage();

                int x = j * gamePanel.getTILE_SIZE();
                int y = i * gamePanel.getTILE_SIZE();

                if (this.calculateOffset(x, y)) {
                    int screenX = x - gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX();
                    int screenY = y - gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY();

                    g2d.drawImage(image, screenX, screenY, image.getWidth() * this.gamePanel.getSCALE(), image.getHeight() * this.gamePanel.getSCALE(), null);
                }
            }
        }
    }

    private boolean calculateOffset (int x, int y) {
        Player player = gamePanel.getPlayer();

        int leftBoundary = player.getX() - player.getScreenX() - gamePanel.getTILE_SIZE();
        int rightBoundary = player.getX() + player.getScreenX() + gamePanel.getTILE_SIZE();
        int topBoundary = player.getY() - player.getScreenY() - gamePanel.getTILE_SIZE();
        int bottomBoundary = player.getY() + player.getScreenY() + gamePanel.getTILE_SIZE();

        return x + gamePanel.getTILE_SIZE() > leftBoundary && x - gamePanel.getTILE_SIZE() < rightBoundary &&
                y + gamePanel.getTILE_SIZE() > topBoundary && y - gamePanel.getTILE_SIZE() < bottomBoundary;
    }
}
