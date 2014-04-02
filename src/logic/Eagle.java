package logic;

public class Eagle extends Character {

	private State state;
	private int heroX, heroY;

	public Eagle(int posX, int posY) {
		super(posX, posY);
		this.state = State.EAGLE_FOLLOWING;
	}

	public char showEagle() {
		return 'G';
	}

	public void setHeroX(int heroX) {
		this.heroX = heroX;
	}

	public void setHeroY(int heroY) {
		this.heroY = heroY;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public int getHeroX() {
		return heroX;
	}

	public int getHeroY() {
		return heroY;
	}
}
