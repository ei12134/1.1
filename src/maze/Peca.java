package maze;

public class Peca {

	private int posX;
	private int posY;
	private char simbolo;

	public Peca(int posX, int posY, char simbolo) {
		this.posX = posX;
		this.posY = posY;
		this.simbolo = simbolo;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public char getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(char simbolo) {
		this.simbolo = simbolo;
	}
}
