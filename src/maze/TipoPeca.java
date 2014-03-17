package maze;

/**
 * Fonte: http://stackoverflow.com/questions/14202959/in-java-is-there-a-way-to-
 * define-enum-of-type-char
 */
public enum TipoPeca {
	LIVRE(' '), PAREDE('X'), SAIDA('S'), ESPADA('E');

	private final char comoChar;

	public char comoChar() {
		return comoChar;
	}

	private TipoPeca(char comoChar) {
		this.comoChar = comoChar;
	}
}
