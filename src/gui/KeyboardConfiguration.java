package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class KeyboardConfiguration extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> gameKeys;
	private Menu menu;
	private Settings settings;

	KeyboardConfiguration(Menu menu, Settings settings) {

		this.menu = menu;
		this.settings = settings;
		gameKeys = menu.getKeyboardKeys();
		addKeyListener(this);
		// keyboardKeys = new GameKeyboard();
		// keyboardKeys.initializeKeys();
		// keyboardKeys.readKeys();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Object[] options = { "Yes", "No" };
			int confirm = JOptionPane
					.showOptionDialog(
							null,
							"Are you sure you want to stop configuring keyboard keys and discard all changes?",
							"Exit keyboard configuration", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[1]);

			if (confirm == 0)
				menu.closePanel(this, settings);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
