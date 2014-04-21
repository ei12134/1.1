package logic;

/**
 * Class <code>Character</code> represents an abstraction for each alive
 * character used in-game.
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Character {

	private int posX;
	private int posY;
	private boolean dead;
	
	
	/**
	 * Constructor that sets initial character position and makes it alive.
	 * 
	 * @param posX
	 *            initial X position integer
	 * @param posY
	 *            initial Y position integer
	 */
	public Character(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		dead = false;
	}

	
	/**
	 * Gets integer position variable posX.
	 * 
	 * @return X position integer
	 */
	public int getPosX() {
		return posX;
	}
	

	/**
	 * Gets integer position variable posY.
	 * 
	 * @return Y position integer
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Gets character alive status.
	 * 
	 * @return <code>boolean</code> true if dead or false if alive
	 */
	public boolean getDead() {
		return dead;
	}

	
	/**
	 * Sets a new position for the character.
	 * 
	 * @param x
	 *            new X position integer
	 * @param y
	 *            new Y position integer
	 */
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}

	
	/**
	 * Sets a new alive status for the character.
	 * 
	 * @param dead
	 *            sets the character dead if true or alive if false
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
