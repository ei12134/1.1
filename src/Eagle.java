
public class Eagle {
	Position position;
	String status;
	String piece;

	Eagle(Position position, String status, String piece) {
		this.position = position;
		this.status = status;
		this.piece = piece;
	}

	public Position getPosition() {
		return position;
	}

	public String getStatus() {
		return status;
	}

	public String getPiece() {
		return piece;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}
}
