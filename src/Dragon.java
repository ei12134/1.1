
public class Dragon {

	Position position;
	String[] status;

	Dragon(Position position, String[] status) {
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
