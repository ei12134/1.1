public class Hero {

	Position position;
	String[] status;

	Hero(Position position, String[] status) {
		this.position = position;
		this.status = status;
	}
	
	
	public Position GetPosition() {
		return position;
	}
	
	public void SetPosition(Position position) {
		this.position = position;
	}
}
