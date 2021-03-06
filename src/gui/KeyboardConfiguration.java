package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyboardConfiguration extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> keys;
	private Menu menu;
	private JTextField up, down, left, right, eagle;
	private JButton save, discard;
	private Settings settings;
	private GameKeyboard gameKeyboard;

	KeyboardConfiguration(Menu menu, Settings settings) {
		this.menu = menu;
		this.settings = settings;
		this.gameKeyboard = menu.getGameKeyboard();
		keys = new HashMap<String, Integer>();
		up = new JTextField();
		down = new JTextField();
		left = new JTextField();
		right = new JTextField();
		eagle = new JTextField();
		save = new JButton("Save changes");
		discard = new JButton("Discard changes");
		setKeyboardConfigurationPanel();
		addKeyListener(this);

		// Read the keyboard data
		readKeyboardData();
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveData();
			}
		});
		discard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeThis();
			}
		});
	}

	public void setKeyboardConfigurationPanel() {
		setSize(menu.getDimension());
		up.setHorizontalAlignment(JTextField.CENTER);
		down.setHorizontalAlignment(JTextField.CENTER);
		left.setHorizontalAlignment(JTextField.CENTER);
		right.setHorizontalAlignment(JTextField.CENTER);
		eagle.setHorizontalAlignment(JTextField.CENTER);

		setLayout(new GridBagLayout());
		GridBagConstraints style = new GridBagConstraints();

		// JTextfield
		style.fill = GridBagConstraints.BOTH;
		style.insets = new Insets(16, 128, 16, 128);
		style.gridwidth = 5;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridx = 3;
		style.gridy = 0;
		add(up, style);
		style.gridy = 1;
		add(down, style);
		style.gridy = 2;
		add(left, style);
		style.gridy = 3;
		add(right, style);
		style.gridy = 4;
		add(eagle, style);

		// Buttons
		style.fill = GridBagConstraints.NONE;
		style.insets = new Insets(0, 128, 0, 0);
		style.weightx = 1;
		style.gridx = 1;
		style.gridy = 5;
		add(save, style);
		style.insets = new Insets(0, 205, 0, 0);
		style.weightx = 1;
		style.gridx = 4;
		add(discard, style);
	}

	
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Object[] options = { "Yes", "No" };
			int confirm = JOptionPane
					.showOptionDialog(
							null,
							"Are you sure you want to stop configuring keyboard keys and discard all changes?",
							"Exit keyboard configuration",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[1]);

			if (confirm == 0)
				closeThis();
		}
	}

	public void saveData() {
		ArrayList<String> newKeys = new ArrayList<String>();
		boolean valid = true;

		newKeys.add(up.getText().toUpperCase());
		newKeys.add(down.getText().toUpperCase());
		newKeys.add(left.getText().toUpperCase());
		newKeys.add(right.getText().toUpperCase());
		newKeys.add(eagle.getText().toUpperCase());

		for (int i = 0; i < newKeys.size() - 1; i++) {
			for (int j = i + 1; j < newKeys.size(); j++) {
				if (newKeys.get(i) == newKeys.get(j))
					valid = false;

				if (!valid)
					break;
			}
		}

		if (!valid)
			JOptionPane.showMessageDialog(null,
					"There are multiple keys for the same command!");
		else if (gameKeyboard.writeCustomConfig(up, down, left, right, eagle)) {
			JOptionPane.showMessageDialog(null,
					"Game keyboard keys configuration successfully updated!");
			gameKeyboard.initializeKeys();
		} else
			JOptionPane.showMessageDialog(null,
					"Error while changing keyboard configuration!");
			gameKeyboard.initializeKeys();

		closeThis();
	}

	public void readKeyboardData() {
		keys = gameKeyboard.getKeys();

		up.setText(String.valueOf((char) Integer.parseInt(keys.get("up")
				.toString())));
		down.setText(String.valueOf((char) Integer.parseInt(keys.get("down")
				.toString())));
		left.setText(String.valueOf((char) Integer.parseInt(keys.get("left")
				.toString())));
		right.setText(String.valueOf((char) Integer.parseInt(keys.get("right")
				.toString())));
		eagle.setText(String.valueOf((char) Integer.parseInt(keys.get("eagle")
				.toString())));
	}

	void closeThis() {
		menu.closePanel(this, settings, "Settings");
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
