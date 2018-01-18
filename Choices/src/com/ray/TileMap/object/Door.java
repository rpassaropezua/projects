package com.ray.TileMap.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.ray.TileMap.framework.ObjectID;


public class Door extends MapObject{
	private boolean difficulty;
	private int level;

	public Door(TileMap map, double x, double y, ObjectID id, int level, boolean difficulty) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.id = id;
		this.level = level;
		this.difficulty = difficulty;
		
		width = 81/2;
		height = 81/2;
	}
	
	@Override
	public void draw(Graphics2D g) {		
		if(difficulty) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLUE);
		}
		g.fillRect((int)(map.getX() + x + width / 2), (int)(map.getY() + y + height/2), width, height);
		
	}

	@Override
	public void update(ArrayList<MapObject> objects) {
		
	}

	@Override
	public void checkCollision() {
		
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)(map.getX() + x + width / 2), (int)(map.getY() + y + height/2), width, height);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isDifficult() {
		return this.difficulty;
	}

}
