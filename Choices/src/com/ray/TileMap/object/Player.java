package com.ray.TileMap.object;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.ray.TileMap.framework.Animation;
import com.ray.TileMap.framework.GamePanel;
import com.ray.TileMap.framework.ObjectID;



public class Player extends MapObject {
	private boolean jumping;
	private double jumpStart;
	
	private boolean grip;
	private int bounces;

	private int lives;
	private int points;
	private int hardLevelsTaken;
	
	private GamePanel game;
	
	private Animation animation;
	private BufferedImage[] idleSprites;
	private BufferedImage[] walkingSprites;
	private BufferedImage[] fallingSprites;
	private BufferedImage[] climbingSprites;
	private boolean facingLeft;
	
	public Player(TileMap map, GamePanel game, ObjectID id) {
		
		this.map = map;
		this.game = game;
		this.id = id;
		
		x = 150;
		y = 140;
		
		width = 9;
		height = 30;
		
		moveSpeed = .8;
		maxSpeed = 4.5;
		maxFallingSpeed = 7;
		stopSpeed = 0.30;
		jumpStart = -11;
		gravity = 0.5;
		
		bounces = 0;
		lives = 3;
		
		try {
			idleSprites = new BufferedImage[5];
			fallingSprites = new BufferedImage[1];
			walkingSprites = new BufferedImage[3];
			climbingSprites = new BufferedImage[3];
			
			BufferedImage img = ImageIO.read(getClass().getResource("/Images/idleSprites2.png"));
			for(int index = 0; index < idleSprites.length; index++) {
				idleSprites[index] = img.getSubimage(index * width + index, 0, width, height);
			}
			
			img = ImageIO.read(getClass().getResource("/Images/walkingSprites.png"));
			for(int index = 0; index < walkingSprites.length; index++) {
				walkingSprites[index] = img.getSubimage(index * width + index, 0, width, height);
			}
			
			img = ImageIO.read(getClass().getResource("/Images/climbingSprites.png"));
			for(int index = 0; index < climbingSprites.length; index++) {
				climbingSprites[index] = img.getSubimage(index * width + index, 0, width, height);
			}
			
			fallingSprites[0] = ImageIO.read(getClass().getResource("/Images/fallingSprites.png"));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		facingLeft = false;
	}
	
	public void update(ArrayList<MapObject>objects) {
		for(int index = 0; index < objects.size(); index++) {
			if(objects.get(index).getId() == ObjectID.Enemy) {
				if(objects.get(index).getBounds().intersects(getBounds())) {
					lives--;
					objects.remove(index);
				}
			} else if (objects.get(index).getId() == ObjectID.Door) {
				Door tempDoor = (Door)objects.get(index);
				if(tempDoor.getBounds().intersects(getBounds())) {
					game.setInterrupted(true);
					calculateResult();
					x = 150;
					y = 140;
					if(lives > 0 && points < 5) {
						game.nextLevel();
					} else {
						game.endScene();
					}
				}
			}
		}
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
		
		if(jumping) {
			deltaY = jumpStart;
			falling = true;
			jumping = false;
		}
		
		if(falling) {
			deltaY += gravity;
			if(deltaY > maxFallingSpeed) {
				deltaY = maxFallingSpeed;
			}
		} else {
			deltaY = 0;
			bounces = 0;
		}
		checkCollision();
	}
	
	public void draw(Graphics2D g) {
		int mapX = map.getX();
		int mapY = map.getY();
		
		if(facingLeft) {
			g.drawImage(animation.getImage(),(int)(mapX + x - width / 2), (int)(mapY + y - height / 2), null);
		} else {
			g.drawImage(animation.getImage(),(int)(mapX + x - width / 2 + width), (int)(mapY + y - height / 2), -width, height, null);
		}

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
				if(grip) {
					bounces++;
					deltaX = 4;
					jumping = true;
					grip = false;
				}
			} else {
				tempX += deltaX;
			}
		}
		if(deltaX > 0) {
			if(topRight || bottomRight) {
				deltaX = 0;
				tempX = (currentCol + 1) * map.getTileSize() - width / 2;
				if(grip) {
					bounces++;
					deltaX = -4;
					jumping = true;
					grip = false;
					
				}
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

		
		map.setX((int)(GamePanel.WIDTH / 2 - x));
		map.setY((int)(GamePanel.HEIGHT / 2 - y));
		
		if(left || right) {
			animation.setFrames(walkingSprites);
			animation.setDelay(100);
		} else {
			animation.setFrames(idleSprites);
			animation.setDelay(200);
		}
		
		if(grip) {
			animation.setFrames(climbingSprites);
			animation.setDelay(100);
		}
		
		if(deltaY < 0) {
			animation.setFrames(fallingSprites);
			animation.setDelay(-1);
		}
		if(deltaY > 0) {
			if(!grip) {
				animation.setFrames(fallingSprites);
				animation.setDelay(-1);
			} else {
				animation.setFrames(climbingSprites);
				animation.setDelay(100);
			}
		}
		
		animation.update();
		
		if(deltaX < 0) {
			facingLeft = true;
		}
		
		if(deltaX > 0) {
			facingLeft = false;
		}
		
		
	}
	
	private void calculateResult() {
		int odds = (int)(Math.random() * 100);
		if(odds >= (50 - (hardLevelsTaken * 2))) {
			if(!map.isHard()) {
				lives--;
			}
		} else {
			points++;
		}
	}
	
	public void setMap(TileMap map) {
		if(map.isHard()) {
			hardLevelsTaken++;
		}
		super.setMap(map);
		
	}
	
	public Rectangle getBounds() {
		Rectangle bounds = new Rectangle((int)(map.getX() + x - width / 2), (int)(map.getY() + y - height / 2), width, height);
		return bounds;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		if(!falling) {
			this.jumping = jumping;
		}
	}
	
	public void setGripping(boolean gripping) {
		if(gripping == false) {
			grip = gripping;
		} else if(falling) {
			if(bounces < 3) {
				grip = gripping;
			}
		}
	}
	
	public boolean isGripping() {
		return this.grip;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getLives() {
		return this.lives;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getHardLevelsTaken() {
		return hardLevelsTaken;
	}

	public void setHardLevelsTaken(int hardLevelsTaken) {
		this.hardLevelsTaken = hardLevelsTaken;
	}
}
