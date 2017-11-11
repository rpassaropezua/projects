package com.warsarehell.shooter.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.window.Game;

public class SpitterAttack extends GameObject{
	private float width = 5, height = 5;
	private float speed = 0;
	private double damage = 0;
	static private int projNumber=0;
	private float gravity = .2f;

	public SpitterAttack(float x, float y, OBJECTID id, float xDir, float speed, double damage) {
		super(x, y, id);
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.damage=damage;
		this.velX=(this.speed*xDir);
		this.velY=-3;
		projNumber++;
	}

	@Override
	public void update(LinkedList<GameObject> object) {
		x+=velX;
		y+=velY;
		velY+=gravity;
		if(x>com.warsarehell.shooter.window.Game.getWIDTH()||x<0||y>com.warsarehell.shooter.window.Game.getHEIGHT())
			object.remove(this);
		collision(object);
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
		g2D.setColor(Color.green);
		g2D.fillRect((int) x, (int)y,(int) width,(int) height);
		g2D.setColor(Color.black);
		g2D.drawRect((int) x, (int)y,(int) width,(int) height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	private void collision(LinkedList<GameObject>object){
		for(int i = 0; i<object.size();i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getId()==OBJECTID.Block){
				if(getBounds().intersects(tempObject.getBounds())){
					object.remove(this);
				}
			}
			if(tempObject.getId()==OBJECTID.Player){
				Player tempPlayer = (Player)tempObject;
				if(getBounds().intersects(tempPlayer.getBounds())){
					object.remove(this);
					tempPlayer.takeDamage(this.damage);
				}
			}
		}
	}
}
