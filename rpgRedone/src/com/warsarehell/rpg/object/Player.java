package com.warsarehell.rpg.object;

import java.awt.Color;
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

public class Player extends GameObject {

	private int targetLocationX = 100;
	private int targetLocationY = 100;
	private float width;
	private float height;
	private float speed;
	private boolean moving = false;
	private boolean init = false;
	private boolean engaged = false;
	private boolean disengage = false;
	private boolean selected = false;
	private double timeSinceLastEngaged = 0.0;
	

	public Player(int y, int x, Handler handler, OBJECTID id, Game game) {
		super(x,y,id);
		handler = new Handler();
		
	}

	@Override
	public void update(LinkedList<GameObject> object) {
		if(init == true){
			float differenceX = this.targetLocationX - (this.x+(width/2));
			float differenceY = this.targetLocationY - (this.y+(height/2));
			float angle = (float)(Math.atan2(differenceY, differenceX) * 180/Math.PI);
			if((Math.abs(differenceX) > 1 || Math.abs(differenceY) > 1)){
				this.x += Math.cos(angle * Math.PI/180) * speed;
				this.y += Math.sin(angle * Math.PI/180) * speed;
				this.moving = true;
			}else{
				this.moving = false;
			}
			//System.out.println(disengage);
			checkLOS(object);
			
		}	
	}

	@Override
	public void render(Graphics2D g) {
		if(init){
			BufferedImage img;
			try {
				img = ImageIO.read(new File("src/images/mainSprite.png"));
				AffineTransform at = new AffineTransform();
				width = img.getWidth();
				height = img.getHeight();	
				float centerX = width/2;
				float centerY = height/2;
				float differenceX = this.targetLocationX - (centerX+x);
				float differenceY = this.targetLocationY - (centerY+y);
				float angle = (float)(Math.atan2(differenceY, differenceX) - (Math.PI/2));
						
				if(Math.abs(differenceX)>1 || Math.abs(differenceY)>1){
					if(isSelected()){
						g.setColor(Color.cyan);
						g.drawLine((int)(getLineOfSight().getCenterX()), (int)(getLineOfSight().getCenterY()), targetLocationX, targetLocationY);
						g.setColor(Color.red);
						g.fillRect((int)(targetLocationX-(2)),(int)(targetLocationY-(2)), 4, 4);
						if(engaged){
							g.setColor(Color.BLUE);
						}else{
							g.setColor(Color.GREEN);
						}
						g.draw(getLineOfSight());
					}
				}
				at.rotate(angle, (int)getLineOfSight().getCenterX(), (int)getLineOfSight().getCenterY());
				at.translate(this.x, this.y);
				g.drawImage(img, at, null);
				g.draw(getBounds());
				g.draw(getLineOfSight().getBounds());
				
					
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
			if(tempObject.getId()==OBJECTID.Enemy){
				Enemy tempEnemy = (Enemy) tempObject;
				if(getLineOfSight().intersects(tempEnemy.getBounds())){
					System.out.println(getLineOfSight().intersects(tempEnemy.getBounds()));
					if(!disengage){
						this.engaged = true;
						this.targetLocationX = (int) tempEnemy.getLineOfSight().getCenterX();
						this.targetLocationY = (int) tempEnemy.getLineOfSight().getCenterY();
						this.speed = 0;
					}else{
						this.speed = .5f;
					}
				}else{
					this.disengage = false;
					this.engaged = false;
					this.speed = .5f;
				}
			}
		}
	}
	
	public int getTargetLocationX(){
		return this.targetLocationX;
	}
	
	public int getTargetLocationY(){
		return this.targetLocationY;
	}
	
	public void setTargetLocation(int newTargetX, int newTargetY){
		if(init){
			targetLocationX = newTargetX;
			targetLocationY = newTargetY;
		}
	}
	
	public void setSpeed(float newSpeed){
		this.speed = newSpeed;
	}
	
	public float getSpeed(){
		return this.speed;
	}
	
	public void init(){
		System.out.println("Player Initialize");
		this.init = true;
	}
	
	public void setSelected(){
		this.selected = true;
	}
	
	public void setDeselected(){
		this.selected = false;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	public void setDisengage(){
		this.disengage = true;
	}
	
	
	public boolean isInit(){
		return this.init;
	}
	
	public boolean isMoving(){
		return this.moving;
	}

	public double getTimeSinceLastEngaged() {
		return timeSinceLastEngaged;
	}

	public void setTimeSinceLastEngaged(double timeSinceLastEngaged) {
		this.timeSinceLastEngaged = timeSinceLastEngaged;
	}
	
}
