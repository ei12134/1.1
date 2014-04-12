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
				new CustomDialog(menu,"hello");
			}
		});

		strategy.addActionListener(new ActionListener() {
			@Override
			// problem on cancel
			public void actionPerformed(ActionEvent ev) {
				Object[] DragonStrategies = { "Idle", "Random Movement",
						"Random Movement and Sleeping" };
				String initialSelection = "Idle";
				Object selection = JOptionPane.showInputDialog(null,
						"Set Dragon Strategy", "Settings",
						JOptionPane.QUESTION_MESSAGE, null, DragonStrategies,
						initialSelection);
				if (selection.equals("Idle"))
					menu.setDragonStrategy(0);
				else if (selection.equals("Random Movement"))
					menu.setDragonStrategy(1);
				else
					menu.setDragonStrategy(2);
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
