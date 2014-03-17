package maze;

public class Hero extends Character {

	private boolean armed;

	public Hero(int posX, int posY) {
		super(posX, posY);
		this.armed = false;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}

	public boolean getArmed() {
		return armed;
	}

	public char showHero() {
		if (armed)
			return 'A';

		return 'H';
	}
}
