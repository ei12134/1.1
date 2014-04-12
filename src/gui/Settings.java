package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Settings extends JPanel {

	private static final long serialVersionUID = 1L;
	private Menu menu;
	private JButton size;
	private JButton counter;
	private JButton strategy;
	private JButton keyboard;
	private JButton close;
	private Dimension dimension;

	public Settings(final Menu menu, Dimension dimension) {
		this.menu = menu;
		this.dimension = dimension;
		size = new JButton("Maze size");
		counter = new JButton("Number of Dragons");
		strategy = new JButton("Dragon Strategy");
		keyboard = new JButton("Keyboard Configuration");
		close = new JButton("Close Settings");

		// Settings ActionListeners
		size.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String inputSize;
				String beginSize = Integer.toString(menu.getMazeSize());
				int newSize = menu.getMazeSize();
				do {
					inputSize = (String) JOptionPane.showInputDialog(null,
							(Object) "Enter odd random maze size:",
							"Random maze size", JOptionPane.OK_CANCEL_OPTION,
							null, null, beginSize);

					if (inputSize != null)
						newSize = Integer.parseInt((String) inputSize);
					else
						newSize = menu.getMazeSize();
				} while (newSize % 2 == 0 || newSize < 7);

				menu.setMazeSize(newSize);
			}
		});

		counter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String inputSize;
				String beginSize = Integer.toString(menu.getDragonCounter());
				int newCounter = menu.getDragonCounter(), mazeSize = menu
						.getMazeSize();
				do {
					inputSize = (String) JOptionPane
							.showInputDialog(
									null,
									(Object) "Enter number of Dragons\n(< "
											+ Integer.toString(menu
													.getMazeSize()) + "):",
									"Random maze dragon counter",
									JOptionPane.OK_CANCEL_OPTION, null, null,
									beginSize);

					if (inputSize != null)
						newCounter = Integer.parseInt((String) inputSize);
					else
						newCounter = menu.getDragonCounter();

				} while (newCounter >= mazeSize);
				menu.setDragonCounter(newCounter);
			}
		});

		strategy.addActionListener(new ActionListener() {
			@Override
			// problem on cancel
			public void actionPerformed(ActionEvent ev) {
				Object[] DragonStrategies = { "Idle", "Random Movement",
						"Random Movement and Sleeping" };
				String initialSelection = "Idle";
				int beginSize = menu.getDragonStrategy();

				if (beginSize == 0)
					initialSelection = "Idle";
				else if (beginSize == 1)
					initialSelection = "Random Movement";
				else if (beginSize == 2)
					initialSelection = "Random Movement and Sleeping";

				String selection = (String) JOptionPane.showInputDialog(null,
						"What strategy can the Dragon(s) use?",
						"Set Dragon Strategy", JOptionPane.QUESTION_MESSAGE,
						null, DragonStrategies, initialSelection);

				if (selection != null) {
					if (selection.equals("Idle"))
						menu.setDragonStrategy(0);
					else if (selection.equals("Random Movement"))
						menu.setDragonStrategy(1);
					else if (selection.equals("Random Movement and Sleeping"))
						menu.setDragonStrategy(2);
				}
			}
		});

		keyboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
			}
		});

		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				closeSettingsPanel();
			}
		});
	}

	public void setSettingsPanel() {
		this.setSize(dimension);
		this.setLayout(new GridBagLayout());
		GridBagConstraints style = new GridBagConstraints();
		style.fill = GridBagConstraints.BOTH;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridheight = 3;
		style.gridwidth = 3;
		style.gridx = 0;
		style.insets = new Insets(32, 256, 32, 256);
		this.add(size, style);
		this.add(counter, style);
		this.add(strategy, style);
		this.add(keyboard, style);
		this.add(close, style);
		this.setVisible(true);
	}

	public void closeSettingsPanel() {
		this.setVisible(false);
		menu.remove(this);
		menu.showPanel(menu.getMenuPanel());
		menu.setVisible(true);
	}

	public void closeMenuPanel() {
		menu.remove(menu.getMenuPanel());
	}
}
