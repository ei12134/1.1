package logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

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
	private JButton button;

	public Piece(int posX, int posY, String symbol) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
	}

	public Piece(int posX, int posY, String symbol, JButton button) {
		this.posX = posX;
		this.posY = posY;
		this.symbol = symbol;
		this.button = button;
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

	public JButton getButton() {
		return button;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		/*
		 * tamanho deve ser determinado como estou a fazer em GameUI
		 * 
		 * widthPixelsPerTile = dimension.width / boardSize; 
		 * heightPixelsPerTile = dimension.height / boardSize;
		 */
		g2.fillRect(15 * getPosX(), 15 * getPosY(), 128, 128);
		g2.setPaint(Color.WHITE);
		// g2.dispose();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(30, 30);
	}
}
