package com.warsarehell.shooter.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.framework.OBJECTID;
//import com.warsarehell.shooter.window.Game;

public class Projectile extends GameObject{
	private float width = 5, height = 5;
	private float speed;
	private float speedMult = 2;
	private double damage = 0;
	private static int shotsFired = 0;
	private static int shotsMissed = 0;
	private static int shotsHit = 0;
	private Player player;
	private boolean alreadyHit = false;

	public Projectile(OBJECTID id,float velX,double damage, Player player) {
		super(player.getProjectileX(), player.getProjectileY(), id);
		this.player=player;
		this.speed=velX*speedMult;
		this.damage=damage;
		shotsFired++;
	}

	@Override
	public void update(LinkedList<GameObject> object) {
		x+=speed;
		if(x>com.warsarehell.shooter.window.Game.getWIDTH()||x<0)
			object.remove(this);
		collision(object);
		shotsMissed=shotsFired-shotsHit;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int) x, (int)y,(int) width,(int) height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)width,(int)height);
	}
	private void collision(LinkedList<GameObject>object){
		for(int i = 0; i<object.size();i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getId()==OBJECTID.Enemy){
				Enemy tempEnemy = (Enemy)tempObject;
				if(getBounds().intersects(tempEnemy.getBounds())){
					object.remove(this);
					if(!alreadyHit){
						tempEnemy.takeDamage(this.damage);
						tempEnemy.updateColorChange();
						tempEnemy.setFocused(true);
					}
					setAlreadyHit();
					shotsHit++;
				}
			}
			if(tempObject.getId()==OBJECTID.SpitterEnemy){
				SpitterEnemy tempEnemy = (SpitterEnemy)tempObject;
				if(getBounds().intersects(tempEnemy.getBounds())){
					object.remove(this);
					if(!alreadyHit){
						tempEnemy.takeDamage(this.damage);
						tempEnemy.updateColorChange();
						tempEnemy.setFocused(true);
					}
					setAlreadyHit();
					shotsHit++;
				}
			}


		}
	}
	public void setAlreadyHit(){
		if(!alreadyHit)
			alreadyHit=true;
	}
	public static int getShotsFired(){
		return Projectile.shotsFired;
	}
	public static int getShotsHit(){
		return Projectile.shotsHit;
	}
	public static int getShotsMissed(){
		return Projectile.shotsMissed;
	}
	/*public static void endGameStats(Graphics g, Game game){
		g.setColor(Color.red);
		g.drawString("Shots fired: "+Projectile.getShotsFired(), game.WIDTH/2, (game.HEIGHT/2)+20);
		g.drawString("Shots hit: "+Projectile.getShotsHit(), game.WIDTH/2, (game.HEIGHT/2)+40);
		g.drawString("Shots missed: "+Projectile.getShotsMissed(), game.WIDTH/2, (game.HEIGHT/2)+60);
	}
	*/

}
