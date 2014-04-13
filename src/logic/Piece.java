package logic;

import java.io.Serializable;

/**
 * Represents common attributes for each Piece of the maze
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Piece implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int posX;
	private int posY;
	private String symbol;

	public Piece(int posX, int posY, String symbol) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
	}

	/**
	 * Gets X position value.
	 * 
	 * @return X integer position
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Gets Y position value.
	 * 
	 * @return Y integer position
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Gets Piece String symbol.
	 * 
	 * @return Piece String symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets new Piece symbol.
	 * 
	 * @param symbol
	 *            new Piece symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Set new Piece position
	 * 
	 * @param x
	 *            new X position value
	 * @param y
	 *            new Y position value
	 */
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
}
