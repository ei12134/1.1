package logic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.ImageIcon;
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
	private int heightPixelsPerTile, widthPixelsPerTile;
	private String symbol;
	private ImageIcon image;

	public Piece(int posX, int posY, String symbol) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
	}

	public Piece(int posX, int posY, String symbol, ImageIcon image,
			int widthPixelsPerTile, int heightPixelsPerTile) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
		this.image = image;
		this.widthPixelsPerTile = widthPixelsPerTile;
		this.heightPixelsPerTile = heightPixelsPerTile;
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

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		if (symbol.equals(PieceType.FREE.asString())
				|| symbol.equals(PieceType.WALL.asString()))
			g2.drawImage(image.getImage(), 0, 0, this.getWidth(),
					this.getHeight(), widthPixelsPerTile * posX,
					heightPixelsPerTile * posY, widthPixelsPerTile * posX
							+ widthPixelsPerTile, heightPixelsPerTile * posY
							+ heightPixelsPerTile, null);
		else {
			g2.drawImage(new ImageIcon("src//png//path.png").getImage(), 0, 0,
					this.getWidth(), this.getHeight(), widthPixelsPerTile
							* posX, heightPixelsPerTile * posY,
					widthPixelsPerTile * posX + widthPixelsPerTile,
					heightPixelsPerTile * posY + heightPixelsPerTile, null);

			g2.drawImage(image.getImage(), 0, 0, this.getWidth(),
					this.getHeight(), this);
		}
		g2.dispose();
	}
}
