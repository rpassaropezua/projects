package com.warsarehell.shooter.framework;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.warsarehell.shooter.object.Weapon;

public abstract class GameObject {
	protected float x,y;
	protected OBJECTID id;
	protected boolean falling = true;
	protected boolean jumping = false;
	protected boolean facingRight = false;
	protected boolean facingLeft = false;
	protected Weapon currentWeapon;
	protected int projectileX;
	protected int projectileY;
	protected double hitPoints;
	protected double maxHP;
	protected int enemiesKilled;
	
	public boolean isFalling() {
		return falling;
	}
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	public boolean isJumping() {
		return jumping;
	}
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	protected float velX = 0,velY = 0;
	public GameObject(float x, float y, OBJECTID id){
		this.x=x;
		this.y=y;
		this.id=id;
	}
	public abstract void update(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void setX(float x){
		this.x=x;
	}
	public void setY(float y){
		this.y=y;
	}
	
	public float getVelX(){
		return this.velX;
	}
	public float getVelY(){
		return this.velY;
	}
	public void setVelX(float velX){
		this.velX=velX;
	}
	public void setVelY(float velY){
		this.velY=velY;
	}
	
	public OBJECTID getId(){
		return this.id;
	}
	public void faceRight(boolean change){
		this.facingRight=change;
	}
	public void faceLeft(boolean change){
		this.facingLeft=change;
	}
	public boolean isFacingRight(){
		return facingRight;
	}
	public boolean isFacingLeft(){
		return facingLeft;
	}
	public Weapon getCurrentWeapon(){
		return currentWeapon;
	}
	public void setCurrentWeapon(Weapon weapon){
		this.currentWeapon=weapon;
	}
	public void setProjectileX(int x){
		this.projectileX=(((int)this.x)+x);
	}
	public void setProjectileY(int y){
		this.projectileY=(((int)this.y)+y);
	}
	public int getProjectileX(){
		return this.projectileX;
	}
	public int getProjectileY(){
		return this.projectileY;
	}
	public void takeDamage(double damage){
		this.hitPoints-=damage;
	}
	public void enemyKilled(){
		this.enemiesKilled++;
	}
	public void setMaxHP(double maxHP){
		this.maxHP=maxHP;
	}
	public void setHP(double hP){
		this.hitPoints=hP;
	}
	

}
