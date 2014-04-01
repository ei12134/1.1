package maze;

public enum PieceType {
	
	FREE("   "), WALL(" X "), EXIT(" S "), SWORD(" E ");

	private final String asString;

	public String asString() {
		return asString;
	}

	private PieceType(String asString) {
		this.asString = asString;
	}
}
