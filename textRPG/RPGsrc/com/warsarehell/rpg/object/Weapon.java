package com.warsarehell.rpg.object;

public class Weapon {
	private String name;
	private int damage;
	private int accuracy;
	private int attackSpeed;
	private int criticalChance;
	private int value;
	
	public Weapon() {
		
	}
	
	public Weapon(String name, int damage, int accuracy, int attackSpeed, int criticalChance, int value) {
		this.name = name;
		this.damage = damage;
		this.accuracy = accuracy;
		this.attackSpeed = attackSpeed;
		this.criticalChance = criticalChance;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getCriticalChance() {
		return criticalChance;
	}

	public void setCriticalChance(int criticalChance) {
		this.criticalChance = criticalChance;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
}
