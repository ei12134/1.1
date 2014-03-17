package maze;

public class Personagem {

	// Todas as personagens tem em comum uma posicao X, Y
	private int posX;
	private int posY;
	private boolean estaMorto;

	public Personagem(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		estaMorto = false;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean estaMorto() {
		return estaMorto;
	}

	public void setEstadoMorto(boolean morto) {
		estaMorto = morto;
	}

	public void atualizaPosicao(int x, int y) {
		posX = x;
		posY = y;
	}
}
