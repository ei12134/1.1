package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.JComponent;



/**
 * Represents common attributes for each Piece of the maze
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Piece extends JComponent implements Serializable {

	private static final long serialVersionUID = 1L;
	private int posX;
	private int posY;
	private String symbol;
	private Image image;
	
	
	public Piece(int posX, int posY, String symbol) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
	}
	
	
	public Piece(int posX, int posY, String symbol, Image image) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
		this.image = image;
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
	
	
	public Image getImage() {
		return image;
	}
	
	
	public void setImage(Image image) {
		this.image = image;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(Color.BLUE);
		g2.drawImage(image, 0, 0, this.getWidth() - 1, this.getHeight() - 1, this);
		g2.dispose();
	}
}
