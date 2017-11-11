package com.warsarehell.shooter.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.window.Game;
import com.warsarehell.shooter.window.Handler;

public class Player extends GameObject {
	private float width = 25, height = 25;
	private float gravity = 0.5f;
	private final float MAX_SPEED = 5;
	private Handler handler;
	private Weapon[] weaponInventory={Weapon.weaponList[0],Weapon.weaponList[1],
			Weapon.weaponList[2],Weapon.weaponList[3],
			Weapon.weaponList[4],Weapon.weaponList[5],Weapon.weaponList[6]};
	private float tempVelX;
	//private float tempVelY;
	private Game game;
	private boolean currentDirR;
	private boolean currentDirL;
	boolean init = false;
	public Player(float x, float y, Handler handler, OBJECTID id, Game game) {
		super(x, y, id);
		this.game = game;
		this.handler = handler;
		for(int i = 1; i<weaponInventory.length; i++){
			weaponInventory[i].setAmmo(0);
			weaponInventory[i].setCurrentMagazine(0);
		}
		setMaxHP(100);
		setHP(maxHP);
		currentWeapon=weaponInventory[0];
		currentWeapon.setCurrentMagazine(2);
		currentWeapon=weaponInventory[6];
		currentWeapon.setCurrentMagazine(1);

	}
	
	public void setCurrentWeapon(int wepIndex){
		currentWeapon = weaponInventory[wepIndex-1];
	}
	public void update(LinkedList<GameObject> object) {
		//System.out.println("player y: " + this.y);
		if(isFacingRight()){
			velX=5;
			tempVelX=velX;
			velX=(float) (5-(currentWeapon.getWeight()));
		}else if(isFacingLeft()){
			velX=-5;
			tempVelX=velX;
			velX=(float) (-5+(currentWeapon.getWeight()));
		}else{
			velX=0;
		}
		if(isFacingRight()||tempVelX>0){
			currentDirR=true;
			currentDirL=false;
		}
		else if(isFacingLeft()||tempVelX<0){
			currentDirL=true;
			currentDirR=false;
		}
		if(isJumping()){
			velY=-10;
		}
		x+=velX;
		for(int i=0;i<weaponInventory.length;i++){
			weaponInventory[i].setX(x);
		}
		if(jumping || falling){
			//velY+=(gravity+(currentWeapon.getWeight()/2));
			velY+=gravity;
			if(velY>MAX_SPEED)
				velY=MAX_SPEED;
		}
		y+=velY;
		for(int i=0;i<weaponInventory.length;i++){
			weaponInventory[i].setY(y);
		}
		setWepInfo();
		if(this.x>game.getWidth())
			this.x=0;
		else if(this.x<0)	
			this.x=game.getWidth();
		collision(handler.object);
		currentWeapon.update();
		if(this.hitPoints<=0){
			this.game.setgameOver();
		}
		//System.out.println("Player x: "+x+"Player y: "+y);

	}
	private void collision(LinkedList<GameObject>object){
		for(int i = 0; i<object.size();i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getId()==OBJECTID.Block){
				if(getBounds().intersects(tempObject.getBounds())){
					y=tempObject.getY()-height;
					velY=0;
					falling = false;
					jumping = false;
				}else
					if(y!=tempObject.getY()-height)
						falling = true;
			}
		}
	}

	public void render(Graphics g) {
		if(this.hitPoints<=0){
			g.setColor(Color.red);
			g.drawString("GAME OVER",game.getWIDTH()/2,game.getHEIGHT()/2);
		}
		g.setColor(Color.cyan);
		g.fillRect((int)x,(int)y,(int)width,(int)height);
		g.setColor(Color.black);
		g.drawString("Score: "+enemiesKilled, 0, 10);
		g.drawString("Hit Points: "+Double.toString(hitPoints)+"/"+Double.toString(maxHP),0,30);
		currentWeapon.render(g);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)height,(int)width);
	}
	public float getTempVelX(){
		return this.tempVelX;
	}
	public int getScore(){
		return this.enemiesKilled;
	}
	public double getHitPoints(){
		return this.hitPoints;
	}
	public void healHp(double amount){
		this.hitPoints+=amount;
	}
	public double getMaxHp(){
		return this.maxHP;
	}
	public void setWepInfo(){
		if(currentDirR){
			setProjectileX(currentWeapon.getBarrelXR());
			setProjectileY(currentWeapon.getBarrelYR());
			for(int i=0;i<weaponInventory.length;i++){
				weaponInventory[i].setFacingRight();
			}
		}else if(currentDirL){
			setProjectileX(currentWeapon.getBarrelXL());
			setProjectileY(currentWeapon.getBarrelYL());
			for(int i=0;i<weaponInventory.length;i++){
				weaponInventory[i].setFacingLeft();
			}
		}
	}
	
	public Weapon[] getWeaponInventory(){
		return this.weaponInventory;
	}


}
