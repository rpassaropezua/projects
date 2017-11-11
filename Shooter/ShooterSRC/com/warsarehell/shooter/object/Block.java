package com.warsarehell.shooter.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.warsarehell.shooter.framework.GameObject;
import com.warsarehell.shooter.framework.OBJECTID;
import com.warsarehell.shooter.window.Handler;

public class Block extends GameObject{
	private Handler handler;
	public Block(float x, float y, OBJECTID id,Handler handler) {
		super(x, y, id);
		this.handler=handler;
	}

	public void update(LinkedList<GameObject> object) {
		
	}

	public void render(Graphics g) {
		g.setColor(handler.getAreaColor());
		g.drawRect((int)x, (int)y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);
	}

}
