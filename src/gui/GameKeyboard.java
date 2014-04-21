package gui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class GameKeyboard {

	private HashMap<String, Integer> keys;
	private String filename = "gameKeys.keys";

	public GameKeyboard() {
		keys = new HashMap<String, Integer>();
	}

	public void initializeKeys() {
		File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
				if (!writeDefaultConfig())
					JOptionPane.showMessageDialog(null,
							"Error saving game keys to disk file!");
				else
					keys = getSavedKeys();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Read the configuration file
			keys = getSavedKeys();
		}
	}

	public HashMap<String, Integer> getKeys() {
		if (keys.size() > 0)
			return keys;

		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getSavedKeys() {
		HashMap<String, Integer> keys = new HashMap<String, Integer>();
		FileInputStream inStream = null;
		ObjectInputStream objInStream = null;
		try {
			ArrayList<CommandKey> key = new ArrayList<CommandKey>();
			inStream = new FileInputStream(filename);
			objInStream = new ObjectInputStream(inStream);

			Object o = objInStream.readObject();
			if (o instanceof ArrayList) {
				key = (ArrayList<CommandKey>) o;
				CommandKey tmp;
				for (int i = 0; i < key.size(); i++) {
					tmp = key.get(i);
					keys.put(tmp.getCommand(), tmp.getCommandChar());
				}
			}
			objInStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			writeDefaultConfig();
			initializeKeys();
		}

		return keys;
	}

	public boolean writeDefaultConfig() {
		ArrayList<CommandKey> listKeys = new ArrayList<CommandKey>();
		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;
		try {
			outStream = new FileOutputStream(filename);
			objStream = new ObjectOutputStream(outStream);
			// Default ones don't overwrite
			listKeys.add(new CommandKey("up_arrow", KeyEvent.VK_UP));
			listKeys.add(new CommandKey("down_arrow", KeyEvent.VK_DOWN));
			listKeys.add(new CommandKey("left_arrow", KeyEvent.VK_LEFT));
			listKeys.add(new CommandKey("right_arrow", KeyEvent.VK_RIGHT));
			listKeys.add(new CommandKey("eagle_spacebar", KeyEvent.VK_SPACE));

			// Allow to customize
			listKeys.add(new CommandKey("up", KeyEvent.VK_W));
			listKeys.add(new CommandKey("down", KeyEvent.VK_S));
			listKeys.add(new CommandKey("left", KeyEvent.VK_A));
			listKeys.add(new CommandKey("right", KeyEvent.VK_D));
			listKeys.add(new CommandKey("eagle", KeyEvent.VK_E));
			objStream.writeObject(listKeys);
			objStream.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean writeCustomConfig(JTextField up, JTextField down,
			JTextField left, JTextField right, JTextField eagle) {
		ArrayList<CommandKey> listKeys = new ArrayList<CommandKey>();
		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;
		try {
			outStream = new FileOutputStream(filename);
			objStream = new ObjectOutputStream(outStream);
			listKeys.add(new CommandKey("up_arrow", KeyEvent.VK_UP));
			listKeys.add(new CommandKey("down_arrow", KeyEvent.VK_DOWN));
			listKeys.add(new CommandKey("left_arrow", KeyEvent.VK_LEFT));
			listKeys.add(new CommandKey("right_arrow", KeyEvent.VK_RIGHT));
			listKeys.add(new CommandKey("eagle_spacebar", KeyEvent.VK_SPACE));

			listKeys.add(new CommandKey("up", KeyStroke.getKeyStroke(
					up.getText().substring(0, 1).toUpperCase()).getKeyCode()));
			listKeys.add(new CommandKey("down", KeyStroke.getKeyStroke(
					down.getText().substring(0, 1).toUpperCase()).getKeyCode()));
			listKeys.add(new CommandKey("left", KeyStroke.getKeyStroke(
					left.getText().substring(0, 1).toUpperCase()).getKeyCode()));
			listKeys.add(new CommandKey("right", KeyStroke.getKeyStroke(
					right.getText().substring(0, 1).toUpperCase()).getKeyCode()));
			listKeys.add(new CommandKey("eagle", KeyStroke.getKeyStroke(
					eagle.getText().substring(0, 1).toUpperCase()).getKeyCode()));
			objStream.writeObject(listKeys);
			objStream.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
