package com.ray.TileMap.object;
import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage image;
	private boolean blocked;
	private boolean door;
	private String difficulty;
	public Tile(BufferedImage image, boolean blocked, boolean door, String difficulty) {
		this.image = image;
		this.blocked = blocked;
		this.door = door;
		this.difficulty = difficulty;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public boolean isDoor() {
		return door;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	

}
