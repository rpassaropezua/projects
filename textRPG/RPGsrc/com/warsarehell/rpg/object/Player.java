package com.warsarehell.rpg.object;

import java.util.ArrayList;

public class Player {
	private String name;
	private int attack = 10;
	private int strength = 5;
	private int maxHealth;
	private int health;
	private int locationX;
	private int locationY;
	private int level;
	private int experience;
	private boolean attacking;
	private boolean healing;
	private boolean running;
	private Weapon currentWeapon;
	private ArrayList<Weapon> weaponInventory;
	private Armor currentArmor;
	private ArrayList<Armor> armorInventory;
	
	public Player() {
		
	}
	
	public Player(Player p) {
		this.name = p.getName();

	}
	
	public Player(String name, int health, int attack, int strength, int level) {
		this.name = name;
		this.maxHealth = health;
		this.health = health;
		this.attack = attack;
		this.strength = strength;
		this.level = level;
	}
	
	public Player(int health, int locationX, int locationY, int level, int experience) {
		this.maxHealth = health;
		this.health = health;
		this.locationX = locationX;
		this.locationY = locationY;
		this.level = level;
		this.experience = experience;
	}
	
	public Player(String name, int health, int locationX, int locationY, int level, int experience) {
		this.name = name;
		this.maxHealth = health;
		this.health = health;
		this.locationX = locationX;
		this.locationY = locationY;
		this.level = level;
		this.experience = experience;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getLocationX() {
		return locationX;
	}
	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	public int getLocationY() {
		return locationY;
	}
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	public void moveX(int moveAmount) {
		if(((this.locationX + (this.locationX + moveAmount)) > 0) && ((this.locationX + moveAmount) < 20)) {
			this.locationX += moveAmount;
		}
	}
	
	public void moveY(int moveAmount) {
		if(((this.locationY +(this.locationY + moveAmount)) > 0) && ((this.locationY + moveAmount) < 20)) {
			this.locationY += moveAmount;
		}
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void takeDamage(int damage) {
		this.health -= damage;
	}

	public Weapon getCurrentWeapon() {
		return currentWeapon;
	}

	public void setCurrentWeapon(Weapon currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	public ArrayList<Weapon> getWeaponInventory() {
		return weaponInventory;
	}

	public void setWeaponInventory(ArrayList<Weapon> weaponInventory) {
		this.weaponInventory = weaponInventory;
	}
	
	public void addWeaponToInv(Weapon newWeapon) {
		this.weaponInventory.add(newWeapon);
	}

	public ArrayList<Armor> getArmorInventory() {
		return armorInventory;
	}

	public void setArmorInventory(ArrayList<Armor> armorInventory) {
		this.armorInventory = armorInventory;
	}
	
	public void addArmorToInv(Armor newArmor) {
		this.armorInventory.add(newArmor);
	}

	public Armor getCurrentArmor() {
		return currentArmor;
	}

	public void setCurrentArmor(Armor currentArmor) {
		this.currentArmor = currentArmor;
	}
	
	public int getHitChance() {
		return (this.currentWeapon.getAccuracy() + attack);
	}
	
	public int getDamageDealt() {
		return (this.currentWeapon.getDamage() + strength);
	}
	
	public int getBlockChance() {
		return this.currentArmor.getDefence();
	}
	
	public int getCriticalChance() {
		return this.currentWeapon.getCriticalChance();
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	public void heal(int heal) {
		this.health += heal;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
		this.healing = false;
		this.running = false;
	}

	public boolean isHealing() {
		return healing;
	}

	public void setHealing(boolean healing) {
		this.healing = healing;
		this.attacking = false;
		this.running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
		this.attacking = false;
		this.healing = false;
	}
	
	public String getData() {
		Weapon curWeapon = this.getCurrentWeapon();
		Armor curArmor = this.getCurrentArmor();
		/*String characterData = (this.name 
							+ " attack: " + this.attack 
							+ " strength: " + this.strength
							+ " experience: " + this.experience 
							+ " currenthealth: " + this.health 
							+ " level: " + this.level 
							+ " x: " + this.locationX
							+ " y: " + this.locationY
							+ " " + curWeapon.getName()
							+ " damage: " + curWeapon.getDamage()
							+ " accuracy: " + curWeapon.getAccuracy()
							+ " attackSpeed: " + curWeapon.getAttackSpeed()
							+ " criticalChance: " + curWeapon.getCriticalChance()
							+ " value: " + curWeapon.getValue()
							+ " " + curArmor.getName()
							+ " defence: " + curArmor.getDefence());*/
		String characterData = (this.name 
				+ " " + this.attack 
				+ " " + this.strength
				+ " " + this.experience 
				+ " " + this.health 
				+ " " + this.level 
				+ " " + this.locationX
				+ " " + this.locationY
				+ " " + curWeapon.getName()
				+ " " + curWeapon.getDamage()
				+ " " + curWeapon.getAccuracy()
				+ " " + curWeapon.getAttackSpeed()
				+ " " + curWeapon.getCriticalChance()
				+ " " + curWeapon.getValue()
				+ " " + curArmor.getName()
				+ " " + curArmor.getDefence());
		return characterData;
	}
	
}
