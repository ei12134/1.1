package maze;

public enum Movimentos {
	MOVIMENTO_CIMA(0), MOVIMENTO_BAIXO(1), MOVIMENTO_DIREITA(2), MOVIMENTO_ESQUERDA(
			3);

	private final int direcao;

	private Movimentos(int direcao) {
		this.direcao = direcao;
	}

	public int getDirecaoInt() {
		return direcao;
	}
}
