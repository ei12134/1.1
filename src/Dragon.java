
public class Dragon {

	Position position;
	String[] status;
	String[] piece;

	Dragon(Position position, String[] status, String[] piece) {
		this.position = position;
		this.status = status;
		this.piece = piece;
	}
	
	public Position GetPosition() {
		return position;
	}
	
	public String[] GetStatus() {
		return status;
	}
	
	public String[] GetPiece() {
		return piece;
	}
	
	public void SetPosition(Position position) {
		this.position = position;
	}
	
	public void SetStatus(String[] status) {
		this.status = status;
	}
	
	public void SetPiece(String[] piece) {
		this.piece=piece;
	}
}
