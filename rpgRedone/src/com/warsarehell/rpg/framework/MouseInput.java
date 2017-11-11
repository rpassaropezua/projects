package com.warsarehell.rpg.framework;

//import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.warsarehell.rpg.object.Player;
import com.warsarehell.rpg.window.Game;
import com.warsarehell.rpg.window.Handler;

public class MouseInput implements MouseListener, MouseMotionListener {
	Handler handler;
	Player tempPlayer;
	Game game;
	public boolean init;
	
	public MouseInput(Handler handler, Game game){
		this.handler = handler;
		this.game = game;
	}
	
	public void init(){
		/*for(int i = 0; i>handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId()==OBJECTID.Player){
				tempPlayer = (Player)handler.object.get(i);
				System.out.println(tempPlayer);
			}
		}*/
		this.init = true;
		for(int index = 0; index < handler.object.size(); index++) {
			if(handler.object.get(index).getId() == OBJECTID.Player){
				tempPlayer = (Player)handler.object.get(index);
				System.out.println("Mouse Listener Initialize");
			}
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(init){
			if(arg0.getButton()==1){
				if((arg0.getX() > (tempPlayer.getBounds().getX()-tempPlayer.getBounds().getWidth())
						&& arg0.getX() < (tempPlayer.getBounds().getX()+tempPlayer.getBounds().getWidth()))
						&& (arg0.getY() > (tempPlayer.getBounds().getY()-tempPlayer.getBounds().getHeight()))
						&& arg0.getY() < (tempPlayer.getBounds().getY()+tempPlayer.getBounds().getHeight())){
					tempPlayer.setSelected();
				}else{
					tempPlayer.setDeselected();
				}
			}else if(arg0.getButton()==3 && tempPlayer.isSelected()){
				tempPlayer.setTargetLocation(arg0.getX(), arg0.getY());	
				tempPlayer.setDisengage();
			}
			
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(tempPlayer.isSelected()){
			//tempPlayer.setMouseLocationX(arg0.getX());
			//tempPlayer.setMouseLocationY(arg0.getY());
		}
		
	}
	

}
