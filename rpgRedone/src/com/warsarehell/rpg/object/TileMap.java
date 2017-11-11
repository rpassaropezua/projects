package com.warsarehell.rpg.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

//import javax.imageio.ImageIO;




import javax.imageio.ImageIO;

import com.warsarehell.rpg.framework.GameObject;
import com.warsarehell.rpg.framework.OBJECTID;

public class TileMap extends GameObject {
	
	public TileMap(float x, float y, OBJECTID id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}
	
	public TileMap() {
		
	}

	private int x;
	private int y;
	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private BufferedImage tileSet;
	private Tile[][] tiles;
	//private BufferedImage sheet;
	
	public void createTileMap(String fileName, String tileSetFileName, int tileSize) {
		this.tileSize = tileSize;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			mapWidth = Integer.parseInt(reader.readLine());
			mapHeight = Integer.parseInt(reader.readLine());
			map = new int[mapHeight][mapWidth];
			loadTiles(tileSetFileName);
			String delimeters = "\\s";
			
			for(int row = 0; row < mapHeight; row++) {
				String line = reader.readLine();
				//System.out.println(line);
				String[] tokens = line.split(delimeters);
				
				for(int col = 0; col < mapWidth; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
					
				}
				
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics2D g){
		
		for(int row = 0; row < mapHeight; row++) {
			
			for(int col = 0; col < mapWidth; col++) {
				int rowColumn = map[row][col];
				
				int r = rowColumn / tiles[0].length;
				int c = rowColumn % tiles[0].length;
				
				g.drawImage(tiles[r][c].getImage(), x + col * tileSize, y + row * tileSize, null);
				
			}
			
		}
	}

	@Override
	public void update(LinkedList<GameObject> object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void loadTiles(String fileName) {
		try {
			tileSet = ImageIO.read(new File(fileName));
			int numTilesAcross = (tileSet.getWidth() + 1) / (tileSize + 1);
			tiles = new Tile[12][numTilesAcross];
			
			BufferedImage subImg;
			for(int col = 0; col < numTilesAcross; col++) {
				subImg = tileSet.getSubimage(col * tileSize + col, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 2, tileSize, tileSize);
				tiles[2][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 3, tileSize, tileSize);
				tiles[3][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 4, tileSize, tileSize);
				tiles[4][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 5, tileSize, tileSize);
				tiles[5][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 6, tileSize, tileSize);
				tiles[6][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 7, tileSize, tileSize);
				tiles[7][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 8, tileSize, tileSize);
				tiles[8][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 9, tileSize, tileSize);
				tiles[9][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 10, tileSize, tileSize);
				tiles[10][col] = new Tile(subImg, false);
				subImg = tileSet.getSubimage(col * tileSize + col, tileSize * 11, tileSize, tileSize);
				tiles[11][col] = new Tile(subImg, false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return this.tileSize;
	}
}
