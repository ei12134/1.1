package logic;

/**
 * Class <code>Eagle</code> stores all attributes the eagle needs to follow the
 * hero, pursue and return the sword.
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Eagle extends Character {

	private State state;
	private int heroX, heroY;

	/**
	 * Constructor that initializes its superclass <code>Character</code> and
	 * sets eagle position and makes it alive and following the hero.
	 * 
	 * @param posX
	 *            initial X position integer
	 * @param posY
	 *            initial Y position integer
	 */
	public Eagle(int posX, int posY) {
		super(posX, posY);
		this.state = State.EAGLE_FOLLOWING;
	}

	/**
	 * Gets the current <code>State</code> of the eagle.
	 * 
	 * @return current <code>State</code> of the eagle
	 */
	public State getState() {
		return state;
	}

	/**
	 * Gets the hero horizontal position initialized at the time of the eagle
	 * release.
	 * 
	 * @return hero custom X position
	 */
	public int getHeroX() {
		return heroX;
	}

	/**
	 * Gets the hero Y position initialized at the time of the eagle release.
	 * 
	 * @return hero custom Y position
	 */
	public int getHeroY() {
		return heroY;
	}

	/**
	 * Gets eagle char to represent eagle in the maze.
	 * 
	 * @return eagle char
	 */
	public char showEagle() {
		return 'G';
	}

	/**
	 * Sets hero custom X position at the time of eagle release.
	 * 
	 * @param heroX
	 *            position
	 */
	public void setHeroX(int heroX) {
		this.heroX = heroX;
	}

	/**
	 * Sets hero custom y position at the time of eagle release.
	 * 
	 * @param heroY
	 *            position
	 */
	public void setHeroY(int heroY) {
		this.heroY = heroY;
	}

	/**
	 * Sets eagle new <code>State</code>.
	 * 
	 * @param state
	 *            new eagle state
	 */
	public void setState(State state) {
		this.state = state;
	}
}
