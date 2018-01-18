package com.ray.TileMap.object;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.ray.TileMap.framework.ObjectID;


public abstract class MapObject {
	protected TileMap map;
	protected ObjectID id;
	
	protected int width;
	protected int height;
	protected double gravity;
	
	protected double x;
	protected double y;
	protected double deltaX;
	protected double deltaY;
	
	protected boolean falling;
	protected double maxFallingSpeed;
	
	protected boolean left;
	protected boolean right;
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	protected int leftTile;
	protected int rightTile;
	protected int bottomTile;
	protected int topTile;
	
	public abstract void draw(Graphics2D g);
	public abstract void update(ArrayList<MapObject>objects);
	public abstract void checkCollision();
	public abstract Rectangle getBounds();
	
	public void setMap(TileMap map) {
		this.map = map;
	}
	
	public TileMap getMap() {
		return this.map;
	}
	
	public ObjectID getId() {
		return this.id;
	}
	
	protected void calculateTile(double x, double y) {
		leftTile = map.getColTile((int)(x-width / 2));
		rightTile = map.getColTile((int)(x+width / 2) - 1);
		topTile = map.getRowTile((int)(y-height / 2));
		bottomTile = map.getRowTile((int)(y+height / 2) - 1);
	}
	
	protected void calculateCorners(double x, double y) {
		calculateTile(x,y);
		
		topLeft = map.isBlocked(topTile, leftTile);
		topRight = map.isBlocked(topTile, rightTile);
		bottomLeft = map.isBlocked(bottomTile, leftTile);
		bottomRight = map.isBlocked(bottomTile, rightTile);
	}
	

}
