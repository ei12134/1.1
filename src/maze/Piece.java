package maze;

public class Piece {

	private int posX;
	private int posY;
	private char symbol;

	public Piece(int posX, int posY, char symbol) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
}
