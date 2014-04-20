package gui;

import java.io.File;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class GameKeyboard {

	private HashMap<String, Integer> keys;
	private String filename = "gameKeys.lpoo";
	private KeyboardConfig keyConfig;

	
	public GameKeyboard() {
		keys = new HashMap<String, Integer>();
		keyConfig = new KeyboardConfig();
	}

	
	public void initializeKeys() {
		File f = new File(filename);
		if(!f.exists()) {
			try {
				f.createNewFile();
				if(!keyConfig.writeDefaultConfig())
					JOptionPane.showMessageDialog(null, "N‹o foi poss’vel escrever os comandos do jogo para um ficheiro!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			//Read the configuration file
			keys = keyConfig.getKeys();
		}
	}
	

	public HashMap<String, Integer> getKeys() {
		if (keys.size() > 0)
			return keys;

		return null;
	}
}
