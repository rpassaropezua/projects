package com.ray.TileMap.object;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.ray.TileMap.framework.ObjectID;


public class Enemy extends MapObject{

	private int level;
	
	public Enemy(TileMap map, double x, double y, ObjectID id, int level) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.id = id;
		this.level = level;
		
		width = 25;
		height = 25;
		
		stopSpeed = .30;
		moveSpeed = .8;
		maxSpeed = 4.5;
		gravity = 0.5;
		maxFallingSpeed = 7;
		
		left = true;
	}
	
	
	public void draw(Graphics2D g) {
		int mapX = map.getX();
		int mapY = map.getY();
		//g.setColor(Color.blue);
		//g.draw(getBounds());
		g.setColor(Color.GREEN);
		g.fillRect((int)(mapX + x - width / 2), (int)(mapY + y - height / 2), width, height);
		
	}

	public Rectangle getBounds() {
		Rectangle bounds = new Rectangle((int)(map.getX() + x - width / 2), (int)(map.getY() + y - height / 2), width, height);
		return bounds;
	}

	public void update(ArrayList<MapObject>objects) {
		if(left) {
			deltaX -= moveSpeed;
			if(deltaX < -maxSpeed) {
				deltaX = -maxSpeed;
			}
		} else if (right) {
			deltaX += moveSpeed;
			if(deltaX > maxSpeed) {
				deltaX = maxSpeed;
			}
		} else {
			if(deltaX > 0) {
				deltaX -= stopSpeed;
				if(deltaX < 0) {
					deltaX = 0;
				}
			} else if(deltaX < 0) {
				deltaX += stopSpeed;
				if(deltaX > 0) {
					deltaX = 0;
				}
			}
		}
		
		if(falling) {
			deltaY += gravity;
			if(deltaY > maxFallingSpeed) {
				deltaY = maxFallingSpeed;
			}
		} else {
			deltaY = 0;
		}
		
		checkCollision();
	}
	
	public void checkCollision() {

		int currentCol = map.getColTile((int)x);
		int currentRow = map.getRowTile((int)y);

		double nextX = x + deltaX;
		double nextY = y + deltaY;
		
		double tempX = x;
		double tempY = y;
		
		calculateCorners(x, nextY);
		if(deltaY < 0) {
			if(topLeft || topRight) {
				deltaY = 0;
				tempY = currentRow * map.getTileSize() + height / 2;
			} else {
				tempY += deltaY;
			}
		}
		if(deltaY > 0) {
			if(bottomLeft || bottomRight) {
				deltaY = 0;
				falling = false;
				tempY = (currentRow + 1) * map.getTileSize() - height / 2;
			} else {
				tempY += deltaY;
			}
		}
				
		calculateCorners(nextX, y);
		if(deltaX < 0) {
			if(topLeft || bottomLeft) {
				deltaX = 0;
				tempX = currentCol * map.getTileSize() + width / 2;
				left = false;
				right = true;
			} else {
				tempX += deltaX;
			}
		}
		if(deltaX > 0) {
			if(topRight || bottomRight) {
				deltaX = 0;
				tempX = (currentCol + 1) * map.getTileSize() - width / 2;
				right = false;
				left = true;
			} else {
				tempX += deltaX;
			}
		}
		
		if(!falling) {
			calculateCorners(x, y+1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
		x = tempX;
		y = tempY;
			
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}

}
