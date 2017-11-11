package com.warsarehell.rpg.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.warsarehell.rpg.framework.GameObject;
import com.warsarehell.rpg.framework.OBJECTID;
import com.warsarehell.rpg.window.Game;
import com.warsarehell.rpg.window.Handler;

public class Enemy extends GameObject {
	private int targetLocationX = 100;
	private int targetLocationY = 100;
	private float width;
	private float height;
	private float speed = (float) 0.5f;
	private boolean moving = false;
	private boolean init = false;
	private boolean engaged = false;
	

	public Enemy(int y, int x, Handler handler, OBJECTID id, Game game) {
		super(x,y,id);
		handler = new Handler();
		
	}

	@Override
	public void update(LinkedList<GameObject> object) {
		if(init == true){
			float differenceX = (float)targetLocationX - (this.x+(width/2));
			float differenceY = (float)targetLocationY - (this.y+(height/2));
			float angle = (float)(Math.atan2(differenceY, differenceX) * 180/Math.PI);
			if(Math.abs(differenceX) > 1 || Math.abs(differenceY) > 1){
				this.x += Math.cos(angle * Math.PI/180) * speed;
				this.y += Math.sin(angle * Math.PI/180) * speed;
				this.moving = true;
			}else{
				this.moving = false;
				setTargetLocation();
			}
			checkLOS(object);
		}	
	}

	@Override
	public void render(Graphics2D g) {
		if(init){
			BufferedImage img;
			AffineTransform at = new AffineTransform();
			try {
				img = ImageIO.read(new File("src/images/mainSprite.png"));
				width = img.getWidth();
				height = img.getHeight();
	
				float centerX = width/2;
				float centerY = height/2;
				float differenceX = (float)targetLocationX - (centerX+x);
				float differenceY = (float)targetLocationY - (centerY+y);
				float angle = (float)(Math.atan2(differenceY, differenceX) - (Math.PI/2));
				
				at.rotate(angle, (int)getLineOfSight().getCenterX(), (int)getLineOfSight().getCenterY());
				at.translate(this.x, this.y);
				g.drawImage(img, at, null);
				g.draw(getBounds());
				g.draw(getLineOfSight().getBounds());
				g.draw(getLineOfSight());
				g.fillRect((int)getLineOfSight().getCenterX(), (int)getLineOfSight().getCenterY(), 2, 2);
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	
	public Ellipse2D getLineOfSight(){
		double x = (this.x-(width*1.5));
		double y = (this.y-(height*1.4));
		double width = (this.width * 4);
		double height = (this.height * 3.5);
		Shape lineOfSightCircle = new Ellipse2D.Double(x, y, width, height);
		return (Ellipse2D) lineOfSightCircle;
	}
	
	public void checkLOS(LinkedList<GameObject>object){
		for(int i = 0; i < object.size(); i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getId()==OBJECTID.Player){
				Player tempPlayer = (Player)tempObject;
				if(getLineOfSight().intersects(tempPlayer.getBounds())){
					this.setEngaged(true);
					this.targetLocationX = (int) tempPlayer.getLineOfSight().getCenterX();
					this.targetLocationY = (int) tempPlayer.getLineOfSight().getCenterY();
					this.speed = 0;
				}else{
					this.setEngaged(false);
					this.speed = .5f;
				}
			}
		}
	}
	
	
	public void setTargetLocation(){
		if(init){
			boolean validate = false;
			while(!validate){
				targetLocationX = (int)(Math.random()*500);
				targetLocationY = (int)(Math.random()*500);
				if(targetLocationX > Game.WIDTH || targetLocationX < 0){
					targetLocationX = (int)(Math.random()*500);
				}else if(targetLocationY > Game.HEIGHT || targetLocationY < 0){
					targetLocationY = (int)(Math.random()*500);
				}else{
					validate = true;
				}
			}
		}
	}
	
	public void init(){
		System.out.println("Enemy Initialize");
		this.init = true;
		setTargetLocation();
	}

	
	public boolean isInit(){
		return this.init;
	}
	
	public boolean isMoving(){
		return this.moving;
	}

	public boolean isEngaged() {
		return engaged;
	}

	public void setEngaged(boolean engaged) {
		this.engaged = engaged;
	}
	
}
