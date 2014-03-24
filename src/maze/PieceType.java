package maze;

/**
 * Fonte: http://stackoverflow.com/questions/14202959/in-java-is-there-a-way-to-
 * define-enum-of-type-String
 */
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
