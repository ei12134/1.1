package logic;

public class Character {

	private int posX;
	private int posY;
	private boolean dead;

	public Character(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		dead = false;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean getDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
}
