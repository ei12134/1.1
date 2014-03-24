package maze;

public class Eagle extends Character {

	private boolean pursuing;
	private boolean returning;
	private int heroX, heroY;

	public Eagle(int posX, int posY) {
		super(posX, posY);
		this.pursuing = false;
		this.returning = false;
	}

	public char showEagle() {
		return 'G';
	}

	public void setPursuing(boolean pursuing) {
		this.pursuing = pursuing;
	}

	public void setReturning(boolean returning) {
		this.returning = returning;
	}

	public void setHeroX(int heroX) {
		this.heroX = heroX;
	}
	
	public void setHeroY(int heroY) {
		this.heroY = heroY;
	}
	
	public boolean getPursuing() {
		return pursuing;
	}

	public boolean getReturning() {
		return returning;
	}
	
	public int getHeroX() {
		return heroX;
	}
	
	public int getHeroY() {
		return heroY;
	}
}
