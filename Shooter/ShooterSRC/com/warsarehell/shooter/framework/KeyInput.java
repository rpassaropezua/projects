package com.warsarehell.shooter.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.warsarehell.shooter.object.Player;
import com.warsarehell.shooter.object.Projectile;
import com.warsarehell.shooter.window.Game;
import com.warsarehell.shooter.window.Handler;

public class KeyInput extends KeyAdapter {
	Handler handler;
	Player tempPlayer;
	Game game;
	public KeyInput(Handler handler, Game game){
		this.handler = handler;
		this.game=game;
	}
	public void init(){
		for(int i = 0; i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId()==OBJECTID.Player)
				tempPlayer = (Player)handler.object.get(i);
		}
	}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ALT)
			game.setFPS_UPS_Visible();
		for(int i = 0; i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId()==OBJECTID.Player){
				if(key == KeyEvent.VK_D){
					tempPlayer.faceRight(true);
					tempPlayer.faceLeft(false);
				}
				if(key == KeyEvent.VK_A){
					tempPlayer.faceLeft(true);
					tempPlayer.faceRight(false);
				}
				if(!tempPlayer.isFalling())
					if((key == KeyEvent.VK_W)) tempPlayer.setVelY(-10);;
					if(key==KeyEvent.VK_SPACE){
						tempPlayer.getCurrentWeapon().weaponFired();
						if(tempPlayer.getCurrentWeapon().canShoot()){
							handler.addObject(new Projectile(OBJECTID.Projectile,tempPlayer.getTempVelX(),tempPlayer.getCurrentWeapon().getDamage(),tempPlayer));
						}
					}
					if(key==KeyEvent.VK_R){
						tempPlayer.getCurrentWeapon().reload();
					}
					if(key==KeyEvent.VK_1){
						tempPlayer.setCurrentWeapon(1);
					}
					if(key==KeyEvent.VK_2){
						tempPlayer.setCurrentWeapon(2);
					}
					if(key==KeyEvent.VK_3){
						tempPlayer.setCurrentWeapon(3);
					}
					if(key==KeyEvent.VK_4){
						tempPlayer.setCurrentWeapon(4);
					}
					if(key==KeyEvent.VK_5){
						tempPlayer.setCurrentWeapon(5);
					}
					if(key==KeyEvent.VK_6){
						tempPlayer.setCurrentWeapon(6);
					}
					if(key==KeyEvent.VK_7){
						tempPlayer.setCurrentWeapon(7);
					}
			}
		}
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		for(int i = 0; i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId()==OBJECTID.Player){
				if(key == KeyEvent.VK_D) 
					if(tempObject.isFacingRight())
						tempObject.faceRight(false);
				if(key == KeyEvent.VK_A)
					if(tempObject.isFacingLeft())
						tempObject.faceLeft(false);


			}
		}
	}

}
