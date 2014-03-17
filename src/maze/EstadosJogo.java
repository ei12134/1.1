package maze;

public enum EstadosJogo {

	JOGADOR_VENCEU("jogador_venceu"), JOGADOR_MORREU("jogador_morreu"), CONTINUAR_JOGO(
			"continuar_jogo");
	private String estado;

	private EstadosJogo(String estado) {
		this.estado = estado;
	}

	public String toString() {
		return estado;
	}
}
