package maze;

public class Eagle extends Character {

	private String state;
	private int heroX, heroY;

	public Eagle(int posX, int posY) {
		super(posX, posY);
		this.state = State.EAGLE_FOLLOWING.toString();
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

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public int getHeroX() {
		return heroX;
	}

	public int getHeroY() {
		return heroY;
	}
}
