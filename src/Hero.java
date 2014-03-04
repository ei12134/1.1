public class Hero {

	Position position;
	String[] status;

	Hero(Position position, String[] status) {
		this.position = position;
		this.status = status;
	}

	public Position getPosition() {
		return position;
	}

	public String[] getStatus() {
		return status;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}
}
