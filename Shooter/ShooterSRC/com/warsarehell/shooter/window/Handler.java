package com.warsarehell.shooter.window;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.object.Block;
import com.warsarehell.shooter.object.Enemy;
import com.warsarehell.shooter.object.Player;
import com.warsarehell.shooter.object.SpitterEnemy;
import com.warsarehell.shooter.object.Weapon;

public class Handler {
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	private GameObject tempObject;
	private int enemyCount=1;
	private Player player;
	private int enemyWave=0;
	private int dir = 0;
	private int lvlMult=1;
	private Weapon weapon=new Weapon();
	private int enemyHeight = 453;
	private int[][] enemyWaveComp={{1,0,0,0,0},{2,0,0,0,0},{3,0,0,0,0},{4,0,0,0,0},{5,0,0,0,0},{1,0,0,0,0},{1,1,0,0,0},{2,2,0,0,0},{3,3,0,0,0}
	,{4,4,0,0,0},{5,5,0,0,0},{1,1,0,0,0},{1,1,1,0,1},{2,2,2,0,2},{3,3,3,0,3},{4,4,4,0,4},{5,5,5,0,5},{1,1,1,0,1},{1,1,1,1,1},{2,2,2,2,2},{3,3,3,3,3},{4,4,4,4,4}
	,{5,5,5,5,5},{1,1,1,1,1},{6,6,6,6,3},{10,1,1,1,4},{10,5,10,5,5},{5,10,5,10,10},{10,10,10,10,5},{10,10,10,10,5},{15,15,10,10,10},{15,15,15,15,15}};
	private Color[]areaColors={Color.red,Color.magenta,Color.gray,Color.orange};

