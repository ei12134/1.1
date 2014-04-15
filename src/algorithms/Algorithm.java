package algorithms;

import logic.Movement;
import logic.Piece;
import logic.PieceType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Classe que contem o algoritmo de geracao de mazes
 * E baseado no dfs(depth-first search) e foi implementado utilizando o 
 * recursive backtracking
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 * 
 */
public class Algorithm {

	public ArrayList<ArrayList<Piece>> maze;
	public boolean visitedPieces[][];
	public Stack<Piece> piecesStack;
	public Piece currentPiece;
	public Random random;
	int mazeSize;

	/**
	 * Constroi um maze de tamanho impar
	 * 
	 * @param mazeSize O tamanho do maze
	 */
	public Algorithm(int mazeSize) {
		this.mazeSize = mazeSize;
		piecesStack = new Stack<Piece>();
		visitedPieces = new boolean[mazeSize][mazeSize];
		random = new Random();

		maze = createInitialMaze();
		setExit(maze);
	}

	/**
	 * Inicialmente construimos um tabuleiro linha a linha
	 * Em cada posicao da linha verificamos se essa posicao estao associados
	 * linhas e colunas impares. Se for o caso então marcamos essa peca como livre,
	 * caso contrario e uma parede
	 * 
	 * @return two dimensional <code>ArrayList</code> with Pieces
	 */
	public ArrayList<ArrayList<Piece>> createInitialMaze() {
		ArrayList<ArrayList<Piece>> mazeTemp = new ArrayList<ArrayList<Piece>>();
		for (int i = 0; i < mazeSize; i++) {
			ArrayList<Piece> linha = new ArrayList<Piece>();
			for (int j = 0; j < mazeSize; j++) {
				linha.add(new Piece(j, i,
						(j % 2 != 0 && i % 2 != 0 ? PieceType.FREE.asString()
								: PieceType.WALL.asString())));
			}
			mazeTemp.add(linha);
		}

		/**
		 * Inicialmente nenhuma peca foi visitada, marcar 
		 * todas como nao visitadas
		 */
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				visitedPieces[i][j] = false;
			}
		}
		return mazeTemp;
	}

	
	/**
	 * Esta funcao marca um ponto de saida num tabuleiro
	 * Comeca por recolher todas as linhas que sao impares e insere todas
	 * num ArrayList. 
	 * O ultimo passo e entao fazer um random dessas linhas para que uma seja
	 * escolhida aleatoriamente. A posicao da coluna e determinada a partir da linha.
	 * Se a linha de saida estiver para la do meio do tabuleiro entao sera escolhida a ultima coluna,
	 * caso contrario sera a primeira coluna
	 * 
	 * @param maze O tabuleiro que estamos a analisar atualmente
	 */
	public void setExit(ArrayList<ArrayList<Piece>> maze) {
		int exitLine;
		int exitColumn;

		// Gather odd lines to ArrayList
		ArrayList<Integer> oddLines = new ArrayList<Integer>();
		for (int i = 0; i < mazeSize; i++)
			if (i % 2 != 0)
				oddLines.add(i);

		// Choose one odd line randomly
		exitLine = oddLines.get(random.nextInt(oddLines.size()));
		if (exitLine > mazeSize / 2)
			exitColumn = mazeSize - 1;
		else
			exitColumn = 0;

		// Set the chosen Piece as exit
		Piece exitPiece = maze.get(exitLine).get(exitColumn);
		exitPiece.setSymbol(PieceType.EXIT.asString());

		// Current Piece will be exit neighbor
		currentPiece = maze.get(exitPiece.getPosY()).get(
				exitPiece.getPosX() == 0 ? exitPiece.getPosX() + 1 : exitPiece
						.getPosX() - 1);

		// Set current Piece as visited and add it to stack
		visitedPieces[currentPiece.getPosY()][currentPiece.getPosX()] = true;
		piecesStack.push(currentPiece);
	}

	
	/**
	 * Funcao principal desta classe. 
	 * E responsavel por correr o depth-first search
	 * De notar que neste ponto a stack ja tem uma peca(a peca de saida)
	 * 
	 * @return Um tabuleiro aleatorio
	 */
	public ArrayList<ArrayList<Piece>> createMaze() {
		//Enquanto existirem pecas nao visitadas
		while (!piecesStack.empty()) {
			//Extrair uma lista de pecas vizinhas
			ArrayList<Piece> PiecesVizinhas = getneighborPieces();

			//Se existir alguma peca vizinha que ainda nao foi visitada
			if (PiecesVizinhas.size() > 0) {
				// Escolher uma dessas Pieces aleatoriamente
				int PiecePos = random.nextInt(PiecesVizinhas.size());

				Piece PieceTemp = PiecesVizinhas.get(PiecePos);
				//Mover para a proxima casa, adicionar a peca a stack e marcar esta peca como sendo a atual
				moveCurrentPiece(PieceTemp, selectDirection(PieceTemp));
			} else {
				/**
				 * Se nao existirem pecas vizinhas disponiveis entao temos que 
				 * recorrer ao recursive backtracking ate encontrarmos uma que 
				 * tenha pecas vizinhas ainda nao visitadas
				 * 
				 * Se isso nao acontecer entao a stack acabara por ficar vazia,
				 * retornando o puzzle final
				 */
				currentPiece = piecesStack.pop();
			}
		}
		return maze;
	}

	public int selectDirection(Piece Piece) {
		if (Piece.getPosY() > currentPiece.getPosY()
				&& Piece.getPosX() == currentPiece.getPosX()) {
			return Movement.MOVE_DOWN.getDirection();
		} else if (Piece.getPosY() < currentPiece.getPosY()
				&& Piece.getPosX() == currentPiece.getPosX()) {
			return Movement.MOVE_UP.getDirection();
		} else if (Piece.getPosY() == currentPiece.getPosY()
				&& Piece.getPosX() > currentPiece.getPosX()) {
			return Movement.MOVE_RIGHT.getDirection();
		} else {
			return Movement.MOVE_LEFT.getDirection();
		}
	}

	
	/**
	 * Esta funcao move a peca atual para uma nova posicao
	 * Ao mover a peca para uma nova direcao tambem marca 
	 * a nova peca como livre 
	 * 
	 * 
	 * @param Piece A nova peca atual
	 * @param direcao A direcao para onde a peca Piece sera movida
	 */
	public void moveCurrentPiece(Piece Piece, int direcao) {
		if (direcao == Movement.MOVE_UP.getDirection()) {
			maze.get(Piece.getPosY() + 1).get(Piece.getPosX())
					.setSymbol(PieceType.FREE.asString());
		} else if (direcao == Movement.MOVE_DOWN.getDirection()) {
			maze.get(Piece.getPosY() - 1).get(Piece.getPosX())
					.setSymbol(PieceType.FREE.asString());
		} else if (direcao == Movement.MOVE_RIGHT.getDirection()) {
			maze.get(Piece.getPosY()).get(Piece.getPosX() - 1)
					.setSymbol(PieceType.FREE.asString());
		} else {
			maze.get(Piece.getPosY()).get(Piece.getPosX() + 1)
					.setSymbol(PieceType.FREE.asString());
		}

		//Marcar a peca como livre
		maze.get(Piece.getPosY()).get(Piece.getPosX())
				.setSymbol(PieceType.FREE.asString());
		//Atualizar a peca atual para uma nova peca
		currentPiece = Piece;

		//Adicionar a peca ao stack para se poder analisar os seus vizinhos
		piecesStack.push(currentPiece);

		//Marcar esta nova peca como visitada
		visitedPieces[currentPiece.getPosY()][currentPiece.getPosX()] = true;
	}

	
	/**
	 * Esta funcao verificar todas as pecas vizinhas nas 4 direccoes possiveis
	 * Alem de verificar ainda tem o cuidado de ver se essa peca ainda nao 
	 * foi visitada. Caso tenha sida e ignorada e passa para a peca seguinte
	 * @return Um ArrayList de objetos Piece que sao vizinhos da peca atual
	 */
	public ArrayList<Piece> getneighborPieces() {
		ArrayList<Piece> neighbor = new ArrayList<Piece>();

		//Verifica se a peca que esta em cima pode ser analisada
		if (currentPiece.getPosY() - 2 > 0 && currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() - 2][currentPiece
					.getPosX()]) {
				neighbor.add(maze.get(currentPiece.getPosY() - 2).get(
						currentPiece.getPosX()));
			}
		}

		//Verifica se a peca que esta em baixo pode ser analisada
		if (currentPiece.getPosY() + 2 < mazeSize - 1
				&& currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() + 2][currentPiece
					.getPosX()]) {
				neighbor.add(maze.get(currentPiece.getPosY() + 2).get(
						currentPiece.getPosX()));
			}
		}

		//Verifica se a peca que esta a direita pode ser analisada
		if (currentPiece.getPosX() + 2 < mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() + 2]) {
				neighbor.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() + 2));
			}
		}

		//Verifica se a peca que esta a esquerda pode ser analisada
		if (currentPiece.getPosX() - 2 > 0) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() - 2]) {
				neighbor.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() - 2));
			}
		}
		return neighbor;
	}
}
