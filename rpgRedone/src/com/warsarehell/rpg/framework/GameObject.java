package com.warsarehell.rpg.framework;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.warsarehell.rpg.object.Weapon;

public abstract class GameObject {
	protected float x, y;
	protected OBJECTID id;
	protected boolean attacking;
	protected boolean defending;
	protected int maxHitPoints;
	protected int currentHitPoints;
	protected Weapon currentWeapon;
	
	public GameObject(float x, float y, OBJECTID id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public GameObject() {
		
	}
	
	public void setX(float newX){
		this.x = newX;
	}
	
	public float getX(){
		return this.x;
	}
	
	public void setY(float newY){
		this.y = newY;
	}
	
	public float getY(){
		return this.y;
	}
	
	public void setAttacking(boolean attack){
		this.attacking = attack;
	}
	
	public boolean isAttacking(){
		return this.attacking;
	}
	
	public void setDefending(boolean defend){
		this.defending = defend;
	}
	
	public boolean isDefending(){
		return this.defending;
	}
	
	public void setWeapon(Weapon newWeapon){
		this.currentWeapon = newWeapon;
	}
	
	public Weapon getWeapon(){
		return this.currentWeapon;
	}
	
	public void setCurrentHitPoints(int newCurrentHitPoints){
		this.currentHitPoints = newCurrentHitPoints;
	}
	
	public int getCurrentHitPoints(){
		return this.currentHitPoints;
	}
	
	public void takeDamage(int damage){
		this.currentHitPoints -= damage;
	}
	
	public void heal(int heal){
		this.currentHitPoints += heal;
	}
	
	public void setMaxHitPoints(int newMaxHitPoints){
		this.maxHitPoints = newMaxHitPoints;
	}
	
	public int getMaxHitPoints(){
		return this.maxHitPoints;
	}
	
	public abstract void update(LinkedList<GameObject> object);
	public void render(Graphics2D g){
		g.draw(new Rectangle());
	};
	public abstract Rectangle getBounds();

	public Object getId() {
		return this.id;
	}
	
}
