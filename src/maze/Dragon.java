package maze;

public class Dragon extends Character {

	private boolean atSword;
	private boolean asleep;
	private char mode;

	public Dragon(int posX, int posY) {
		super(posX, posY);
	}

	public char showDragon(Hero hero) {
		if (!getDead()) {
			if (getAtSword() && !hero.getArmed())
				return 'F';
			else if (getAsleep())
				return 'd';
			else
				return 'D';
		} else {
			return ' ';
		}
	}

	public void setAtSword(boolean espada) {
		atSword = espada;
	}

	public void setAsleep(boolean asleep) {
		this.asleep = asleep;
	}
	
	public void setMode(char mode) {
		this.mode = mode;
	}

	public boolean getAtSword() {
		return atSword;
	}

	public boolean getAsleep() {
		return asleep;
	}

	public char getMode() {
		return mode;
	}
}
