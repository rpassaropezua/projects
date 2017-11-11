package com.warsarehell.rpg.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import com.warsarehell.rpg.object.Armor;
import com.warsarehell.rpg.object.Enemy;
import com.warsarehell.rpg.object.Player;
import com.warsarehell.rpg.object.Weapon;

public class Logic {
	private Player player;
	private Enemy enemy = new Enemy();
	private final int ENCOUNTER_THRESHOLD = 25;
	private final String ENEMYFILE = "RPGsrc/resources/enemies";
	private ArrayList<Enemy> enemyList = new ArrayList<>();
	private Scanner reader;
	
	public void init() {
		//String line = null;
		try {
			reader = new Scanner(new File(ENEMYFILE));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enemy tempEnemy = new Enemy();
		while(reader.hasNext()) {
			tempEnemy.setName(reader.next());
			tempEnemy.setHealth(reader.nextInt());
			tempEnemy.setAttack(reader.nextInt());
			tempEnemy.setStrength(reader.nextInt());
			tempEnemy.setLevel(reader.nextInt());
			System.out.println(tempEnemy.testDisplay());
			enemyList.add(tempEnemy);
			tempEnemy = new Enemy();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public boolean encounterRoll() {
		if((int)(Math.random()*100) < ENCOUNTER_THRESHOLD){
			return true;
		} else {
			return false;
		}
	}
	
	public String combatStep() {
		String playerHitMsg = ("You hit the " + enemy.getName() + " for " + player.getDamageDealt() + " damage.");
		String enemyHitMsg = ("The " + enemy.getName() + " hits you for " + enemy.getDamageDealt() + " damage.");
		String playerCrit = ("You scored a critical hit! You deal " + player.getDamageDealt()*2 + " damage.");
		String enemyCrit = ("The " + enemy.getName() + " scored a critical hit on you! You take " + enemy.getDamageDealt()*2 + " damage.");
		String playerMiss = ("You missed!");
		String enemyMiss = (enemy.getName() + " missed!");
		String playerHeal = ("You heal 20 hitpoints.");
		String[] stepResult = new String[2];
		String resultString;
		
		if(player.isAttacking()){
			if(player.getCurrentWeapon().getAttackSpeed() > enemy.getCurrentWeapon().getAttackSpeed()) {
				if((int)(Math.random()*100) + player.getHitChance() > enemy.getBlockChance()) {
					if((int)(Math.random()*100) < player.getCriticalChance()) {
						stepResult[0] = playerCrit;
						enemy.takeDamage(player.getDamageDealt()*2);
					} else {
						stepResult[0] = playerHitMsg;
						enemy.takeDamage(player.getDamageDealt());
					}
				} else {
					stepResult[0] = playerMiss;
				}
				if((int)(Math.random()*100) + enemy.getHitChance() > player.getBlockChance()) {
					if((int)(Math.random()*100) < enemy.getCriticalChance()) {
						stepResult[1] = enemyCrit;
						player.takeDamage(enemy.getDamageDealt()*2);
					} else {
						stepResult[1] = enemyHitMsg;
						player.takeDamage(enemy.getDamageDealt());
					}
				} else {
					stepResult[1] = enemyMiss;
				}
			} else {
				if((int)(Math.random()*100) + enemy.getHitChance() > player.getBlockChance()) {
					if((int)(Math.random()*100) < enemy.getCriticalChance()) {
						stepResult[1] = enemyCrit;
						player.takeDamage(enemy.getDamageDealt()*2);
					} else {
						stepResult[1] = enemyHitMsg;
						player.takeDamage(enemy.getDamageDealt());
					}
				} else {
					stepResult[1] = enemyMiss;
				}
				
				if((int)(Math.random()*100) + player.getHitChance() > enemy.getBlockChance()) {
					if((int)(Math.random()*100) < player.getCriticalChance()) {
						stepResult[0] = playerCrit;
						enemy.takeDamage(player.getDamageDealt()*2);
					} else {
						stepResult[0] = playerHitMsg;
						enemy.takeDamage(player.getDamageDealt());
					
					}
				} else {
					stepResult[0] = playerMiss;
				}
			}
		} else if(player.isHealing()) {
			player.heal(20);
			stepResult[0] = playerHeal;
			if(player.getHealth() >= player.getMaxHealth()) {
				player.setHealth(player.getMaxHealth());
			}
			if((int)(Math.random()*100) + enemy.getHitChance() > player.getBlockChance()) {
				if((int)(Math.random()*100) < enemy.getCriticalChance()) {
					stepResult[1] = enemyCrit;
					player.takeDamage(enemy.getDamageDealt()*2);
				} else {
					stepResult[1] = enemyHitMsg;
					player.takeDamage(enemy.getDamageDealt());
				}
			} else {
				stepResult[1] = enemyMiss;
			}
		}
		resultString = "<html>" + stepResult[0] + "<br />" + stepResult[1] + "</html>";
		return resultString;
	}
	
	public Enemy createEnemy(int level) {
		int rng = (int)(Math.random() * 3);
		enemy.setTo(enemyList.get(rng));
		System.out.println(rng + " " + this.enemy.getName());
		enemy.setCurrentWeapon(new Weapon("test", 5, 10, 1, 10, 0));
		enemy.setCurrentArmor(new Armor("Test", 10));
		return enemy;
	}
	
	public boolean runRoll(int playerLevel, int enemyLevel) {
		if((Math.random()*10) + playerLevel > enemyLevel) {
			return true;
		} else {
			if((int)(Math.random()*100) + enemy.getHitChance() > player.getBlockChance()) {
				if((int)(Math.random()*100) < enemy.getCriticalChance()) {
					player.takeDamage(enemy.getDamageDealt()*2);
				} else {
					player.takeDamage(enemy.getDamageDealt());
				}
			}
			return false;
		}
	}
	
	public void saveFile() {
		try {
			PrintWriter writer = new PrintWriter("RPGsrc/saves/" + player.getName() + ".txt", "UTF-8");
			writer.print(player.getData());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadFile(File file) {
		Weapon loadWeapon;
		Armor loadArmor;
		try {
			reader = new Scanner(file);
			//System.out.println(reader.next());
			//System.out.println(reader.nextInt());
			player.setName(reader.next());
			player.setAttack(reader.nextInt());
			player.setStrength(reader.nextInt());
			player.setHealth(reader.nextInt());
			player.setLevel(reader.nextInt());
			player.setLocationX(reader.nextInt());
			player.setLocationY(reader.nextInt());
			System.out.println(player.getLocationX() + " " + player.getLocationY());
			//loadWeapon = new Weapon(reader.next(), reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
