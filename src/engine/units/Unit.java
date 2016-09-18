package engine.units;

import engine.tiles.Owner;

/**
 * Parent class for all Units, such as turrets, infintry, tanks, etc.
 * @author Chris
 * @author Daljit
 * @version 0.1
 */
public abstract class Unit {
	private int hp;
	private int moveRange;
	//private int movesRemaining;
	//private int researchTree;
	private int attDamage;
	private int attRange;
	//private int strength;
	//private int weakness;
	//private int updateStatus;
	private boolean canMine;
	private boolean canBuild;
	private Owner owner;
	
	/**
	 * When making a new unit must state its HP, number of steps allowed to take, damage, and range
	 */
	public Unit(int unitHp, int unitMoves, int damage, int range, boolean mine, boolean build, Owner cont) {
		this.hp = unitHp;
		this.moveRange = unitMoves;
		this.attDamage = damage;
		this.attRange = range;
		this.canMine = mine;
		this.canBuild = build;
		this.owner = cont;
	}
	
	public void setHp(int hitpoints){
		this.hp = hitpoints;
	}
	
	public int getHp(){
		return this.hp;
	}
	
	/**
	  * This is the number of steps a unit is allowed to move in one turn (upgradable to increase?)
	  */ 
	public void setRange(int numberOfMoves){
		this.moveRange = numberOfMoves;
	}
	
	public int getRange(){
		return this.moveRange;
	}
	
	public void setAttDamage(int unitDamage){
		this.attDamage = unitDamage;
	}
	
	public int getAttDamage(){
		return this.attDamage;
	}
	
	public void setAttRange(int unitAttRange){
		this.attRange = unitAttRange;
	}
	
	public int getAttRange(){
		return this.attRange;
	}
	
	/**
	  * States if the unit is alive or not
	  */
	public boolean isAlive(){
		int unitHp = this.getHp();
		if (unitHp <= 0){
			return true;
		}
		else return false;
		
	}
	
	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	/**
	  * Whenever damage is done, resets HP and checks to see if the unit is alive
	  */
	public void damageDealt(int damage){
		int unitHp = this.getHp();
		this.setHp(unitHp - damage);
		boolean isAlive = isAlive();
		if (isAlive = false){
		//Need to replace with what to do when a unit dies
			this.setAttDamage(0);
			this.setAttRange(0);
		}
	}
	
		

}
