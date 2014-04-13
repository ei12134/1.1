package gui;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KeyboardKey implements Serializable {

	private String command;
	private int commandInt;

	public KeyboardKey(String command, int commandInt) {
		this.command = command;
		this.commandInt = commandInt;
	}

	public String getCommand() {
		return command;
	}

	public int getCommandChar() {
		return commandInt;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setCommandInt(int commandInt) {
		this.commandInt = commandInt;
	}

	@Override
	public String toString() {
		return command + ":" + commandInt;
	}
}
