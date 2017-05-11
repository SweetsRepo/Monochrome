package game.units;

import engine.board.Owner;

/**
 * Parent class for all Units, such as turrets, infintry, tanks, etc.
 * @author Daljit
 * @coauthor Christopher Sweet - crs4263@rit.edu
 * @version 0.5
 */
public abstract class Unit {
	
	private int hp;
	private int moveRange;
	private int buildRange;
	private int attDamage;
	private int attRange;
	private boolean canMine;
	private boolean canBuild;
	
	/**
	 * When making a new unit must state its HP, number of steps allowed to take, damage, and range
	 */
	public Unit(int unitHp, int unitMoves, int buildRange, int damage, int range, boolean mine, boolean build) {
		this.hp = unitHp;
		this.moveRange = unitMoves;
		this.buildRange = buildRange;
		this.attDamage = damage;
		this.attRange = range;
		this.canMine = mine;
		this.canBuild = build;
	}
	
	/**
	 * Temp Constructor cause I added in buildRange and didn't want to update all subsequent classes
	 */
	public Unit(int unitHp, int unitMoves, int damage, int range, boolean mine, boolean build) {
		this.hp = unitHp;
		this.moveRange = unitMoves;
		this.buildRange = 0;
		this.attDamage = damage;
		this.attRange = range;
		this.canMine = mine;
		this.canBuild = build;
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
	
	public int getMoveRange() {
		return moveRange;
	}

	public void setMoveRange(int moveRange) {
		this.moveRange = moveRange;
	}

	public boolean canMine() {
		return canMine;
	}

	public void setCanMine(boolean canMine) {
		this.canMine = canMine;
	}

	public boolean canBuild() {
		return canBuild;
	}

	public void setCanBuild(boolean canBuild) {
		this.canBuild = canBuild;
	}

	public int getBuildRange() {
		return buildRange;
	}

	public void setBuildRange(int buildRange) {
		this.buildRange = buildRange;
	}

	/**
	  * States if the unit is alive or not
	  */
	private boolean isAlive(){
		int unitHp = this.getHp();
		if (unitHp <= 0){
			return true;
		}
		else return false;
		
	}

	/**
	  * Whenever damage is done, resets HP and checks to see if the unit is alive
	  */
	public boolean damageDealt(int damage){
		int unitHp = this.getHp();
		this.setHp(unitHp - damage);
		return isAlive();
	}
	
		

}
