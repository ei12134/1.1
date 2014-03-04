public class Main {

	public static void main(String[] args) {

		// Start the game
		Builder start = new Builder();
		start.defaultMaze(); //start.generateMaze();
		start.heroMove();
	}
}