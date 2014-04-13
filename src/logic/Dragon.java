package logic;

/**
 * Class <code>Dragon</code> stores all attributes for each dragon created.
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Dragon extends Character {

	private boolean guarding;
	private boolean asleep;

	/**
	 * Constructor that initializes its superclass <code>Character</code> and
	 * sets dragon position and makes it alive
	 * 
	 * @param posX
	 *            initial X position integer
	 * @param posY
	 *            initial Y position integer
	 */
	public Dragon(int posX, int posY) {
		super(posX, posY);
	}

	/**
	 * Ciphers the appropriate method to display the dragon current status.
	 * 
	 * @return <code>String</code> used to display the dragon
	 */
	public String showDragon() {
		if (!getDead()) {
			if (getGuarding() && getAsleep())
				return " f ";
			else if (getGuarding())
				return " F ";
			else if (getAsleep())
				return " d ";
			else
				return " D ";
		} else
			return "   ";
	}

	/**
	 * Gets guarding status of the dragon.
	 * 
	 * @return true if guarding the sword, false otherwise
	 */
	public boolean getGuarding() {
		return guarding;
	}

	/**
	 * Gets asleep status of the dragon.
	 * 
	 * @return true if asleep, false otherwise
	 */
	public boolean getAsleep() {
		return asleep;
	}

	/**
	 * Sets guarding status of the dragon.
	 * 
	 * @param guarding
	 *            true if at sword position or false otherwise
	 */
	public void setGuarding(boolean guarding) {
		this.guarding = guarding;
	}

	/**
	 * Sets asleep status of the dragon.
	 * 
	 * @param asleep
	 *            true asleep, false otherwise
	 */
	public void setAsleep(boolean asleep) {
		this.asleep = asleep;
	}
}
