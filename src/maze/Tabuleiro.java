package maze;

import algorithms.Algoritmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Tabuleiro {

	/**
	 * Um tabuleiro e caracterizado por um conjunto de pecas(em que cada uma
	 * representa um objeto da classe Peca)
	 */
	ArrayList<ArrayList<Peca>> tabuleiro;

	// Esta variavel representa a peca de saida do tabuleiro
	private Peca saida;
	// Esta variavel representa a peca onde a espada esta localizada no
	// tabuleiro
	private Peca espada;

	public Tabuleiro(Heroi heroi, Dragao dragao, int defeito) {
		tabuleiro = new ArrayList<ArrayList<Peca>>();
		if (defeito == 1)
			iniciarTabuleiroDefeito(heroi, dragao);
		else
			iniciarTabuleiroAleatorio(heroi, dragao);
	}

	private void iniciarTabuleiroDefeito(Heroi heroi, Dragao dragao) {
		saida = new Peca(9, 5, TipoPeca.SAIDA.comoChar());
		espada = new Peca(1, 8, TipoPeca.ESPADA.comoChar());

		for (int i = 0; i < 10; i++) {
			ArrayList<Peca> linha = new ArrayList<Peca>();
			for (int j = 0; j < 10; j++) {
				linha.add(new Peca(i, j, TipoPeca.PAREDE.comoChar()));
			}

			tabuleiro.add(linha);
		}

		for (int i = 2; i < 9; i++)
			tabuleiro.get(1).get(i).setSimbolo(TipoPeca.LIVRE.comoChar());
		for (int i = 2; i < 9; i++)
			tabuleiro.get(i).get(1).setSimbolo(TipoPeca.LIVRE.comoChar());

		for (int i = 4; i < 9; i += 2) {
			for (int j = 1; j < 9; j++)
				tabuleiro.get(j).get(i).setSimbolo(TipoPeca.LIVRE.comoChar());
		}

		for (int i = 1; i < 9; i++)
			if (i != 7)
				tabuleiro.get(5).get(i).setSimbolo(TipoPeca.LIVRE.comoChar());

		for (int i = 1; i < 9; i++)
			if (i != 2 && i != 3)
				tabuleiro.get(8).get(i).setSimbolo(TipoPeca.LIVRE.comoChar());

		tabuleiro.get(heroi.getPosY()).get(heroi.getPosX())
				.setSimbolo(heroi.mostrarHeroi());
		tabuleiro.get(saida.getPosY()).get(saida.getPosX())
				.setSimbolo(TipoPeca.SAIDA.comoChar());
		tabuleiro.get(espada.getPosY()).get(espada.getPosX())
				.setSimbolo(TipoPeca.ESPADA.comoChar());
		tabuleiro.get(dragao.getPosY()).get(dragao.getPosX())
				.setSimbolo(dragao.mostrarDragao(heroi));
	}

	private void iniciarTabuleiroAleatorio(Heroi heroi, Dragao dragao) {
		int resposta;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Seleccionar um tamanho(impar) para o puzzle");
		resposta = scanner.nextInt();
		while (resposta % 2 == 0 || resposta > 40) {
			System.out.println("Opcao invalida! Escolher novamente...");
			resposta = scanner.nextInt();
		}
		Algoritmo algoritmo = new Algoritmo(resposta);
		tabuleiro = algoritmo.gerarPuzzle();

		/**
		 * Aqui ja temos o tabuleio gerado, agora temos que encontrar as
		 * posicoes corretas para posicionar os varios elementos do jogo
		 */
		int posX, posY;
		// Gerar um numero aleatorio entre [1, tamanho do tabuleiro - 1]
		posX = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		while (tabuleiro.get(posY).get(posX).getSimbolo() == TipoPeca.PAREDE
				.comoChar()
				|| tabuleiro.get(posY).get(posX).getSimbolo() == TipoPeca.SAIDA
						.comoChar()) {
			posX = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		}
		// Gerar posicao do heroi
		heroi.atualizaPosicao(posX, posY);
		tabuleiro.get(heroi.getPosY()).get(heroi.getPosX())
				.setSimbolo(heroi.mostrarHeroi());

		posX = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		while (tabuleiro.get(posY).get(posX).getSimbolo() == TipoPeca.PAREDE
				.comoChar()
				|| tabuleiro.get(posY).get(posX).getSimbolo() == TipoPeca.SAIDA
						.comoChar()
				|| tabuleiro.get(posY).get(posX).getSimbolo() == heroi
						.mostrarHeroi()) {
			posX = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		}
		// Gerar posicao do dragao
		dragao.atualizaPosicao(posX, posY);
		tabuleiro.get(dragao.getPosY()).get(dragao.getPosX())
				.setSimbolo(dragao.mostrarDragao(heroi));

		posX = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		while (tabuleiro.get(posY).get(posX).getSimbolo() == TipoPeca.PAREDE
				.comoChar()
				|| tabuleiro.get(posY).get(posX).getSimbolo() == TipoPeca.SAIDA
						.comoChar()
				|| tabuleiro.get(posY).get(posX).getSimbolo() == heroi
						.mostrarHeroi()
				|| tabuleiro.get(posY).get(posX).getSimbolo() == dragao
						.mostrarDragao(heroi)) {
			posX = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((tabuleiro.size() - 1 - 1) + 1));
		}
		// Gerar posicao da espada
		espada = new Peca(posX, posY, TipoPeca.ESPADA.comoChar());
		tabuleiro.get(espada.getPosY()).get(espada.getPosX())
				.setSimbolo(TipoPeca.ESPADA.comoChar());

		// Marcar a saida
		for (int i = 0; i < tabuleiro.size(); i++)
			for (int j = 0; j < tabuleiro.get(i).size(); j++)
				if (tabuleiro.get(j).get(i).getSimbolo() == TipoPeca.SAIDA
						.comoChar()) {
					saida = new Peca(i, j, TipoPeca.SAIDA.comoChar());
					break;
				}
	}

	public void mostrarTabuleiro() {
		for (int i = 0; i < tabuleiro.size(); i++) {
			ArrayList<Peca> linhaTabuleiro = tabuleiro.get(i);
			for (int j = 0; j < tabuleiro.get(i).size(); j++)
				System.out.print(linhaTabuleiro.get(j).getSimbolo() + " ");

			System.out.println();
		}
	}

	/**
	 * Esta funcao verifica as 4 posicoes em redor do heroi. Para cada uma delas
	 * e testado se essa posicao esta livre. Caso esteja entao e adicionada a um
	 * HashMap
	 * 
	 * @param heroi
	 *            Um objeto da classe Heroi utilizado para verificar todas as
	 *            posicoes que o jogador pode tomar
	 * @return Um HashMap constituido pelas posicoes que o heroi pode tomar no
	 *         proximo passo
	 */
	public HashMap<Integer, Boolean> getMovimentosPossiveis(Heroi heroi) {
		HashMap<Integer, Boolean> movimentosPossiveis = new HashMap<Integer, Boolean>();
		boolean heroiArmado = heroi.getHeroiTemEspada();

		// Testar se o heroi se pode mover para cima
		if (heroi.getPosY() - 1 >= 0)
			if (tabuleiro.get(heroi.getPosY() - 1).get(heroi.getPosX())
					.getSimbolo() != TipoPeca.PAREDE.comoChar()) {
				// Esta condicao assegura que o movimento para a peca saida so
				// sera possivel se o heroi estiver armado
				if (!((tabuleiro.get(heroi.getPosY() - 1).get(heroi.getPosX())
						.getSimbolo() == TipoPeca.SAIDA.comoChar()) && !heroiArmado))
					movimentosPossiveis.put(0, true);
			}

		// Testar se o heroi se pode mover para baixo
		if (heroi.getPosY() + 1 < tabuleiro.size())
			if (tabuleiro.get(heroi.getPosY() + 1).get(heroi.getPosX())
					.getSimbolo() != TipoPeca.PAREDE.comoChar()) {
				if (!((tabuleiro.get(heroi.getPosY() + 1).get(heroi.getPosX())
						.getSimbolo() == TipoPeca.SAIDA.comoChar()) && !heroiArmado))
					movimentosPossiveis.put(1, true);
			}

		// Testar se o heroi se pode mover para a direita
		if (heroi.getPosX() + 1 < tabuleiro.size())
			if (tabuleiro.get(heroi.getPosY()).get(heroi.getPosX() + 1)
					.getSimbolo() != TipoPeca.PAREDE.comoChar()) {
				if (!((tabuleiro.get(heroi.getPosY()).get(heroi.getPosX() + 1)
						.getSimbolo() == TipoPeca.SAIDA.comoChar()) && !heroiArmado))
					movimentosPossiveis.put(2, true);
			}

		// Testar se o heroi se pode mover para a esquerda
		if (heroi.getPosX() - 1 >= 0)
			if (tabuleiro.get(heroi.getPosY()).get(heroi.getPosX() - 1)
					.getSimbolo() != TipoPeca.PAREDE.comoChar()) {
				if (!((tabuleiro.get(heroi.getPosY()).get(heroi.getPosX() - 1)
						.getSimbolo() == TipoPeca.SAIDA.comoChar()) && !heroiArmado))
					movimentosPossiveis.put(3, true);
			}

		return movimentosPossiveis;
	}

	/**
	 * Esta funcao e utilizada para trocar duas pecas. Alem de trocar duas pecas
	 * ainda verifica se o heroi esta armado ou nao atraves de uma funcao
	 * auxiliar Quando marcamos o simbolo no tabuleiro o programa verifica se o
	 * heroi tem espada ou nao Se tiver entao mostra um 'A', caso contrario
	 * mostra um 'H'
	 */
	public void trocarPecas(int direcao, Heroi heroi) {
		if (direcao == Movimentos.MOVIMENTO_CIMA.getDirecaoInt()) {
			getPecaTabuleiro(heroi.getPosX(), heroi.getPosY()).setSimbolo(
					TipoPeca.LIVRE.comoChar());

			if (!heroi.getHeroiTemEspada()
					&& pecaHeroiArmadoProxima(
							Movimentos.MOVIMENTO_CIMA.getDirecaoInt(), heroi))
				heroi.setHeroiTemEspada(true);
			getPecaTabuleiro(heroi.getPosX(), heroi.getPosY() - 1).setSimbolo(
					heroi.mostrarHeroi());

			heroi.atualizaPosicao(heroi.getPosX(), heroi.getPosY() - 1);
		} else if (direcao == Movimentos.MOVIMENTO_BAIXO.getDirecaoInt()) {
			getPecaTabuleiro(heroi.getPosX(), heroi.getPosY()).setSimbolo(
					TipoPeca.LIVRE.comoChar());

			if (!heroi.getHeroiTemEspada()
					&& pecaHeroiArmadoProxima(
							Movimentos.MOVIMENTO_BAIXO.getDirecaoInt(), heroi))
				heroi.setHeroiTemEspada(true);
			getPecaTabuleiro(heroi.getPosX(), heroi.getPosY() + 1).setSimbolo(
					heroi.mostrarHeroi());

			heroi.atualizaPosicao(heroi.getPosX(), heroi.getPosY() + 1);
		} else if (direcao == Movimentos.MOVIMENTO_DIREITA.getDirecaoInt()) {
			getPecaTabuleiro(heroi.getPosX(), heroi.getPosY()).setSimbolo(
					TipoPeca.LIVRE.comoChar());

			if (!heroi.getHeroiTemEspada()
					&& pecaHeroiArmadoProxima(
							Movimentos.MOVIMENTO_DIREITA.getDirecaoInt(), heroi))
				heroi.setHeroiTemEspada(true);
			getPecaTabuleiro(heroi.getPosX() + 1, heroi.getPosY()).setSimbolo(
					heroi.mostrarHeroi());

			heroi.atualizaPosicao(heroi.getPosX() + 1, heroi.getPosY());
		} else if (direcao == Movimentos.MOVIMENTO_ESQUERDA.getDirecaoInt()) {
			getPecaTabuleiro(heroi.getPosX(), heroi.getPosY()).setSimbolo(
					TipoPeca.LIVRE.comoChar());

			if (!heroi.getHeroiTemEspada()
					&& pecaHeroiArmadoProxima(
							Movimentos.MOVIMENTO_ESQUERDA.getDirecaoInt(),
							heroi))
				heroi.setHeroiTemEspada(true);
			getPecaTabuleiro(heroi.getPosX() - 1, heroi.getPosY()).setSimbolo(
					heroi.mostrarHeroi());

			heroi.atualizaPosicao(heroi.getPosX() - 1, heroi.getPosY());
		}
	}

	public boolean pecaHeroiArmadoProxima(int pos, Heroi heroi) {
		int posX = heroi.getPosX();
		int posY = heroi.getPosY();
		if (pos == Movimentos.MOVIMENTO_CIMA.getDirecaoInt()) {
			if ((posX == espada.getPosX()) && (posY - 1 == espada.getPosY()))
				return true;
		} else if (pos == Movimentos.MOVIMENTO_BAIXO.getDirecaoInt()) {
			if ((posX == espada.getPosX()) && (posY + 1 == espada.getPosY()))
				return true;
		} else if (pos == Movimentos.MOVIMENTO_DIREITA.getDirecaoInt()) {
			if ((posX + 1 == espada.getPosX()) && (posY == espada.getPosY()))
				return true;
		} else if ((pos == Movimentos.MOVIMENTO_ESQUERDA.getDirecaoInt())) {
			if ((posX - 1 == espada.getPosX()) && (posY == espada.getPosY()))
				return true;
		}

		return false;
	}

	public int getSaidaX() {
		return saida.getPosX();
	}

	public int getSaidaY() {
		return saida.getPosY();
	}

	public char getPecaTabuleiroSimbolo(int x, int y) {
		return tabuleiro.get(y).get(x).getSimbolo();
	}

	public Peca getPecaTabuleiro(int x, int y) {
		return tabuleiro.get(y).get(x);
	}

	public Peca getEspada() {
		return espada;
	}
}
