package maze;

public class Piece {

	private int posX;
	private int posY;
	private String symbol;

	public Piece(int posX, int posY, String symbol) {
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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
}
