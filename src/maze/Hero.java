package maze;

public class Hero extends Character {

	private boolean armed;
	private boolean eagle;

	public Hero(int posX, int posY) {
		super(posX, posY);
		this.armed = false;
		this.eagle = true;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}

	public void setEagle(boolean eagle) {
		this.eagle = eagle;
	}

	public boolean getArmed() {
		return armed;
	}

	public boolean getEagle() {
		return eagle;
	}

	public String showHero() {
		return " " + (armed ? "A" : "H") + (eagle ? "G" : " ");
	}
}
