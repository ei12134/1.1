package maze;

public class Dragon extends Character {
 
	private boolean guarding;
	private boolean asleep;

	public Dragon(int posX, int posY) {
		super(posX, posY);
	}

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
		} else {
			return "   ";
		}
	}

	public void setGuarding(boolean guarding) {
		this.guarding = guarding;
	}

	public void setAsleep(boolean asleep) {
		this.asleep = asleep;
	}

	public boolean getGuarding() {
		return guarding;
	}

	public boolean getAsleep() {
		return asleep;
	}

}
