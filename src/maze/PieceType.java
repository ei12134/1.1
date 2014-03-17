package maze;

/**
 * Fonte: http://stackoverflow.com/questions/14202959/in-java-is-there-a-way-to-
 * define-enum-of-type-char
 */
public enum PieceType {
	FREE(' '), WALL('X'), EXIT('S'), SWORD('E');

	private final char asChar;

	public char asChar() {
		return asChar;
	}

	private PieceType(char asChar) {
		this.asChar = asChar;
	}
}