	public void update(){
		
		for(int i = 0; i<object.size();i++){
			tempObject = object.get(i);
			tempObject.update(object);
			if(tempObject.getId()==OBJECTID.Enemy)
				enemyCount++;
		}
		if(enemyCount==0){
			if(enemyWave>31){
				enemyWave=0;
				enemyCount=1;
				lvlMult++;
			}
			System.out.println(enemyWave);
			if(enemyWaveComp[0][0]!=0){
				for(int i = 0;i<(enemyWaveComp[enemyWave][0])*lvlMult;i++){
					setDir();
					if(dir<0){
						if(enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23){
							addObject(new Enemy(-60+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,1,10*lvlMult));
						}else{
							addObject(new Enemy(-60+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,1,1*lvlMult));
						}
					}else if(dir>0){
						if(enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23){
							addObject(new Enemy(Game.getWIDTH()+60+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,1,10*lvlMult));
						}else{
							addObject(new Enemy(Game.getWIDTH()+60+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,1,1*lvlMult));
						}
					}
				}
			}
			if(enemyWaveComp[1][0]!=0){
				for(int i = 0;i<(enemyWaveComp[enemyWave][1])*lvlMult;i++){
					setDir();
					if(dir<0){
						if(enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23){
							addObject(new Enemy(-100+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,1.5,20*lvlMult));
						}else
							addObject(new Enemy(-100+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,1.5,2*lvlMult));
					}else if(dir>0){
						if(enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23){
							addObject(new Enemy(Game.getWIDTH()+100+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,1.5,20*lvlMult));
						}else
							addObject(new Enemy(Game.getWIDTH()+100+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,1.5,2*lvlMult));
					}
				}
			}
			if(enemyWaveComp[2][0]!=0){
				for(int i = 0;i<(enemyWaveComp[enemyWave][2])*lvlMult;i++){
					setDir();
					if(dir<0){
						if(enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23){
							addObject(new Enemy(-140+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,2,30*lvlMult));
						}else
							addObject(new Enemy(-140+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,2,3*lvlMult));
					}else if(dir>0){
						if(enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23){
							addObject(new Enemy(Game.getWIDTH()+140+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,2,30*lvlMult));
						}else
							addObject(new Enemy(Game.getWIDTH()+140+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,2,3*lvlMult));
					}
				}
			}
			if(enemyWaveComp[3][0]!=0){
				for(int i = 0;i<(enemyWaveComp[enemyWave][3])*lvlMult;i++){
					setDir();
					if(dir<0){
						if((enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23)){
							addObject(new Enemy(-180+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,2.5,40*lvlMult));
						}else
							addObject(new Enemy(-180+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.Enemy,2.5,4*lvlMult));
					}else if(dir>0){
						if((enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23)){
							addObject(new Enemy(Game.getWIDTH()+180+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,2.5,40*lvlMult));
						}else
							addObject(new Enemy(Game.getWIDTH()+180+(int)(Math.random()*100),enemyHeight,this,OBJECTID.Enemy,2.5,4*lvlMult));
					}
				}
			}
			if(enemyWaveComp[4][0]!=0){
				for(int i = 0;i<(enemyWaveComp[enemyWave][3])*lvlMult;i++){
					setDir();
					if(dir<0){
						if((enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23)){
							addObject(new SpitterEnemy(-220+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.SpitterEnemy,2,20*lvlMult));
						}else
							addObject(new SpitterEnemy(-220+(int)(Math.random()*-100),enemyHeight,this,OBJECTID.SpitterEnemy,2,4*lvlMult));
					}else if(dir>0){
						if((enemyWave==5||enemyWave==11||enemyWave==17||enemyWave==23)){
							addObject(new SpitterEnemy(Game.getWIDTH()+220+(int)(Math.random()*100),enemyHeight,this,OBJECTID.SpitterEnemy,2,20*lvlMult));
						}else
							addObject(new SpitterEnemy(Game.getWIDTH()+220+(int)(Math.random()*100),enemyHeight,this,OBJECTID.SpitterEnemy,2,4*lvlMult));
					}
				}
			}
			enemyWave++;
		}
		enemyCount=0;
	}
	public void render(Graphics g){
		g.setColor(areaColors[lvlMult-1]);
		g.drawString("Wave: "+enemyWave, Game.getWIDTH()/2, 20);
		g.drawString("Area: "+lvlMult,Game.getWIDTH()/2,40);
		gameInfo(g);
		for(int i = 0; i<object.size();i++){
			object.get(i).render(g);
		}
		for(int i = 0; i<object.size();i++){
			if(object.get(i).getId()==OBJECTID.Enemy){
				Enemy tempEnemy = (Enemy) object.get(i);
				if(tempEnemy.isFocused())
					tempEnemy.render(g);
			}else if(object.get(i).getId()==OBJECTID.SpitterEnemy){
				SpitterEnemy tempEnemy = (SpitterEnemy) object.get(i);
				if(tempEnemy.isFocused())
					tempEnemy.render(g);
			}
		}
	}
	public void addObject(GameObject object){
		this.object.add(object);
	}
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	public void createLevel(){
		//addObject(new SpitterEnemy(((int)(Math.random()*((Game.WIDTH/2)*dir))+(Game.WIDTH*dir)),443,this,OBJECTID.SpitterEnemy,2,4*lvlMult));
		for(int i = 0; i<Game.getWIDTH()+32;i+=32)
			addObject(new Block(i,Game.getHEIGHT()-32,OBJECTID.Block,this));
	}
	public void setPlayer(){
		for(int i=0; i<object.size();i++){
			if(object.get(i).getId()==OBJECTID.Player)
				this.player=(Player) object.get(i);

		}
	}
	private void setDir(){
		if(Math.random()*100<50)
			this.dir=1;
		else
			this.dir=-1;
	}
	public Color getAreaColor(){
		return this.areaColors[lvlMult-1];
	}
	
	public int getLevelMultiplier(){
		return this.lvlMult;
	}
	private void gameInfo(Graphics g){
		if(player.getCurrentWeapon().getName().equals("Pistol"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("1", 585, 40);
		g.drawRect(560,0,60,40);
		weapon.drawPistol(g, 560, 10);
		if(player.getCurrentWeapon().getName().equals("Assault Rifle"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("2", 647, 40);
		g.drawRect(622,0,60,40);
		weapon.drawAR(g, 625,10);
		if(player.getCurrentWeapon().getName().equals("SMG"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("3", 709, 40);
		g.drawRect(684,0,60,40);
		weapon.drawSMG(g, 690,10);
		if(player.getCurrentWeapon().getName().equals("LMG"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("4", 771, 40);
		g.drawRect(746,0,60,40);
		weapon.drawLMG(g, 748,7);
		if(player.getCurrentWeapon().getName().equals("Sniper Rifle"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("5", 833, 40);
		g.drawRect(808,0,60,40);
		weapon.drawSniper(g, 808,10);
		if(player.getCurrentWeapon().getName().equals("Rocket Launcher"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("6", 895, 40);
		g.drawRect(870,0,60,40);
		weapon.drawRL(g, 888,10);
		if(player.getCurrentWeapon().getName().equals("Minigun"))
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.drawString("7", 957, 40);
		g.drawRect(932,0,60,40);
		weapon.drawMinigun(g, 942,5);
	}

}
