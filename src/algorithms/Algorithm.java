package algorithms;

import logic.Movement;
import logic.Piece;
import logic.PieceType;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * 
 * Class that has the maze´s generation algorithm based on dfs(depth-first
 * search) and implemented using recursive backtracking
 * 
 * @author Andre Pinheiro
 * @author Jose Peixoto
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
	 * builds a maze with a odd size
	 * 
	 * @param mazeSize
	 *            maze size integer
	 * 
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
	 * 
	 * 
	 * Initially the board is build line by line , in each cell of the line if
	 * the line and column have an odd index that cell is marked as empty
	 * otherwise its a wall
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
		 * Inicialmente nenhuma peca foi visitada, marcar todas como nao
		 * visitadas
		 */
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				visitedPieces[i][j] = false;
			}
		}
		return mazeTemp;
	}

	/**
	 * This function marks one cell as Exit point in a board, starts by gadering
	 * all odd lines and inserts them into an ArrayList. The last step is to
	 * pick a random line, and from that to pick a column. If the exit line is
	 * behond the midle of the board then its picked the last column otherwise
	 * its the first column.
	 * 
	 * @param maze
	 *            Current maze
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
	 * Core function of this class. And in charge of running depth-first-search.
	 * Note that at this point the stack as already one piece(the exit).
	 * 
	 * @return a random board
	 */
	public ArrayList<ArrayList<Piece>> createMaze() {
		// Enquanto existirem pecas nao visitadas
		while (!piecesStack.empty()) {
			// Extrair uma lista de pecas vizinhas
			ArrayList<Piece> PiecesVizinhas = getneighborPieces();

			// Se existir alguma peca vizinha que ainda nao foi visitada
			if (PiecesVizinhas.size() > 0) {
				// Escolher uma dessas Pieces aleatoriamente
				int PiecePos = random.nextInt(PiecesVizinhas.size());

				Piece PieceTemp = PiecesVizinhas.get(PiecePos);
				// Mover para a proxima casa, adicionar a peca a stack e marcar
				// esta peca como sendo a atual
				moveCurrentPiece(PieceTemp, selectDirection(PieceTemp));
			} else {
				/**
				 * 
				 * If theres no available free pieces in the neighborhood we use
				 * the recursive backtracking until we find one that has
				 * unvisited pieces in its neighborhood.
				 * 
				 * If this doesnt happend then the stack will at some point be
				 * empty, returning the final puzzle
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
	 * 
	 * This function moves the actual piece to a new position, by doing so it
	 * also marks the new piece as free
	 * 
	 * @param Piece
	 *            The new actual piece
	 * @param direcao
	 *            The direction to where the piece will be moved to
	 * 
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

		// Marks the piece as free
		maze.get(Piece.getPosY()).get(Piece.getPosX())
				.setSymbol(PieceType.FREE.asString());

		// Updates the actual pieace as a new piece
		currentPiece = Piece;

		// Adds the piece to the stack in order to analize its neighbors
		piecesStack.push(currentPiece);

		// Marks this new piece as visited
		visitedPieces[currentPiece.getPosY()][currentPiece.getPosX()] = true;
	}

	/**
	 * 
	 * This function checks all its neighbor´s pieces in all 4 directions
	 * possibles And also checks if that piece was visited or not yet. If it has
	 * been visited we ignore her and move to the next piece
	 * 
	 * @return A ArrayList of object Piece that are neighbors of the actual
	 *         pieace
	 * 
	 */
	public ArrayList<Piece> getneighborPieces() {
		ArrayList<Piece> neighbor = new ArrayList<Piece>();

		// Checks if the piece that is above can be analize
		if (currentPiece.getPosY() - 2 > 0 && currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() - 2][currentPiece
					.getPosX()]) {
				neighbor.add(maze.get(currentPiece.getPosY() - 2).get(
						currentPiece.getPosX()));
			}
		}
		// Checks if the piece that is below can be analize
		if (currentPiece.getPosY() + 2 < mazeSize - 1
				&& currentPiece.getPosX() != 0
				&& currentPiece.getPosX() != mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY() + 2][currentPiece
					.getPosX()]) {
				neighbor.add(maze.get(currentPiece.getPosY() + 2).get(
						currentPiece.getPosX()));
			}
		}

		// Checks if the piece that is at the right can be analize
		if (currentPiece.getPosX() + 2 < mazeSize - 1) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() + 2]) {
				neighbor.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() + 2));
			}
		}

		// Checks if the piece that is at the left can be analize
		if (currentPiece.getPosX() - 2 > 0) {
			if (!visitedPieces[currentPiece.getPosY()][currentPiece.getPosX() - 2]) {
				neighbor.add(maze.get(currentPiece.getPosY()).get(
						currentPiece.getPosX() - 2));
			}
		}
		return neighbor;
	}
}
