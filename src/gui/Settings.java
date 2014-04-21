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
	private Play play;
	private JButton size;
	private JButton counter;
	private JButton strategy;
	private JButton keyboard;
	private JButton close;
	private Dimension dimension;

	public Settings(final Menu menu) {
		this.menu = menu;
		this.dimension = menu.getDimension();
		this.play = menu.getPlayPanel();
		size = new JButton("Maze size");
		counter = new JButton("Number of Dragons");
		strategy = new JButton("Dragon Strategy");
		keyboard = new JButton("Keyboard Configuration");
		close = new JButton("Close Settings");
		setSettingsPanel();

		// Settings ActionListeners
		size.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String inputSize;
				String beginSize = Integer.toString(play.getMazeSize());
				int newSize = play.getMazeSize();
				do {
					inputSize = (String) JOptionPane
							.showInputDialog(
									null,
									(Object) "Enter odd random maze size:\n(from 7 to 27)",
									"Random maze size",
									JOptionPane.OK_CANCEL_OPTION, null, null,
									beginSize);

					if (inputSize != null)
						newSize = Integer.parseInt((String) inputSize);
					else
						newSize = play.getMazeSize();
				} while (newSize % 2 == 0 || newSize < 7 || newSize > 27);

				if (newSize < play.getMazeSize()
						&& play.getDragonCounter() >= newSize / 2)
					play.setDragonCounter(newSize / 2 - 1);

				play.setMazeSize(newSize);
			}
		});

		counter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String inputSize;
				String beginSize = Integer.toString(play.getDragonCounter());
				int newCounter = play.getDragonCounter(), mazeSize = play
						.getMazeSize();
				do {
					inputSize = (String) JOptionPane
							.showInputDialog(
									null,
									(Object) "Enter number of Dragons (less than "
											+ Integer.toString(play
													.getMazeSize() / 2 + 1)
											+ "):",
									"Random maze dragon counter",
									JOptionPane.OK_CANCEL_OPTION, null, null,
									beginSize);

					if (inputSize != null)
						newCounter = Integer.parseInt((String) inputSize);
					else
						newCounter = play.getDragonCounter();

				} while (newCounter > mazeSize / 2);
				play.setDragonCounter(newCounter);
			}
		});

		strategy.addActionListener(new ActionListener() {
			@Override
			// problem on cancel
			public void actionPerformed(ActionEvent ev) {
				Object[] DragonStrategies = { "Idle", "Random Movement",
						"Random Movement and Sleeping" };
				String initialSelection = "Idle";
				int beginSize = play.getDragonStrategy();

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
						play.setDragonStrategy(0);
					else if (selection.equals("Random Movement"))
						play.setDragonStrategy(1);
					else if (selection.equals("Random Movement and Sleeping"))
						play.setDragonStrategy(2);
				}
			}
		});

		keyboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				startKeyboardConfiguration();
			}
		});

		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				menu.closePanel(menu.getSettingsPanel(), menu, "Maze Game");
			}
		});
	}

	public void setSettingsPanel() {
		setSize(dimension);
		setLayout(new GridBagLayout());
		GridBagConstraints style = new GridBagConstraints();
		style.fill = GridBagConstraints.BOTH;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridheight = 3;
		style.gridwidth = 3;
		style.gridx = 1;
		style.insets = new Insets(32, 256, 32, 256);
		add(size, style);
		add(counter, style);
		add(strategy, style);
		add(keyboard, style);
		add(close, style);
		setVisible(true);
	}

	public void startKeyboardConfiguration() {
		this.setVisible(false);
		menu.remove(this);
		KeyboardConfiguration keyboardPanel = new KeyboardConfiguration(menu,
				this);
		menu.showPanel(keyboardPanel, "Keyboard Configuration");
	}
}
