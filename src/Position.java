public class Position {
	private int x, y;
	private String[] id;

	public Position(int x, int y, String[] id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	// get methods
	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public String[] GetId() {
		return id;
	}

	// set methods
	public void SetX(int x) {
		this.x = x;
	}

	public void SetY(int y) {
		this.y = y;
	}

	public void SetId(String[] id) {
		this.id = id;
	}
}
