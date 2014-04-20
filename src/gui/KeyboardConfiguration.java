package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KeyboardConfiguration extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> keys;
	private Menu menu;
	private JTextField up, down, left, right;
	private JButton submitData;
	private Settings settings;
	private KeyboardConfig keyConfig;
	

	KeyboardConfiguration(Menu menu, Settings settings) {
		this.menu = menu;
		this.settings = settings;
		keys = new HashMap<String, Integer>();
		up = new JTextField();
		down = new JTextField();
		left = new JTextField();
		right = new JTextField();
		submitData = new JButton("Guardar atalhos!");
		keyConfig = new KeyboardConfig();
		add(up);
		add(down);
		add(left);
		add(right);
		add(submitData);
		addKeyListener(this);
		
		//Read the keyboard data
		readKeyboardData();
		submitData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveData();
			}
		});
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

	
	public void saveData() {
		if(up.getText().toUpperCase() == "E" || down.getText().toUpperCase() == "E" || 
				left.getText().toUpperCase() == "E" || right.getText().toUpperCase() == "E")
			JOptionPane.showMessageDialog(null, "As teclas escolhidas s‹o inv‡idas!");
		else
			keyConfig.writeCustomConfig(up, down, left, right);
	}
	
	
	public void readKeyboardData() {
		keys = keyConfig.getKeys();
		
		up.setText(String.valueOf((char)Integer.parseInt(keys.get("up").toString())));
		down.setText(String.valueOf((char)Integer.parseInt(keys.get("down").toString())));
		left.setText(String.valueOf((char)Integer.parseInt(keys.get("left").toString())));
		right.setText(String.valueOf((char)Integer.parseInt(keys.get("right").toString())));
	}
	
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
