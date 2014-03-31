package maze;

public class Eagle extends Character {

	private boolean pursuit;
	private boolean returning;
	private boolean ground;
	private boolean dead;
	private int heroX, heroY;

	public Eagle(int posX, int posY) {
		super(posX, posY);
		this.pursuit = false;
		this.returning = false;
		this.dead = false;
	}

	public char showEagle() {
		return 'G';
	}

	public void setPursuit(boolean pursuit) {
		this.pursuit = pursuit;
	}

	public void setReturning(boolean returning) {
		this.returning = returning;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setGround(boolean ground) {
		this.ground = ground;
	}

	public void setHeroX(int heroX) {
		this.heroX = heroX;
	}

	public void setHeroY(int heroY) {
		this.heroY = heroY;
	}

	public boolean getDead() {
		return dead;
	}

	public boolean getPursuit() {
		return pursuit;
	}

	public boolean getReturning() {
		return returning;
	}

	public boolean getGround(){
		return ground;
	}
	
	
	public int getHeroX() {
		return heroX;
	}

	public int getHeroY() {
		return heroY;
	}
}
