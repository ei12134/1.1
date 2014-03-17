package maze;

public class Dragao extends Personagem {

	private boolean naEspada;
	private boolean aDormir;

	public Dragao(int posX, int posY) {
		super(posX, posY);
	}

	public char mostrarDragao(Heroi heroi) {
		if (!estaMorto()) {
			if (estaNaEspada() && !heroi.getHeroiTemEspada())
				return 'F';
			else if (estaADormir())
				return 'd';
			else
				return 'D';
		} else {
			return ' ';
		}
	}

	public boolean estaNaEspada() {
		return naEspada;
	}

	public void setNaEspada(boolean espada) {
		naEspada = espada;
	}

	public boolean estaADormir() {
		return aDormir;
	}

	public void setaDormir(boolean aDormir) {
		this.aDormir = aDormir;
	}
}
