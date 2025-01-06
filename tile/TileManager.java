package tile;

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
        this.mapTiles = new int[3][3];
        loadMap("resources/maps/map.txt");
        getTileImage();
    }

    public void getTileImage () {
        try {
            this.tiles[0] = new Tile();
            this.tiles[0].setImage(ImageIO.read(new File("resources/tiles/grass_tile.png")));

            this.tiles[1] = new Tile();
            this.tiles[1].setImage(ImageIO.read(new File("resources/tiles/wall_tile.png")));

            this.tiles[2] = new Tile();
            this.tiles[2].setImage(ImageIO.read(new File("resources/tiles/water_tile.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap (String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            int maxRows = 5;
            int maxCols = 5;

            this.mapTiles = new int[maxRows][maxCols];

            String line;

            int row = 0;

            while ((line = br.readLine()) != null && row < maxRows) {
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
                int x = j * image.getWidth() * this.gamePanel.getSCALE();
                int y = i * image.getHeight() * this.gamePanel.getSCALE();

                g2d.drawImage(image, x, y, image.getWidth() * this.gamePanel.getSCALE(), image.getHeight() * this.gamePanel.getSCALE(), null);
            }
        }
    }
}
