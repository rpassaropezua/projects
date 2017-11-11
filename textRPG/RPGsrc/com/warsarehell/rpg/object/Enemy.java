package com.warsarehell.rpg.object;

public class Enemy extends Player{
	
	public Enemy() {
		
	}

	public Enemy(String name, int health, int locationX, int locationY, int level, int experience) {
		super(name, health, locationX, locationY, level, experience);
	}
	public Enemy(String name, int health, int attack, int strength, int level) {
		super(name, health, attack, strength, level);
	}
	
	public String testDisplay() {
		return this.getName() + " " + this.getHealth() + " " + this.getAttack() + " " + this.getStrength() + " " + this.getLevel();
	}
	
	public void setTo(Enemy enemy) {
		this.setName(enemy.getName());
		this.setHealth(enemy.getHealth());
		this.setAttack(enemy.getAttack());
		this.setStrength(enemy.getStrength());
		this.setLevel(enemy.getLevel());
	}

}
