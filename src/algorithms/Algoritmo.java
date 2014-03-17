package algorithms;

import maze.Movimentos;
import maze.Peca;
import maze.TipoPeca;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Algoritmo {

	ArrayList<ArrayList<Peca>> tabuleiro;
	boolean pecasVisitadas[][];
	Stack<Peca> pecasStack;
	Peca pecaAtual;
	Random r;
	int mazeTamanho;

	public Algoritmo(int mazeTamanho) {
		this.mazeTamanho = mazeTamanho;
		pecasStack = new Stack<Peca>();
		pecasVisitadas = new boolean[mazeTamanho][mazeTamanho];
		r = new Random();

		tabuleiro = gerarTabuleiroInicial();
		gerarSaida(tabuleiro);
	}

	public ArrayList<ArrayList<Peca>> gerarTabuleiroInicial() {
		ArrayList<ArrayList<Peca>> tabuleiroTemp = new ArrayList<ArrayList<Peca>>();
		for (int i = 0; i < mazeTamanho; i++) {
			ArrayList<Peca> linha = new ArrayList<Peca>();
			for (int j = 0; j < mazeTamanho; j++) {
				linha.add(new Peca(j, i,
						(j % 2 != 0 && i % 2 != 0 ? TipoPeca.LIVRE.comoChar()
								: TipoPeca.PAREDE.comoChar())));
			}
			tabuleiroTemp.add(linha);
		}

		// Iniciar todas as pecas visitadas a false
		for (int i = 0; i < mazeTamanho; i++) {
			for (int j = 0; j < mazeTamanho; j++) {
				pecasVisitadas[i][j] = false;
			}
		}

		return tabuleiroTemp;
	}

	public void gerarSaida(ArrayList<ArrayList<Peca>> tabuleiro) {
		int linhaSaida;
		int colunaSaida;

		// Inicialmente vamos recolher todas as linhas que sao impares para um
		// ArrayList
		ArrayList<Integer> linhasPossiveis = new ArrayList<Integer>();
		for (int i = 0; i < mazeTamanho; i++)
			if (i % 2 != 0)
				linhasPossiveis.add(i);

		// Para seleccionar a linha da saida escolhemos uma dessas linhas
		// aleatoriamente
		linhaSaida = linhasPossiveis.get(r.nextInt(linhasPossiveis.size()));
		if (linhaSaida > mazeTamanho / 2)
			colunaSaida = mazeTamanho - 1;
		else
			colunaSaida = 0;

		// Marcar a peca de saida no tabuleiro
		Peca pecaSaida = tabuleiro.get(linhaSaida).get(colunaSaida);
		pecaSaida.setSimbolo(TipoPeca.SAIDA.comoChar());

		// A peca atual sera a peca que esta imediatamente ao lado da saida
		pecaAtual = tabuleiro.get(pecaSaida.getPosY()).get(
				pecaSaida.getPosX() == 0 ? pecaSaida.getPosX() + 1 : pecaSaida
						.getPosX() - 1);

		// Marcar a peca atual como visitada e adicionar ao stack
		pecasVisitadas[pecaAtual.getPosY()][pecaAtual.getPosX()] = true;
		pecasStack.push(pecaAtual);
	}

	public ArrayList<ArrayList<Peca>> gerarPuzzle() {
		while (!pecasStack.empty()) {
			// Seleccionar a lista das pecas vizinhas que ainda nao foram
			// visitadas
			ArrayList<Peca> pecasVizinhas = getPecasVizinhas();

			// Se existir alguma peca vizinha que ainda nao foi visitada
			if (pecasVizinhas.size() > 0) {
				// Escolher uma dessas pecas aleatoriamente
				int pecaPos = r.nextInt(pecasVizinhas.size());

				Peca pecaTemp = pecasVizinhas.get(pecaPos);
				// Mover para a proxima casa
				moverPecaAtual(pecaTemp, selecionarDirecao(pecaTemp));
			} else {
				pecaAtual = pecasStack.pop();
			}
		}

		return tabuleiro;
	}

	public int selecionarDirecao(Peca peca) {
		if (peca.getPosY() > pecaAtual.getPosY()
				&& peca.getPosX() == pecaAtual.getPosX()) {
			return Movimentos.MOVIMENTO_BAIXO.getDirecaoInt();
		} else if (peca.getPosY() < pecaAtual.getPosY()
				&& peca.getPosX() == pecaAtual.getPosX()) {
			return Movimentos.MOVIMENTO_CIMA.getDirecaoInt();
		} else if (peca.getPosY() == pecaAtual.getPosY()
				&& peca.getPosX() > pecaAtual.getPosX()) {
			return Movimentos.MOVIMENTO_DIREITA.getDirecaoInt();
		} else {
			return Movimentos.MOVIMENTO_ESQUERDA.getDirecaoInt();
		}
	}

	public void moverPecaAtual(Peca peca, int direcao) {
		if (direcao == Movimentos.MOVIMENTO_CIMA.getDirecaoInt()) {
			tabuleiro.get(peca.getPosY() + 1).get(peca.getPosX())
					.setSimbolo(TipoPeca.LIVRE.comoChar());
		} else if (direcao == Movimentos.MOVIMENTO_BAIXO.getDirecaoInt()) {
			tabuleiro.get(peca.getPosY() - 1).get(peca.getPosX())
					.setSimbolo(TipoPeca.LIVRE.comoChar());
		} else if (direcao == Movimentos.MOVIMENTO_DIREITA.getDirecaoInt()) {
			tabuleiro.get(peca.getPosY()).get(peca.getPosX() - 1)
					.setSimbolo(TipoPeca.LIVRE.comoChar());
		} else {
			tabuleiro.get(peca.getPosY()).get(peca.getPosX() + 1)
					.setSimbolo(TipoPeca.LIVRE.comoChar());
		}

		// Marcar como peca livre
		tabuleiro.get(peca.getPosY()).get(peca.getPosX())
				.setSimbolo(TipoPeca.LIVRE.comoChar());
		// Atualizar a peca atual
		pecaAtual = peca;

		// Adicionar a nova peca ao stack
		pecasStack.push(pecaAtual);
		// Marcar a peca como visitada
		pecasVisitadas[pecaAtual.getPosY()][pecaAtual.getPosX()] = true;
	}

	public ArrayList<Peca> getPecasVizinhas() {
		ArrayList<Peca> vizinhas = new ArrayList<Peca>();

		// Verificar se podemos adicionar a peca que esta em cima
		if (pecaAtual.getPosY() - 2 > 0 && pecaAtual.getPosX() != 0
				&& pecaAtual.getPosX() != mazeTamanho - 1) {
			if (!pecasVisitadas[pecaAtual.getPosY() - 2][pecaAtual.getPosX()]) {
				vizinhas.add(tabuleiro.get(pecaAtual.getPosY() - 2).get(
						pecaAtual.getPosX()));
			}
		}

		// Verificar se podemos adicionar a peca que esta em baixo
		if (pecaAtual.getPosY() + 2 < mazeTamanho - 1
				&& pecaAtual.getPosX() != 0
				&& pecaAtual.getPosX() != mazeTamanho - 1) {
			if (!pecasVisitadas[pecaAtual.getPosY() + 2][pecaAtual.getPosX()]) {
				vizinhas.add(tabuleiro.get(pecaAtual.getPosY() + 2).get(
						pecaAtual.getPosX()));
			}
		}

		// Verificar se podemos adicionar a peca que esta a direita
		if (pecaAtual.getPosX() + 2 < mazeTamanho - 1) {
			if (!pecasVisitadas[pecaAtual.getPosY()][pecaAtual.getPosX() + 2]) {
				vizinhas.add(tabuleiro.get(pecaAtual.getPosY()).get(
						pecaAtual.getPosX() + 2));
			}
		}

		// Verificar se podemos adicionar a peca que esta a esquerda
		if (pecaAtual.getPosX() - 2 > 0) {
			if (!pecasVisitadas[pecaAtual.getPosY()][pecaAtual.getPosX() - 2]) {
				vizinhas.add(tabuleiro.get(pecaAtual.getPosY()).get(
						pecaAtual.getPosX() - 2));
			}
		}

		return vizinhas;
	}
}
