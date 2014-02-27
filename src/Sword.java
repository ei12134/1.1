
public class Sword {
	Position position;
	//String[] status;

	Sword(Position position) {
		this.position = position;
		//this.status = status;
	}
	
	
	public Position GetPosition() {
		return position;
	}
	
	public void SetPosition(Position position) {
		this.position = position;
	}
}
