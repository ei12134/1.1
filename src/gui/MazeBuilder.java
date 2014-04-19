package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.util.ArrayList;

import logic.Piece;
import logic.PieceType;

public class MazeBuilder extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private Play play;
	private ArrayList<ArrayList<Piece>> maze;
	private ArrayList<PieceType> types;
	private int mazeSize, dragonCounter, dragonStrategy;
	private Dimension dimension;

	MazeBuilder(final Play play, int boardSize, int dragonCounter,
			int dragonStrategy, Dimension dimension) {
		this.play = play;
		this.mazeSize = boardSize;
		this.dragonCounter = dragonCounter;
		this.dragonStrategy = dragonStrategy;
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
		types.add(PieceType.HERO_UNARMED_EAGLE);
		types.add(PieceType.SWORD);
		types.add(PieceType.DRAGON);
	}

	public void populateGameButtons() {
		// The maze size is MxM, where M is an odd number
		for (int i = 0; i < mazeSize; i++) {
			ArrayList<Piece> row = new ArrayList<Piece>();
			for (int j = 0; j < mazeSize; j++)
				row.add(new Piece(j, i, PieceType.FREE.asString(),
						new ImageIcon("src//png//path.png")));
			maze.add(row);
		}

		for (int i = 0; i < mazeSize; i++) {
			maze.get(0).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(0).get(i)
					.setImage(new ImageIcon("src//png//wall_red.png"));
			maze.get(mazeSize - 1).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(mazeSize - 1).get(i)
					.setImage(new ImageIcon("src//png//wall_red.png"));
			maze.get(i).get(0).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(0)
					.setImage(new ImageIcon("src//png//wall_red.png"));
			maze.get(i).get(mazeSize - 1).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(mazeSize - 1)
					.setImage(new ImageIcon("src//png//wall_red.png"));
		}
	}

	public void startMazeBuilder() {
		for (int i = 0; i < maze.size(); i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				add(maze.get(i).get(j));

				maze.get(i).get(j).addMouseListener(new MouseListener() {

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
						p = maze.get(p.getPosY()).get(p.getPosX());
						// JOptionPane.showConfirmDialog(null, p.getPosY() +
						// " - " + p.getPosX());
						if ((p.getPosX() == 0 && p.getPosY() == 0)
								|| (p.getPosX() == mazeSize - 1 && p.getPosY() == 0)
								|| (p.getPosX() == 0 && p.getPosY() == mazeSize - 1)
								|| (p.getPosX() == mazeSize - 1 && p.getPosY() == mazeSize - 1)) {
							JOptionPane.showMessageDialog(null,
									"This Piece can't be changed");
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
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).asString() != p.getSymbol()) {
				item = new JMenuItem(types.get(i).asString());
				item.addMouseListener(listener);
				popup.add(item);
			}
		}
		popup.show(p, 0, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_A) {
			if (checkPuzzle(maze)) {
				// Ask the user to enter a filename
				String filename = "";
				filename = JOptionPane
						.showInputDialog("Enter filename");

				saveMaze(filename, maze);
			} else {
				JOptionPane.showMessageDialog(null,
						"Invalid maze! Checking again...");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Object[] options = { "Yes", "No" };
			int confirm = JOptionPane.showOptionDialog(null,
					"Are you sure you want to abort building a maze?", "Abort game",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
			if (confirm == 0)
				play.closeMazeBuilder();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public boolean checkPuzzle(ArrayList<ArrayList<Piece>> maze) {
		boolean has_exit = false;
		boolean has_dragon = false;
		boolean has_sword = false;
		boolean has_hero = false;

		for (int i = 0; i < maze.size(); i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				if (maze.get(j).get(i).getSymbol()
						.equals(PieceType.EXIT.asString())) {
					if (has_exit)
						return false;
					else
						has_exit = true;
				} else if (maze.get(j).get(i).getSymbol()
						.equals(PieceType.DRAGON.asString())) {
					if (!has_dragon)
						has_dragon = true;
				} else if (maze.get(j).get(i).getSymbol()
						.equals(PieceType.HERO_UNARMED_EAGLE.asString())) {
					if (has_hero)
						return false;
					else
						has_hero = true;
				} else if (maze.get(j).get(i).getSymbol()
						.equals(PieceType.SWORD.asString())) {
					if (has_sword)
						return false;
					else
						has_sword = true;
				}
			}
		}
		if (has_exit)
			JOptionPane.showMessageDialog(null, "Exit already exists");
		if (has_dragon)
			JOptionPane.showMessageDialog(null, "Already has a dragon");
		if (has_hero)
			JOptionPane.showMessageDialog(null, "Hero defined already");
		if (has_sword)
			JOptionPane.showMessageDialog(null, "Sword already exists");

		if (has_exit && has_dragon && has_sword && has_hero)
			return true;

		return false;
	}

	public void saveMaze(String filename, ArrayList<ArrayList<Piece>> maze) {
		// We should not write the images to the file
		ArrayList<ArrayList<Piece>> tmp = new ArrayList<ArrayList<Piece>>();
		for (int i = 0; i < maze.size(); i++) {
			ArrayList<Piece> row = new ArrayList<Piece>();
			for (int j = 0; j < maze.get(i).size(); j++) {
				row.add(new Piece(j, i, maze.get(i).get(j).getSymbol()));
			}
			tmp.add(row);
		}

		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;

		try {
			outStream = new FileOutputStream(filename + ".puzzle");
			objStream = new ObjectOutputStream(outStream);

			objStream.writeObject(tmp);
			objStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class PopupListener extends MouseAdapter {
	Piece p;
	int mazeSize;

	public PopupListener(Piece p, int mazeSize) {
		this.p = p;
		this.mazeSize = mazeSize;
	}

	public void setData(ImageIcon img, String symbol) {
		p.setSymbol(symbol);
		p.setImage(img);
	}

	public void mousePressed(MouseEvent e) {
		Component x = e.getComponent();
		if (x instanceof JMenuItem) {
			String tmp = ((JMenuItem) x).getText();
			if (tmp.equals(PieceType.WALL.asString())) {
				setData(new ImageIcon("src//png//wall_red.png"),
						PieceType.WALL.asString());
			} else if (tmp.equals(PieceType.FREE.asString())) {
				if (p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null,
							"N‹o Ž poss’vel marcar esta pe�a como livre!");
				} else {
					setData(new ImageIcon("src//png//path.png"),
							PieceType.FREE.asString());
				}
			} else if (tmp.equals(PieceType.EXIT.asString())) {
				if (p.getPosX() != 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null,
							"N‹o Ž poss’vel marcar a sa’da nesta pe�a!");
				} else {
					if (p.getPosX() == 0) {
						setData(new ImageIcon("src//png//exit.png"),
								PieceType.EXIT.asString());
					} else {
						setData(new ImageIcon("src//png//exit_symmetrical.png"),
								PieceType.EXIT.asString());
					}
				}
			} else if (tmp.equals(PieceType.HERO_UNARMED_EAGLE.asString())) {
				if (p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null,
							"N‹o Ž poss’vel colocar o her—i nesta pe�a!");
				} else {
					setData(new ImageIcon("src//png//hero_unarmed_eagle.png"),
							PieceType.HERO_UNARMED_EAGLE.asString());
				}
			} else if (tmp.equals(PieceType.SWORD.asString())) {
				if (p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null,
							"N‹o Ž poss’vel adicionar a espada nesta pe�a!");
				} else {
					setData(new ImageIcon("src//png//sword.png"),
							PieceType.SWORD.asString());
				}
			} else if (tmp.equals(PieceType.DRAGON.asString())) {
				if (p.getPosX() == 0 || p.getPosX() == mazeSize - 1) {
					JOptionPane.showMessageDialog(null,
							"N‹o Ž poss’vel colocar o drag‹o nesta posi�‹o!");
				} else {
					setData(new ImageIcon("src//png//dragon.png"),
							PieceType.DRAGON.asString());
				}
			}
		}
	}
}