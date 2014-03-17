package maze;

public class Heroi extends Personagem {

	private boolean heroiTemEspada;

	public Heroi(int posX, int posY) {
		super(posX, posY);
		this.heroiTemEspada = false;
	}

	public void setHeroiTemEspada(boolean estadoEspada) {
		heroiTemEspada = estadoEspada;
	}

	public boolean getHeroiTemEspada() {
		return heroiTemEspada;
	}

	public char mostrarHeroi() {
		if (heroiTemEspada)
			return 'A';

		return 'H';
	}
}
