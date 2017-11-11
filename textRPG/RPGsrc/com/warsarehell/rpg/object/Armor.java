package com.warsarehell.rpg.object;

public class Armor {
	private String name;
	private int defence;
	
	public Armor() {
		
	}
	
	public Armor(String name, int defence) {
		this.setName(name);
		this.setDefence(defence);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}
	
}
