package maze;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Jogo {

	/**
	 * Cada jogo inicialmente tera um tabuleiro, um heroi e um dragao como
	 * principais elementos
	 */
	private Tabuleiro tabuleiro;
	private Heroi heroi;
	private Dragao dragao;
	private boolean jogoAcabado;
	Random random = new Random();

	public Jogo() {
		jogoAcabado = false;
		perguntarTamanhoTabuleiro();
	}

	public void perguntarTamanhoTabuleiro() {
		int resposta;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Puzzle normal/aleatorio? (0/1)");
		resposta = scanner.nextInt();
		while (resposta != 0 && resposta != 1) {
			System.out.println("Resposta invalida! Escolher novamente...");
			resposta = scanner.nextInt();
		}

		if (resposta == 0) {
			heroi = new Heroi(1, 1);
			dragao = new Dragao(1, 6);
			tabuleiro = new Tabuleiro(heroi, dragao, 1);
		} else {
			// TODO: Melhorar esta funcao mais tarde. So funciona porque (0,0) e
			// sempre parede
			heroi = new Heroi(0, 0);
			dragao = new Dragao(0, 0);
			tabuleiro = new Tabuleiro(heroi, dragao, 0);
		}

		iniciarJogo();
	}

	public void iniciarJogo() {
		// Variavel que armazena a escolha do utilizador para movimentar o heroi
		char jogadorOpcao;
		// Variavel que define o estado do dragao
		int estadoDragao;

		// Utilizado para receber input do jogador
		Scanner scanner = new Scanner(System.in);
		Random r = new Random();
		/**
		 * Mostrar uma mensagem ao jogador no qual ele(a) e informado sobre o
		 * objetivo do jogo
		 */
		System.out.println("Encontrar a saida(S) para vencer o dragao!");
		tabuleiro.mostrarTabuleiro();
		while (!jogoAcabado) {
			estadoDragao = r.nextInt(2);
			System.out.print("Escolher nova posicao do heroi: ");
			jogadorOpcao = scanner.next().charAt(0);

			HashMap<Integer, Boolean> movimentosPossiveis = tabuleiro
					.getMovimentosPossiveis(heroi);

			if (estadoDragao == 0)
				dragao.setaDormir(false);
			else {
				dragao.setaDormir(true);
				System.out.println("Dragao a dormir!!!!!!!!!!");
			}
			// Movimentar o heroi no tabuleiro
			movimentarHeroi(jogadorOpcao, movimentosPossiveis);
			// Movimentar o dragao no tabuleiro
			if (!dragao.estaMorto()) {
				if (dragao.estaADormir())
					tabuleiro.getPecaTabuleiro(dragao.getPosX(),
							dragao.getPosY()).setSimbolo(
							dragao.mostrarDragao(heroi));
				else
					movimentarDragao();
			}
			String estado = veriricarJogo();
			// E finalmente mostrar o tabuleiro
			tabuleiro.mostrarTabuleiro();

			if (estado.equals(EstadosJogo.JOGADOR_VENCEU.toString())) {
				jogoAcabado = true;

				// Escrever uma mensagem para informar que o jogador venceu o
				// jogo
				System.out.println("Venceste o jogo!!");
			} else if (estado.equals(EstadosJogo.JOGADOR_MORREU.toString())) {
				jogoAcabado = true;

				// Escrever uma mensagem para informar que o jogador morreu
				System.out.println("Foste morto pelo dragao!!!");
			}
		}
	}

	/**
	 * Esta funcao vai receber como parametro uma nova posicao para movimentar o
	 * jogador e um HashMap que se certifica que a proxima posicao e
	 * possivel(nao e parede por exemplo) Se for valida entao movimenta, caso
	 * contrario mostra uma mensagem de erro
	 */
	public void movimentarHeroi(int pos, HashMap<Integer, Boolean> movimentos) {
		// Mover jogador para cima
		if (pos == 'w') {
			// Verificar se o HashMap contem a key com o valor 0(cima)
			if (movimentos.containsKey(Movimentos.MOVIMENTO_CIMA
					.getDirecaoInt())) {
				// Alterar a peca atual onde o heroi esta para uma peca livre
				tabuleiro.trocarPecas(
						Movimentos.MOVIMENTO_CIMA.getDirecaoInt(), heroi);
			} else
				System.err.println("Nao pode mover o heroi para cima!");
		} else if (pos == 's') {
			// Verificar se o HashMap contem a key com o valor 1(baixo)
			if (movimentos.containsKey(Movimentos.MOVIMENTO_BAIXO
					.getDirecaoInt())) {
				tabuleiro.trocarPecas(
						Movimentos.MOVIMENTO_BAIXO.getDirecaoInt(), heroi);
			} else
				System.err.println("Nao pode mover o heroi para baixo!");
		} else if (pos == 'a') {
			// Verificar se o HashMap contem a key com o valor 3(esquerda)
			if (movimentos.containsKey(Movimentos.MOVIMENTO_ESQUERDA
					.getDirecaoInt())) {
				tabuleiro.trocarPecas(
						Movimentos.MOVIMENTO_ESQUERDA.getDirecaoInt(), heroi);
			} else
				System.err.println("Nao pode mover o heroi para a esquerda!");
		} else if (pos == 'd') {
			// Verificar se o HashMap contem a key com o valor 2(direita)
			if (movimentos.containsKey(Movimentos.MOVIMENTO_DIREITA
					.getDirecaoInt())) {
				tabuleiro.trocarPecas(
						Movimentos.MOVIMENTO_DIREITA.getDirecaoInt(), heroi);
			} else
				System.err.println("Nao pode mover o heroi para a direita!");
		}
	}

	/**
	 * O movimento do dragao e feito em varios passos
	 * 
	 * Primeiro verificamos se a peca para onde queremos mover nao e parede nem
	 * e o dragao(para nao sobrepor as duas personagens) Se essa condicao for
	 * verdadeira entao atualizamos a posicao atual do dragao para livre e
	 * verificamos se esta nova peca corresponde a peca onde a espada esta. Se
	 * estiver entao alteramos uma propriedade do Dragao(naEspada) e mostramos
	 * um 'F'. Se um dragao estava marcado com 'F' anteriormente e agora se move
	 * para uma peca diferente da peca da espada entao mostramos um 'E'na peca
	 * da espada e um 'D' na peca do dragao(estado normal)
	 */
	public void movimentarDragao() {
		int posX = dragao.getPosX();
		int posY = dragao.getPosY();
		int dir = random.nextInt(4);
		char simbolo;
		if (dir == Movimentos.MOVIMENTO_CIMA.getDirecaoInt()) {
			simbolo = tabuleiro.getPecaTabuleiroSimbolo(posX, posY - 1);
			if (simbolo != TipoPeca.PAREDE.comoChar()
					&& simbolo != heroi.mostrarHeroi()
					&& simbolo != TipoPeca.SAIDA.comoChar()) {
				tabuleiro.getPecaTabuleiro(posX, posY).setSimbolo(
						TipoPeca.LIVRE.comoChar());
				dragao.atualizaPosicao(posX, posY - 1);
				verificarDragaoNaEspada();

				tabuleiro.getPecaTabuleiro(posX, posY - 1).setSimbolo(
						dragao.mostrarDragao(heroi));
			}
		} else if (dir == Movimentos.MOVIMENTO_BAIXO.getDirecaoInt()) {
			simbolo = tabuleiro.getPecaTabuleiroSimbolo(posX, posY + 1);
			if (simbolo != TipoPeca.PAREDE.comoChar()
					&& simbolo != heroi.mostrarHeroi()
					&& simbolo != TipoPeca.SAIDA.comoChar()) {
				tabuleiro.getPecaTabuleiro(posX, posY).setSimbolo(
						TipoPeca.LIVRE.comoChar());
				dragao.atualizaPosicao(posX, posY + 1);
				verificarDragaoNaEspada();

				tabuleiro.getPecaTabuleiro(posX, posY + 1).setSimbolo(
						dragao.mostrarDragao(heroi));
			}
		} else if (dir == Movimentos.MOVIMENTO_DIREITA.getDirecaoInt()) {
			simbolo = tabuleiro.getPecaTabuleiroSimbolo(posX + 1, posY);
			if (simbolo != TipoPeca.PAREDE.comoChar()
					&& simbolo != heroi.mostrarHeroi()
					&& simbolo != TipoPeca.SAIDA.comoChar()) {
				tabuleiro.getPecaTabuleiro(posX, posY).setSimbolo(
						TipoPeca.LIVRE.comoChar());
				dragao.atualizaPosicao(posX + 1, posY);
				verificarDragaoNaEspada();

				tabuleiro.getPecaTabuleiro(posX + 1, posY).setSimbolo(
						dragao.mostrarDragao(heroi));
			}
		} else if (dir == Movimentos.MOVIMENTO_ESQUERDA.getDirecaoInt()) {
			simbolo = tabuleiro.getPecaTabuleiroSimbolo(posX - 1, posY);
			if (simbolo != TipoPeca.PAREDE.comoChar()
					&& simbolo != heroi.mostrarHeroi()
					&& simbolo != TipoPeca.SAIDA.comoChar()) {
				tabuleiro.getPecaTabuleiro(posX, posY).setSimbolo(
						TipoPeca.LIVRE.comoChar());
				dragao.atualizaPosicao(posX - 1, posY);
				verificarDragaoNaEspada();

				tabuleiro.getPecaTabuleiro(posX - 1, posY).setSimbolo(
						dragao.mostrarDragao(heroi));
			}
		}
	}

	public void verificarDragaoNaEspada() {
		if (dragaoNaEspada()) {
			dragao.setNaEspada(true);
		} else {
			if (!heroi.getHeroiTemEspada())
				tabuleiro.getPecaTabuleiro(tabuleiro.getEspada().getPosX(),
						tabuleiro.getEspada().getPosY()).setSimbolo(
						TipoPeca.ESPADA.comoChar());
			dragao.setNaEspada(false);
		}
	}

	public String veriricarJogo() {
		if ((heroi.getPosX() == tabuleiro.getSaidaX())
				&& (heroi.getPosY() == tabuleiro.getSaidaY())
				&& heroi.getHeroiTemEspada()) {
			return EstadosJogo.JOGADOR_VENCEU.toString();
		}

		if (dragaoAdjacente()) {
			/**
			 * Depois de movimentar o heroi temos que verificar se a nova
			 * posicao e adjacente a posicao onde esta o dragao atualmente.
			 * 
			 * Se for e o heroi nao tiver espada entao o heroi morre. No caso de
			 * estar armado entao o dragao morre e desaparece
			 */
			if (!heroi.getHeroiTemEspada()) {
				if (!dragao.estaMorto() && !dragao.estaADormir()) {
					// Se o heroi nao estiver armado entao o jogo termina com a
					// morte do heroi
					return EstadosJogo.JOGADOR_MORREU.toString();
				}
			} else {
				// Neste caso o heroi esta armado
				if (!dragao.estaMorto() || dragao.estaADormir()) {
					System.out.println("O dragao esta morto!!");
					// Alterar o estado do dragao para morto
					dragao.setEstadoMorto(true);
					tabuleiro.getPecaTabuleiro(dragao.getPosX(),
							dragao.getPosY()).setSimbolo(
							dragao.mostrarDragao(heroi));
				}
			}
		}

		return EstadosJogo.CONTINUAR_JOGO.toString();
	}

	public boolean dragaoAdjacente() {
		// Estas variaveis foram declaradas para nao se estar constantemente a
		// chamar as funcoes que retornam as posicoes
		int heroiX = heroi.getPosX();
		int heroiY = heroi.getPosY();
		int dragaoX = dragao.getPosX();
		int dragaoY = dragao.getPosY();

		if (heroiX + 1 == dragaoX && heroiY == dragaoY)
			return true;
		else if (heroiX - 1 == dragaoX && heroiY == dragaoY)
			return true;
		else if (heroiX == dragaoX && heroiY - 1 == dragaoY)
			return true;
		else if (heroiX == dragaoX && heroiY + 1 == dragaoY)
			return true;
		else
			return false;
	}

	public boolean dragaoNaEspada() {
		return (dragao.getPosX() == tabuleiro.getEspada().getPosX())
				&& (dragao.getPosY() == tabuleiro.getEspada().getPosY());
	}
}
