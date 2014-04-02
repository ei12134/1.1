package algorithms;

import logic.Movement;
import logic.Piece;
import logic.PieceType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Algorithm {

	public ArrayList<ArrayList<Piece>> maze;
	public boolean visitedPieces[][];
	public Stack<Piece> piecesStack;
	public Piece currentPiece;
	public Random random;
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
						(j % 2 != 0 && i % 2 != 0 ? PieceType.FREE.asString()
								: PieceType.WALL.asString())));
			}
			mazeTemp.add(linha);
		}

		// Initialize all visited Pieces to false
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

	public ArrayList<ArrayList<Piece>> createMaze() {
		while (!piecesStack.empty()) {
			// Seleccionar a lista das Pieces neighbor que ainda nao foram
			// visitadas
			ArrayList<Piece> PiecesVizinhas = getneighborPieces();

			// Se existir alguma Piece vizinha que ainda nao foi visitada
			if (PiecesVizinhas.size() > 0) {
				// Escolher uma dessas Pieces aleatoriamente
				int PiecePos = random.nextInt(PiecesVizinhas.size());

				Piece PieceTemp = PiecesVizinhas.get(PiecePos);
				// Mover para a proxima casa
				moveCurrentPiece(PieceTemp, selectDirection(PieceTemp));
			} else {
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

		// Set as Free Piece
		maze.get(Piece.getPosY()).get(Piece.getPosX())
				.setSymbol(PieceType.FREE.asString());
		// Update current Piece
		currentPiece = Piece;

		// Add new Piece to the stack
		piecesStack.push(currentPiece);

		// Set Piece as visited
		visitedPieces[currentPiece.getPosY()][currentPiece.getPosX()] = true;
	}

	public ArrayList<Piece> getneighborPieces() {
		ArrayList<Piece> neighbor = new ArrayList<Piece>();

		// Check if the Piece above can be added
		if (currentPiece.getPosY() - 2 > 0 && currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() - 2][currentPiece
					.getPosX()]) {
				neighbor.add(maze.get(currentPiece.getPosY() - 2).get(
						currentPiece.getPosX()));
			}
		}

		// Check if the Piece underneath can be added
		if (currentPiece.getPosY() + 2 < mazeSize - 1
				&& currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() + 2][currentPiece
					.getPosX()]) {
				neighbor.add(maze.get(currentPiece.getPosY() + 2).get(
						currentPiece.getPosX()));
			}
		}

		// Check if the Piece on the right can be added
		if (currentPiece.getPosX() + 2 < mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() + 2]) {
				neighbor.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() + 2));
			}
		}

		// Check if the Piece on the left can be added
		if (currentPiece.getPosX() - 2 > 0) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() - 2]) {
				neighbor.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() - 2));
			}
		}
		return neighbor;
	}
}
