package algorithms;

import maze.Movement;
import maze.Piece;
import maze.PieceType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Algorithm {

	ArrayList<ArrayList<Piece>> maze;
	boolean visitedPieces[][];
	Stack<Piece> piecesStack;
	Piece currentPiece;
	Random random;
	int mazeSize;

	public Algorithm(int mazeSize) {
		this.mazeSize = mazeSize;
		piecesStack = new Stack<Piece>();
		visitedPieces = new boolean[mazeSize][mazeSize];
		random = new Random();

		maze = createInitialMaze();
		setExit(maze);
	}

	public ArrayList<ArrayList<Piece>> createInitialMaze() {
		ArrayList<ArrayList<Piece>> mazeTemp = new ArrayList<ArrayList<Piece>>();
		for (int i = 0; i < mazeSize; i++) {
			ArrayList<Piece> linha = new ArrayList<Piece>();
			for (int j = 0; j < mazeSize; j++) {
				linha.add(new Piece(j, i,
						(j % 2 != 0 && i % 2 != 0 ? PieceType.FREE.asChar()
								: PieceType.WALL.asChar())));
			}
			mazeTemp.add(linha);
		}

		// Iniciar todas as Pieces visitadas a false
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				visitedPieces[i][j] = false;
			}
		}

		return mazeTemp;
	}

	public void setExit(ArrayList<ArrayList<Piece>> maze) {
		int exitLine;
		int exitColumn;

		// Inicialmente vamos recolher todas as linhas que sao impares para um
		// ArrayList
		ArrayList<Integer> linhasPossiveis = new ArrayList<Integer>();
		for (int i = 0; i < mazeSize; i++)
			if (i % 2 != 0)
				linhasPossiveis.add(i);

		// Para seleccionar a linha da EXIT escolhemos uma dessas linhas
		// aleatoriamente
		exitLine = linhasPossiveis.get(random.nextInt(linhasPossiveis.size()));
		if (exitLine > mazeSize / 2)
			exitColumn = mazeSize - 1;
		else
			exitColumn = 0;

		// Marcar a Piece de EXIT no maze
		Piece exitPiece = maze.get(exitLine).get(exitColumn);
		exitPiece.setSymbol(PieceType.EXIT.asChar());

		// A Piece atual sera a Piece que esta imediatamente ao lado da EXIT
		currentPiece = maze.get(exitPiece.getPosY()).get(
				exitPiece.getPosX() == 0 ? exitPiece.getPosX() + 1 : exitPiece
						.getPosX() - 1);

		// Marcar a Piece atual como visitada e adicionar ao stack
		visitedPieces[currentPiece.getPosY()][currentPiece.getPosX()] = true;
		piecesStack.push(currentPiece);
	}

	public ArrayList<ArrayList<Piece>> createMaze() {
		while (!piecesStack.empty()) {
			// Seleccionar a lista das Pieces neighbour que ainda nao foram
			// visitadas
			ArrayList<Piece> PiecesVizinhas = getNeighbourPieces();

			// Se existir alguma Piece vizinha que ainda nao foi visitada
			if (PiecesVizinhas.size() > 0) {
				// Escolher uma dessas Pieces aleatoriamente
				int PiecePos = random.nextInt(PiecesVizinhas.size());

				Piece PieceTemp = PiecesVizinhas.get(PiecePos);
				// Mover para a proxima casa
				moveCurrentPiece(PieceTemp, selecionarDirecao(PieceTemp));
			} else {
				currentPiece = piecesStack.pop();
			}
		}

		return maze;
	}

	public int selecionarDirecao(Piece Piece) {
		if (Piece.getPosY() > currentPiece.getPosY()
				&& Piece.getPosX() == currentPiece.getPosX()) {
			return Movement.MOVE_DOWN.getDirecaoInt();
		} else if (Piece.getPosY() < currentPiece.getPosY()
				&& Piece.getPosX() == currentPiece.getPosX()) {
			return Movement.MOVE_UP.getDirecaoInt();
		} else if (Piece.getPosY() == currentPiece.getPosY()
				&& Piece.getPosX() > currentPiece.getPosX()) {
			return Movement.MOVE_RIGHT.getDirecaoInt();
		} else {
			return Movement.MOVE_LEFT.getDirecaoInt();
		}
	}

	public void moveCurrentPiece(Piece Piece, int direcao) {
		if (direcao == Movement.MOVE_UP.getDirecaoInt()) {
			maze.get(Piece.getPosY() + 1).get(Piece.getPosX())
					.setSymbol(PieceType.FREE.asChar());
		} else if (direcao == Movement.MOVE_DOWN.getDirecaoInt()) {
			maze.get(Piece.getPosY() - 1).get(Piece.getPosX())
					.setSymbol(PieceType.FREE.asChar());
		} else if (direcao == Movement.MOVE_RIGHT.getDirecaoInt()) {
			maze.get(Piece.getPosY()).get(Piece.getPosX() - 1)
					.setSymbol(PieceType.FREE.asChar());
		} else {
			maze.get(Piece.getPosY()).get(Piece.getPosX() + 1)
					.setSymbol(PieceType.FREE.asChar());
		}

		// Marcar como Piece FREE
		maze.get(Piece.getPosY()).get(Piece.getPosX())
				.setSymbol(PieceType.FREE.asChar());
		// Atualizar a Piece atual
		currentPiece = Piece;

		// Adicionar a nova Piece ao stack
		piecesStack.push(currentPiece);
		// Marcar a Piece como visitada
		visitedPieces[currentPiece.getPosY()][currentPiece.getPosX()] = true;
	}

	public ArrayList<Piece> getNeighbourPieces() {
		ArrayList<Piece> neighbour = new ArrayList<Piece>();

		// Verificar se podemos adicionar a Piece que esta em cima
		if (currentPiece.getPosY() - 2 > 0 && currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() - 2][currentPiece.getPosX()]) {
				neighbour.add(maze.get(currentPiece.getPosY() - 2).get(
						currentPiece.getPosX()));
			}
		}

		// Verificar se podemos adicionar a Piece que esta em baixo
		if (currentPiece.getPosY() + 2 < mazeSize - 1
				&& currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() + 2][currentPiece.getPosX()]) {
				neighbour.add(maze.get(currentPiece.getPosY() + 2).get(
						currentPiece.getPosX()));
			}
		}

		// Verificar se podemos adicionar a Piece que esta a direita
		if (currentPiece.getPosX() + 2 < mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() + 2]) {
				neighbour.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() + 2));
			}
		}

		// Verificar se podemos adicionar a Piece que esta a esquerda
		if (currentPiece.getPosX() - 2 > 0) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() - 2]) {
				neighbour.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() - 2));
			}
		}

		return neighbour;
	}
}
