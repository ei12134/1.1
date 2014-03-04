public class Position {
	private int x, y;
	private String[] id;

	public Position() {
		this.x = 0;
		this.y = 0;
		this.id = Piece.emptyChar;
	}

	public Position(int x, int y, String[] id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String[] getId() {
		return id;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(String[] id) {
		this.id = id;
	}
}
