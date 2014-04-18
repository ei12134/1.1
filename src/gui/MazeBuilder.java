package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.util.ArrayList;

import logic.Piece;
import logic.PieceType;


public class MazeBuilder extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Menu menu;
	private int mazeSize;
	private ArrayList<ArrayList<Piece>> maze;
	private ArrayList<PieceType> types;	


	public MazeBuilder(final Menu menu, int mazeSize) {
		this.menu = menu;
		this.frame = menu.getFrame();
		this.mazeSize = mazeSize;
		maze = new ArrayList<ArrayList<Piece>>();
		types = new ArrayList<PieceType>();
		addKeyListener(this);

		setLayout(new GridLayout(mazeSize, mazeSize));
		populateTypes();
		populateGameButtons();
	}
	
	
	/**
	 * The JPanel does not have the keyboard focus. We need to requestFocus when 
	 * the panel is added to the screen
	 */
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }

	
	public void populateTypes() {
		types.add(PieceType.FREE);
		types.add(PieceType.WALL);
		types.add(PieceType.EXIT);
		types.add(PieceType.HERO_UNARMED);
		types.add(PieceType.SWORD);
	}
	
	
	public void populateGameButtons() {
		// The maze size is MxM, where M is an odd number
		for (int i = 0; i < mazeSize; i++) {
			ArrayList<Piece> row = new ArrayList<Piece>();
			for (int j = 0; j < mazeSize; j++)
				row.add(new Piece(j, i, PieceType.FREE.asString()));
			maze.add(row);
		}

		for (int i = 0; i < mazeSize; i++) {
			maze.get(0).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(mazeSize - 1).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(0).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(mazeSize - 1).setSymbol(PieceType.WALL.asString());
		}
	}

	
	
	public void startMazeBuilder() {
		for (int i = 0; i < maze.size(); i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				Piece piece = new Piece(0, 0, "");
				if (maze.get(j).get(i).getSymbol() == PieceType.FREE.asString()) {
					piece = new Piece(j, i, PieceType.FREE.asString(), 
							new ImageIcon("src//png//path.png").getImage());
					add(piece);
				} else if (maze.get(j).get(i).getSymbol() == PieceType.WALL
						.asString()) {
					piece = new Piece(j, i, PieceType.FREE.asString(), 
							new ImageIcon("src//png//wall_red.png").getImage());
					add(piece);
				}

				piece.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseClicked(MouseEvent arg0) {
						Piece p = (Piece) arg0.getSource();
						if((p.getPosX() == 0 && p.getPosY() == 0) || (p.getPosX() == mazeSize - 1 && p.getPosY() == 0) || 
								(p.getPosX() == 0 && p.getPosY() == mazeSize - 1) || (p.getPosX() == mazeSize - 1 && p.getPosY() == mazeSize - 1)) {
							JOptionPane.showMessageDialog(null, "N‹o Ž poss’vel editar esta pea!");
						} else 
							showPopup(p);
					}
				});
			}
		}
	}
	
	
	public void showPopup(Piece p) {
		JMenuItem item;
		MouseListener listener = new PopupListener(p, mazeSize);
		final JPopupMenu popup = new JPopupMenu();
		for(int i = 0; i < types.size(); i++)
			if(types.get(i).asString() != p.getSymbol()) {
				item = new JMenuItem(types.get(i).asString());
				item.addMouseListener(listener);
				popup.add(item);
			}
		popup.show(p, 0, 0);
	}

	
	public void closeMenuPanel() {
		frame.remove(menu);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_1) {
			//Prompt the user for a filename
			String filename = "";
			filename = JOptionPane.showInputDialog("Escolher o nome do ficheiro...");
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}
}



class PopupListener extends MouseAdapter {
	Piece p;
	int mazeSize;
	
	
	public PopupListener(Piece p, int mazeSize) {
		this.p = p;
		this.mazeSize = mazeSize;
	}
	
	
	public void setData(Image img, String symbol) {
		p.setSymbol(symbol);
		p.setImage(img);
	}
	
	
	public void mousePressed(MouseEvent e) {
		Component x = e.getComponent();
		if(x instanceof JMenuItem) {
			String tmp = ((JMenuItem) x).getText();
			if(tmp.equals(PieceType.WALL.asString())) {
				setData(new ImageIcon("src//png//wall_red.png").getImage(), PieceType.WALL.asString());
			} else if(tmp.equals(PieceType.FREE.asString())) {
				if(p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null, "N‹o Ž poss’vel marcar esta pea como livre!");
				} else {
					setData(new ImageIcon("src//png//path.png").getImage(), PieceType.FREE.asString());
				}
			} else if(tmp.equals(PieceType.EXIT.asString())) {
				if(p.getPosX() != 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null, "N‹o Ž poss’vel marcar a sa’da nesta pea!");
				} else {
					if(p.getPosX() == 0) {
						setData(new ImageIcon("src//png//exit.png").getImage(), PieceType.EXIT.asString());
					} else {
						setData(new ImageIcon("src//png//exit_symmetrical.png").getImage(), PieceType.EXIT.asString());
					}
				}
			} else if(tmp.equals(PieceType.HERO_UNARMED.asString())) {
				if(p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null, "N‹o Ž poss’vel colocar o her—i nesta pea!");
				} else {
					setData(new ImageIcon("src//png//hero_unarmed.png").getImage(), PieceType.HERO_UNARMED.asString());
				}
			} else if(tmp.equals(PieceType.SWORD.asString())) {
				if(p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null, "N‹o Ž poss’vel adicionar a espada nesta pea!");
				} else {
					setData(new ImageIcon("src//png//sword.png").getImage(), PieceType.SWORD.asString());
				}
			}
		}
	}
}