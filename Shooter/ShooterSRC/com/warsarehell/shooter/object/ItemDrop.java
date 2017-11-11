package com.warsarehell.shooter.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.window.Game;

public class ItemDrop extends GameObject {
	String type;
	private int amount=1;
	private Player player;
	private int width=10,height=10;
	private int onFloor=0;
	public ItemDrop(float x, float y, OBJECTID id,String type) {
		super(x, y, id);
		this.type=type;
	}
	private void collision(LinkedList<GameObject>object){
		for(int i = 0; i<object.size();i++){
			GameObject tempObject = object.get(i);
			if(tempObject.getId()==OBJECTID.Player){
				player=(Player)tempObject;
				if(player.getBounds().intersects(getBounds())){
					if(!type.contains("UP")&&!type.contains("Health")){
						for(int ii = 0; ii<player.getWeaponInventory().length;ii++){
							if(player.getWeaponInventory()[ii].getName().equals(type)){
								player.getWeaponInventory()[ii].pickUpMagazine(amount);
								object.remove(this);
							}
						}
					}else if(type.contains("UP")){
						if(type.contains("Damage")){
							player.getCurrentWeapon().damageUp(2);
							object.remove(this);
						}
						else if(type.contains("Clip size")){
							player.getCurrentWeapon().clipSizeUp(5);
							object.remove(this);
						}
					}else if(type.contains("Health")){
						if(player.getHitPoints()+10<=player.getMaxHp()){
							player.healHp(10);
							object.remove(this);
						}
					}
				}
			}
		}
	}
	@Override
	public void update(LinkedList<GameObject> object) {
		collision(object);
		if(this.x<0)
			this.x=10;
		else if(this.x>Game.getWIDTH())
			this.x=Game.getWIDTH()-10;
		onFloor++;
		if(onFloor>240)
			object.remove(this);

	}
	@Override
	public void render(Graphics g) {
		if(!type.contains("UP")&&!type.contains("Health")){
			g.setColor(Color.green);
			g.fillRect((int)x, (int)y, width, height);
			g.setColor(Color.black);
			g.drawRect((int)x, (int)y, width, height);
			g.drawString( type+" Ammo", (int)x, (int)y-10);
		}else if(type.contains("UP")&&!type.contains("Health")){
			g.setColor(Color.darkGray);
			g.fillOval((int)x, (int)y, width, height);
			g.setColor(Color.black);
			g.drawOval((int)x, (int)y, width, height);
			g.drawString("Current Weapon: "+type, (int)x, (int)y-10);
		}else if(type.contains("Health")){
			g.setColor(Color.pink);
			g.fillRect((int)x, (int)y, width, height);
			g.setColor(Color.black);
			g.drawRect((int)x, (int)y, width, height);
			g.drawString(type+" pack", (int)x, (int)y);
		}

	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,width,height);
	}
}
