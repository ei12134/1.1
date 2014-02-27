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
	
	public String[] GetStatus() {
		return status;
	}
	
	public void SetPosition(Position position) {
		this.position = position;
	}
	
	public void SetStatus(String[] status) {
		this.status = status;
	}
}
