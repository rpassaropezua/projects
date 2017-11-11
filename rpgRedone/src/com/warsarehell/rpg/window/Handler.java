package com.warsarehell.rpg.window;

import java.awt.Graphics2D;
import java.util.LinkedList;

import com.warsarehell.rpg.framework.GameObject;
import com.warsarehell.rpg.framework.OBJECTID;
import com.warsarehell.rpg.object.Player;
import com.warsarehell.rpg.object.Enemy;

public class Handler {
	private Player player;
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	public boolean init = false;
	public boolean combat = false;
	
	public void update(){
		if(init){
			for(int i = 0; i<object.size(); i++){
				object.get(i).update(object);
				if(object.get(i).getId()==OBJECTID.Player){
					player = (Player) object.get(i);
					if(player.isInit() == false){
						player.init();
						player.setSpeed((float).5);
					}
				}else if(object.get(i).getId()==OBJECTID.Enemy){
					Enemy tempEnemy = (Enemy) object.get(i);
					if(tempEnemy.isInit()==false){
						tempEnemy.init();
					}
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		for(int i = 0; i<object.size(); i++){
			object.get(i).render(g);
		}
	}
	

	public void setPlayer(){
		for(int i=0; i<object.size();i++){
			if(object.get(i).getId()==OBJECTID.Player)
				this.player=(Player) object.get(i);

		}
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	public void removeObject(GameObject object){
		this.object.remove(object);
	}
	
	public void init(){
		this.init = true;
		System.out.println("Handler Initialize");
	}
}
