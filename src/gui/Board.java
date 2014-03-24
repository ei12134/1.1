package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	public Timer temporizador;
	
	
	public Board() {
		temporizador = new Timer (25,this);
		temporizador.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		
	}
	
	public void draw(Graphics g){
		super.paint(g);
		g.setColor(Color.red);
		g.fillRect(45, 60, 32, 32);
	//	g.draw3DRect(1, 2, 3, 4, true);
		
	}

}
