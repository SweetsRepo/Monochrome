package game.units;

/**
 * Parent class for all Units, such as turrets, infintry, tanks, etc.
 * @author Chris
 * @author Daljit
 * @version 0.1
 */
public abstract class Unit {
	private int hp;
	private int moves;
	//private int movesRemaining;
	//private int researchTree;
	private int attDamage;
	private int attRange;
	//private int strength;
	//private int weakness;
	//private int updateStatus;
	private boolean canMine;
	private boolean canBuild;
	
	/**
	 * When making a new unit must state its HP, number of steps allowed to take, damage, and range
	 */
	public Unit(int unitHp, int unitMoves, int damage, int range, boolean mine, boolean build) {
		hp = unitHp;
		moves = unitMoves;
		attDamage = damage;
		attRange = range;
		canMine = mine;
		canBuild = build;
	}
	
	public void setHp(int hitpoints){
		hp = hitpoints;
	}
	
	public int getHp(){
		return hp;
	}
	
	/**
	  * This is the number of steps a unit is allowed to move in one turn (upgradable to increase?)
	  */ 
	public void setMoves(int numberOfMoves){
		moves = numberOfMoves;
	}
	
	//public int getmovesRemining()
	
	public void setAttDamage(int unitDamage){
		attDamage = unitDamage;
	}
	
	public int getAttDamage(){
		return attDamage;
	}
	
	public void setAttRange(int unitAttRange){
		attRange = unitAttRange;
	}
	
	public int getAttRange(){
		return attRange;
	}
	
	/**
	  * States if the unit is alive or not
	  */
	public boolean getIsAlive(){
		int unitHp = this.getHp();
		if (unitHp <= 0){
			return true;
		}
		else return false;
		
	}
	
	/**
	  * Whenever damage is done, resets HP and checks to see if the unit is alive
	  */
	public void damageDealt(int damage){
		int unitHp = this.getHp();
		this.setHp(unitHp - damage);
		boolean isAlive = getIsAlive();
		if (isAlive = false){
		//Need to replace with what to do when a unit dies
			this.setAttDamage(0);
			this.setAttRange(0);
		}
	}
	
		

}
