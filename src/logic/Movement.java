package logic;

public enum Movement {
	MOVE_UP(0), MOVE_DOWN(1), MOVE_RIGHT(2), MOVE_LEFT(3);

	private final int move;

	private Movement(int move) {
		this.move = move;
	}

	public int getDirection() {
		return move;
	}
}
