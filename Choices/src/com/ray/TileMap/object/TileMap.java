package com.ray.TileMap.object;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.ray.TileMap.framework.GamePanel;


public class TileMap {
	private int x;
	private int y;
	
	
	private int tileSize;
	private int[][] map;
	
	private int mapWidth;
	private int mapHeight;
	
	private BufferedImage tileSet;
	private Tile[][] tiles;
	private GamePanel game;
	private static int easyLevel = 0;
	private static int hardLevel = 0;
	
	private boolean hard;
	
	private void fillMap(String delimeters, int level, BufferedReader reader) {
		for(int row = 0; row < mapHeight; row++) {
			String line;
			try {
				line = reader.readLine();
				String[] tokens = line.split(delimeters);
				
				for(int col = 0; col < mapWidth; col++) {
					int tempTile = Integer.parseInt(tokens[col]);
					if (tempTile == 7) {
						game.createDoor(this,(col) * (tileSize - x), (row) * (tileSize - y), level, true);
						map[row][col] = 8;
					} else if (tempTile == 9) {
						game.createDoor(this,(col) * (tileSize - x), (row) * (tileSize - y), level, false);
						map[row][col] = 8;
					} else {
						map[row][col] = tempTile;
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
		
	}
	public TileMap(String fileName, int tileSize, GamePanel game) {
		this.tileSize = tileSize;
		this.game = game;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)));
			String difficulty = reader.readLine();
			mapWidth = Integer.parseInt(reader.readLine());
			mapHeight = Integer.parseInt(reader.readLine());
			//System.out.println("width: " + mapWidth + "height: " + mapHeight);
			map = new int[mapHeight][mapWidth];
			loadTiles("/Images/tileset2.jpg");
			String delimeters = "\\s";
			if(difficulty.equals("easy")) {
				fillMap(delimeters, easyLevel, reader);
				easyLevel++;
				this.hard = false;
			} else if(difficulty.equals("hard")) {
				fillMap(delimeters, hardLevel, reader);
				hardLevel++;
				this.hard = true;
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadTiles(String fileName) {
		try {
			//tileSet = ImageIO.read(new File(fileName));
			tileSet = ImageIO.read(getClass().getResourceAsStream(fileName));
			int numTilesAcross = (tileSet.getWidth() + 1) / (tileSize + 1);
			tiles = new Tile[12][numTilesAcross];
			
			BufferedImage subImg;
			for(int col = 0; col < numTilesAcross; col++) {
				subImg = tileSet.getSubimage(col * tileSize + col, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subImg, true, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 2, tileSize, tileSize);
				tiles[2][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 3, tileSize, tileSize);
				tiles[3][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 4, tileSize, tileSize);
				tiles[4][col] = new Tile(subImg, false, true, "hard");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 5, tileSize, tileSize);
				tiles[5][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 6, tileSize, tileSize);
				tiles[6][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 7, tileSize, tileSize);
				tiles[7][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 8, tileSize, tileSize);
				tiles[8][col] = new Tile(subImg, false, true, "easy");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 9, tileSize, tileSize);
				tiles[9][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 10, tileSize, tileSize);
				tiles[10][col] = new Tile(subImg, false, false, "");
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 11, tileSize, tileSize);
				tiles[11][col] = new Tile(subImg, false, false, "");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g){
		
		for(int row = 0; row < mapHeight; row++) {
			
			for(int col = 0; col < mapWidth; col++) {
				int rowColumn = map[row][col];
				
				int r = rowColumn / tiles[0].length;
				int c = rowColumn % tiles[0].length;
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize, (int)y + row * tileSize, null);
				
			}
			
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getColTile(int x) {
		return x / tileSize;
	}
	
	public int getRowTile(int y) {
		return y / tileSize;
	}
	
	public int getTile(int row, int col) {
		return map[row][col];
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public boolean isBlocked(int row, int col) {
		int rc = map[row][col];
		int r = rc / tiles[0].length;
		int c = rc % tiles[0].length;
		return tiles[r][c].isBlocked();
	}
	
	public boolean isDoor(int row, int col) {
		int rc = map[row][col];
		int r = rc / tiles[0].length;
		int c = rc % tiles[0].length;
		return tiles[r][c].isDoor();
	}
	
	public boolean isDifficult(int row, int col) {
		int rc = map[row][col];
		int r = rc / tiles[0].length;
		int c = rc % tiles[0].length;
		if(tiles[r][c].getDifficulty().equals("hard")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void setEasyLevel(int level) {
		TileMap.easyLevel = level;
	}

	public GamePanel getGame() {
		return game;
	}

	public void setGame(GamePanel game) {
		this.game = game;
	}

	public static int getHardLevel() {
		return hardLevel;
	}

	public static void setHardLevel(int hardLevel) {
		TileMap.hardLevel = hardLevel;
	}
	
	public boolean isHard() {
		return this.hard;
	}
	
	
	
}
