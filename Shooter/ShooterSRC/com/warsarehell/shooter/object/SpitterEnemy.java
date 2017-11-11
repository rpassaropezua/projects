package com.warsarehell.shooter.object;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.window.Handler;

public class SpitterEnemy extends GameObject{
	static final int WIDTH = 25;
	static final int HEIGHT = 25;
	private Handler handler;
	private double speedMultiplier=1;
	private int colorChangeValue = 0;
	private int colorChange = 0;
	private boolean focused = false;
	private boolean stillFocused = false;
	private int focusedCount = 0;
	private boolean rangedAttack = true;
	private float playerX = 0;
	private int attackCoolDown=0;
	private float projDir = 0;
	private float projSpeed = 5;
	//private int attackBarCharge = 0;

	public SpitterEnemy(float x, float y,Handler handler, OBJECTID id,double speedMultiplier,double hPMultiplier) {
		super(x, y, id);
		setMaxHP(10);
		this.handler=handler;
		this.maxHP*=hPMultiplier;
		this.hitPoints=maxHP;
		this.speedMultiplier=speedMultiplier;
		colorChangeValue=255/(int)this.hitPoints;
	}

	public void update(LinkedList<GameObject> object) {
		if(!checkIfAlive()){
			checkDrop(object);
			object.remove(this);
		}
		for(int i = 0; i<handler.object.size();i++){
			GameObject tempObj = object.get(i);
			if(tempObj.getId()==OBJECTID.Player){
				playerX=tempObj.getX();
				if(tempObj.getX()>this.x){
					projDir=1;
					if(Math.abs(tempObj.getX()-this.x)<70)
						velX=(float)(2*speedMultiplier);
					else
						velX=(float)(1*speedMultiplier);
				}
				else if(tempObj.getX()<this.x){
					projDir=-1;
					if(Math.abs(this.x-tempObj.getX())<70)
						velX=(float)(-2*speedMultiplier);
					else
						velX=(float)(-1*speedMultiplier);
				}

			}
		}
		collision(object);
		x+=velX;
		if(Math.abs(playerX-x)<80){
			rangedAttack=false;
		}else{
			rangedAttack=true;
		}
		if(rangedAttack&&attackCoolDown<=0){
			projectileAttack(object);
			//attackBarCharge=0;
		}
		if(focused&&!stillFocused)
			focusedCount++;
		if(focusedCount>300){
			focused=false;
			focusedCount=0;
		}
		stillFocused=false;
		if(attackCoolDown>0){
			attackCoolDown--;
		}
	}

	public void render(Graphics g) {
		Graphics2D g2D=(Graphics2D)g;
		//Graphics2D attackBar = (Graphics2D)g;
		g2D.addRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
		g2D.setColor(new Color(0,255,colorChange));
		g2D.fillRect((int)x,(int) y, WIDTH, HEIGHT);
		g2D.setColor(Color.black);
		g2D.drawRect((int)x, (int)y, WIDTH, HEIGHT);
		g2D.setColor(Color.red);
		if(focused)
			g2D.drawString(hitPoints+"/"+maxHP, (int)x, (int)y-10);
		/*attackBar.setColor(Color.gray);
		attackBar.fillRect((int)x+3, (int)y+5, WIDTH-7, HEIGHT-15);
		attackBar.setColor(Color.black);
		if(attackCoolDown>0){
			attackBarCharge++;
			attackBar.fillRect((int)x+(attackBarCharge/100), (int)y+5, WIDTH-8, HEIGHT-16);
		}*/
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,WIDTH,HEIGHT);
	}
	private boolean checkIfAlive(){
		if(this.hitPoints<=0){
			for(int i = 0; i<handler.object.size();i++){
				if(handler.object.get(i).getId()==OBJECTID.Player)
					handler.object.get(i).enemyKilled();
			}
			return false;
		}else
			return true;
	}
	private void collision(LinkedList<GameObject>object){
		for(int i = 0; i<object.size();i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getId()==OBJECTID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
					tempObject.takeDamage(10);
					object.remove(this);
				}
			}
		}
	}
	public void checkDrop(LinkedList<GameObject> object){
		int dropChance=(int)(Math.random()*200);
		if(dropChance<=3)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Minigun"));
		else if(dropChance>3&&dropChance<=6)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Rocket Launcher"));
		else if(dropChance>6&&dropChance<=11)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Sniper Rifle"));
		else if(dropChance>11&&dropChance<=21)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"LMG"));
		else if(dropChance>21&&dropChance<=31)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"SMG"));
		else if(dropChance>31&&dropChance<=46)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Assault Rifle"));
		else if(dropChance>46&&dropChance<=66)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Pistol"));
		else if(dropChance==67)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Damage UP"));
		else if(dropChance==68)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Clip size UP"));
		else if(dropChance>68&&dropChance<70)
			object.add(new ItemDrop(this.x,this.y,OBJECTID.ItemDrop,"Health"));
	}
	public void updateColorChange(){
		int dmgTaken=(int)(this.maxHP-this.hitPoints);
		for(int i = 0; i<dmgTaken;i++){
			colorChange+=colorChangeValue;
		}
		if(colorChange>255){
			colorChange=255;
		}
	}
	public void setFocused(boolean focused){
		this.focused=focused;
		this.stillFocused=focused;
	}
	public void projectileAttack(LinkedList<GameObject>object){
		for(int i = 0; i<(Math.abs(x-playerX));i++){
			projSpeed+=.04f;
		}
		attackCoolDown=250;
		object.add(new SpitterAttack(this.x,this.y,OBJECTID.EnemyProjectile,projDir,projSpeed,7));
		projSpeed=0;
	}
	public boolean isFocused(){
		if(focused)
			return true;
		else return false;
	}

}
