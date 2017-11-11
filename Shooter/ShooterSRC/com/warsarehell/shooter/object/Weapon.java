package com.warsarehell.shooter.object;

import java.awt.Color;
import java.awt.Graphics;

public class Weapon{
	private String name;
	private int clipSize;
	private int magazines;
	private double weight;
	private double damage;
	private double fireRate;
	private double reloadingTime;
	private boolean canFire=true;
	private boolean reloaded;
	private long lastShot=0;
	private int ammo;
	private int currentMagazines;
	private long timeR;
	private float x;
	private float y;
	private boolean facingRight=true;
	private boolean facingLeft;
	private int barrelXR;
	private int barrelYR;
	private int barrelXL;
	private int barrelYL;
	private int graphicsCount=0;
	public Weapon(String name, int clipSize, int magazines,double weight, double damage, double fireRate,double reloadingTime,int barrelXR,int barrelYR,int barrelXL,int barrelYL){
		this.name=name;
		this.clipSize=clipSize;
		this.magazines=magazines;
		this.weight=weight;
		this.damage=damage;
		this.fireRate=fireRate;
		this.reloadingTime=reloadingTime;
		this.ammo = clipSize;
		this.currentMagazines=magazines;
		this.barrelXR=barrelXR;
		this.barrelYR=barrelYR;
		this.barrelXL=barrelXL;
		this.barrelYL=barrelYL;
	}
	public Weapon(){
		
	}
	public static Weapon[]weaponList={
		new Weapon("Pistol",15,5,1.0,5.0,200,1000,42,5,-16,5),
		new Weapon("Assault Rifle",25,5,3.0,10.0,100,2000,47,5,-23,5),
		new Weapon("SMG",30,5,2.3,7.0,60,2000,33,8,-8,8),
		new Weapon("LMG",100,3,9.0,15.0,70,4000,50,7,-25,7),
		new Weapon("Sniper Rifle",5,5,4.0,45.0,400,4000,57,5,-32,5),
		new Weapon("Rocket Launcher",1,2,9.0,100.0,100,8000,51,7,-18,7),
		new Weapon("Minigun",1000,0,20.0,10.0,10.0,0.0,40,11,-20,11)};
	public void weaponFired(){
		if(!reloaded && (System.currentTimeMillis()>(lastShot+fireRate))&&ammo>0){
			canFire=true;
			ammo--;
			graphicsCount++;
			lastShot=System.currentTimeMillis();
		}else
			canFire=false;
	}
	public boolean canShoot(){
		return canFire;
	}
	public String getName(){
		return name;
	}
	public void reload(){
		if(currentMagazines>0){
			reloaded=true;
			timeR=System.currentTimeMillis();
			currentMagazines--;
			ammo=clipSize;
		}
	}
	public void update(){
		if(System.currentTimeMillis()>(timeR+reloadingTime))
			reloaded=false;
	}
	public void render(Graphics g){
		if(reloaded)
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString(ammo+"/"+currentMagazines,(int)x-5, (int)y-10);
		g.setColor(Color.black);
		if(this.name.equals("Pistol")){
			drawPistol(g,(int)this.x,(int)this.y);
		}
		else if(this.name.equals("Assault Rifle")){
			drawAR(g,(int)this.x,(int)this.y);
		}
		else if(this.name.equals("SMG")){
			drawSMG(g,(int)this.x,(int)this.y);
		}
		else if(this.name.equals("LMG")){
			drawLMG(g,(int)this.x,(int)this.y);
		}
		else if(this.name.equals("Sniper Rifle")){
			drawSniper(g,(int)this.x,(int)this.y);
		}
		else if(this.name.equals("Rocket Launcher")){
			drawRL(g,(int)this.x,(int)this.y);
		}
		else if(this.name.equals("Minigun")){
			drawMinigun(g,(int)this.x,(int)this.y);
		}
	}
	public void drawPistol(Graphics g, int x, int y){
		if(facingRight){
			g.setColor(Color.gray);
			g.fillRect(x+27, y+5, 15, 4);
			g.setColor(Color.darkGray);
			g.fillRect(x+27, y+9, 4, 6);
		}else if(facingLeft){
			g.setColor(Color.gray);
			g.fillRect((int)x-16, (int)y+5, 15, 4);
			g.setColor(Color.darkGray);
			g.fillRect((int)x-5, (int)y+9, 4, 6);
		}
	}
	public void drawAR(Graphics g, int x, int y){
		g.setColor(Color.black);
		if(facingRight){
			g.fillRect((int)x+10, (int)y+5, 30, 5);
			g.fillRect((int)x+10, (int)y+9, 4, 5);
			g.fillRect((int)x+19, (int)y+9, 4, 6);
			if(!reloaded){
				g.setColor(Color.darkGray);
				g.fillRect((int)x+30, (int)y+9, 4, 8);
			}
			g.setColor(Color.black);
			g.fillRect((int)x+20, (int)y+3, 20, 2);
			g.fillRect((int)x+29, (int)y, 3, 4);
			g.fillRect((int)x+37, (int)y+5, 10, 2);
		}else if(facingLeft){
			g.fillRect((int)x-16, (int)y+5, 30, 5);
			g.fillRect((int)x+10, (int)y+9, 4, 5);
			g.fillRect((int)x+2, (int)y+9, 4, 6);
			if(!reloaded){
				g.setColor(Color.darkGray);
				g.fillRect((int)x-9, (int)y+9, 4, 8);
			}
			g.setColor(Color.black);
			g.fillRect((int)x-16, (int)y+3, 20, 2);
			g.fillRect((int)x-8, (int)y, 3, 4);
			g.fillRect((int)x-23, (int)y+5, 10, 2);
		}
	}
	public void drawSMG(Graphics g, int x, int y){
		g.setColor(Color.black);
		if(facingRight){
			g.fillRect((int)x+11, (int)y+8, 15, 5);
			if(!reloaded){
				g.setColor(Color.darkGray);
				g.fillRect((int)x+16, (int)y+6, 10, 2);
			}
			g.setColor(Color.black);
			g.fillRect((int)x+26, (int)y+4, 4, 9);
			g.fillRect((int)x+30, (int)y+8, 3, 2);
			g.fillRect((int)x+11, (int)y+11, 9, 5);
		}else if(facingLeft){
			g.fillRect((int)x-1, (int)y+8, 15, 5);
			if(!reloaded){
				g.setColor(Color.darkGray);
				g.fillRect((int)x-1, (int)y+6, 10, 2);
			}
			g.setColor(Color.black);
			g.fillRect((int)x-5, (int)y+4, 4, 9);
			g.fillRect((int)x-8, (int)y+8, 3, 2);
			g.fillRect((int)x+5, (int)y+11, 9, 5);
		}
	}
	public void drawLMG(Graphics g, int x, int y){
		g.setColor(Color.black);
		if(facingRight){
			g.fillRect((int)x+4, (int)y+7, 35, 7);
			g.fillRect((int)x+4, (int)y+14, 5, 4);
			g.fillRect((int)x+15, (int)y+14, 4, 7);
			g.fillRect((int)x+30, (int)y+14, 4, 6);
			if(!reloaded){
				g.setColor(Color.darkGray);
				for(int i = 0; i<(ammo/7);i++){
					g.fillRect((int)x+23, (int)y+(10+i), 4, 1);
				}
			}
			g.setColor(Color.black);
			g.fillRect((int)x+35, (int)y+7, 15, 3);
			g.fillRect((int)x+15, (int)y+5, 20, 2);
		}else if(facingLeft){
			g.fillRect((int)x-15, (int)y+7, 35, 7);
			g.fillRect((int)x+15, (int)y+14, 5, 4);
			g.fillRect((int)x+5, (int)y+14, 4, 7);
			g.fillRect((int)x-10, (int)y+14, 4, 6);
			if(!reloaded){
				g.setColor(Color.darkGray);
				for(int i = 0; i<(ammo/7);i++){
					g.fillRect((int)x-3, (int)y+(10+i), 4, 1);
				}
			}
			g.setColor(Color.black);
			g.fillRect((int)x-25, (int)y+7, 15, 3);
			g.fillRect((int)x-10, (int)y+5, 20, 2);
		}
	}
	public void drawSniper(Graphics g, int x, int y){
		g.setColor(Color.black);
		if(facingRight){
			g.fillRect((int)x+7, (int)y+5, 25, 7);
			g.fillRect((int)x+32, (int)y+5, 15, 5);
			g.fillRect((int)x+47, (int)y+5, 10, 3);
			g.fillRect((int)x+3, (int)y+5, 4, 10);
			g.fillRect((int)x+13, (int)y+4, 30, 2);
			g.fillRect((int)x+13, (int)y+12, 4, 7);
			g.fillRect((int)x+20, (int)y+1, 9, 5);
			if(!reloaded){
				g.setColor(Color.darkGray);
				g.fillRect((int)x+22, (int)y+12, 10, 4);
			}
		}
		else if(facingLeft){
			g.fillRect((int)x-7, (int)y+5, 25, 7);
			g.fillRect((int)x-22, (int)y+5, 15, 5);
			g.fillRect((int)x-32, (int)y+5, 10, 3);
			g.fillRect((int)x+18, (int)y+5, 4, 10);
			g.fillRect((int)x-16, (int)y+4, 30, 2);
			g.fillRect((int)x+7, (int)y+12, 4, 7);
			g.fillRect((int)x-5, (int)y+1, 9, 5);
			if(!reloaded){
				g.setColor(Color.darkGray);
				g.fillRect((int)x-7, (int)y+12, 10, 4);
			}
		}
	}
	public void drawRL(Graphics g, int x, int y){
		g.setColor(Color.black);
		if(facingRight){
			g.setColor(Color.darkGray);
			g.fillRect(x-7, y+6, 38, 9);
			g.setColor(Color.black);
			g.fillRect(x-7, y+5, 5, 11);
			g.fillRect(x+26, y+5, 5, 11);
			g.setColor(Color.red);
			g.fillRect(x+20, y+2, 3, 5);
			g.setColor(Color.darkGray);
			g.drawRect(x+20, y+2, 3, 5);
		}
		else if(facingLeft){
			g.setColor(Color.darkGray);
			g.fillRect(x-7, y+6, 38, 9);
			g.setColor(Color.black);
			g.fillRect(x-7, y+5, 5, 11);
			g.fillRect(x+26, y+5, 5, 11);
			g.setColor(Color.red);
			g.fillRect(x+1, y+2, 3, 5);
			g.setColor(Color.darkGray);
			g.drawRect(x+1, y+2, 3, 5);
		}
	}
	public void drawMinigun(Graphics g, int x, int y){
		g.setColor(Color.black);
		if(facingRight){
			if(graphicsCount>=3){
				graphicsCount=0;
			}
			if(graphicsCount==0)
				g.setColor(Color.darkGray);
			else if(graphicsCount==1)
				g.setColor(Color.gray);
			else if(graphicsCount==2)
				g.setColor(Color.lightGray);
			g.fillRect((int)x+20, (int)y+7, 20, 4);
			if(graphicsCount==0)
				g.setColor(Color.lightGray);
			else if(graphicsCount==1)
				g.setColor(Color.darkGray);
			else if(graphicsCount==2)
				g.setColor(Color.gray);
			g.fillRect((int)x+20, (int)y+11, 20, 4);
			if(graphicsCount==0)
				g.setColor(Color.gray);
			else if(graphicsCount==1)
				g.setColor(Color.lightGray);
			else if(graphicsCount==2)
				g.setColor(Color.darkGray);
			g.fillRect((int)x+20, (int)y+15, 20, 4);
			g.setColor(Color.black);
			g.fillRect((int)x+5, (int)y+7, 15, 13);
			g.setColor(Color.red);
			g.fillRect((int)x+12, (int)y+3, 2, 7);
		}
		else if(facingLeft){
			if(graphicsCount>=3){
				graphicsCount=0;
			}
			if(graphicsCount==0)
				g.setColor(Color.darkGray);
			else if(graphicsCount==1)
				g.setColor(Color.gray);
			else if(graphicsCount==2)
				g.setColor(Color.lightGray);
			g.fillRect((int)x-15, (int)y+7, 20, 4);
			if(graphicsCount==0)
				g.setColor(Color.lightGray);
			else if(graphicsCount==1)
				g.setColor(Color.darkGray);
			else if(graphicsCount==2)
				g.setColor(Color.gray);
			g.fillRect((int)x-15, (int)y+11, 20, 4);
			if(graphicsCount==0)
				g.setColor(Color.gray);
			else if(graphicsCount==1)
				g.setColor(Color.lightGray);
			else if(graphicsCount==2)
				g.setColor(Color.darkGray);
			g.fillRect((int)x-15, (int)y+15, 20, 4);
			g.setColor(Color.black);
			g.fillRect((int)x+5, (int)y+7, 15, 13);
			g.setColor(Color.red);
			g.fillRect((int)x+12, (int)y+3, 2, 7);

		}
	}
	public void setX(float x){
		this.x=x;
	}
	public void setY(float y){
		this.y=y;
	}
	public void setFacingRight(){
		facingLeft = false;
		facingRight = true;
	}
	public void setFacingLeft(){
		facingLeft = true;
		facingRight = false;
	}
	public void setAmmo(int ammo){
		this.ammo=ammo;
	}
	public void setCurrentMagazine(int cM){
		this.currentMagazines=cM;
	}
	public int getBarrelXR(){
		return this.barrelXR;
	}
	public int getBarrelYR(){
		return this.barrelYR;
	}
	public int getBarrelXL(){
		return this.barrelXL;
	}
	public int getBarrelYL(){
		return this.barrelYL;
	}
	public double getDamage(){
		return this.damage;
	}
	public void pickUpMagazine(int amount){
		this.currentMagazines+=amount;
	}
	public void damageUp(double amount){
		this.damage+=amount;
	}
	public void clipSizeUp(double amount){
		this.clipSize+=amount;
	}
	
	public double getWeight(){
		return (this.weight/5);
	}
}

