import java.util.Scanner;
import java.util.Random;

public class labyrinth {
	private static int size = 10;
	private static int sizeSquare = size*size;
	private static int posHero = 11;
	private static int posSword = 81;
	private static int posDragon = 31;
	private static int posExit = 59;
	static String[] heroChar = { " H " };
	static String[] dragonChar = { " D " };
	static String[] exitChar = { " S " };
	static String[] swordChar = { " E " };
	static String[] labyrinth = new String[sizeSquare];

	public static void main(String[] args) {

		// fill with spaces
		for (int i = 0; i < sizeSquare; i++)
			labyrinth[i] = "   ";

		// upper and lower labyrinth border limits
		for (int i = 0; i < size; i++)
			labyrinth[i] = " X ";
		for (int i = sizeSquare-size; i < sizeSquare; i++)
			labyrinth[i] = " X ";

		// left and right labyrinth border limits
		for (int i = size; i < sizeSquare-size; i += 10)
			labyrinth[i] = " X ";
		for (int i = size*2-1; i < sizeSquare-size; i += 10)
			labyrinth[i] = " X ";

		labyrinth[22] = " X ";
		labyrinth[32] = " X ";
		labyrinth[42] = " X ";
		labyrinth[62] = " X ";
		labyrinth[72] = " X ";
		labyrinth[82] = " X ";

		labyrinth[23] = " X ";
		labyrinth[33] = " X ";
		labyrinth[43] = " X ";
		labyrinth[63] = " X ";
		labyrinth[73] = " X ";
		labyrinth[83] = " X ";

		labyrinth[25] = " X ";
		labyrinth[35] = " X ";
		labyrinth[45] = " X ";
		labyrinth[65] = " X ";
		labyrinth[75] = " X ";

		labyrinth[27] = " X ";
		labyrinth[37] = " X ";
		labyrinth[47] = " X ";
		labyrinth[57] = " X ";
		labyrinth[67] = " X ";
		labyrinth[77] = " X ";

		// hero position
		labyrinth[posHero] = heroChar[0];

		// dragon position
		labyrinth[posDragon] = dragonChar[0];

		// exit position
		labyrinth[posExit] = exitChar[0];

		// sword position
		labyrinth[posSword] = swordChar[0];

		DisplayLabyrinth();
		HeroMove();

	}

	public static void DisplayLabyrinth() {
		for (int i = 0; i < labyrinth.length; i++) {
			if ((i + 1) % size != 0)
				System.out.print(labyrinth[i]);
			else
				System.out.println(labyrinth[i]);
		}
	}

	public static void HeroMove() {

		String input = "x";
		String quit = "q";
		String message = null;
		while (!input.equals(quit)) {
			input = getkey();
			String right = "d";
			if (input.equals(right) && (labyrinth[posHero + 1] != " X ")) {

				if ((labyrinth[posHero + 1] == " S ") && (heroChar[0] != " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHero + 1] == " E ") {
						heroChar[0] = " A ";
					} else if (labyrinth[posHero + 1] == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";
					}
					labyrinth[posHero] = "   ";
					labyrinth[posHero + 1] = heroChar[0];
					posHero++;

				}

			} else if (input.equals("a") && (labyrinth[posHero - 1] != " X ")) {
				if ((labyrinth[posHero - 1] == " S ") && (heroChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHero - 1] == " E ") {

						heroChar[0] = " A ";
					} else if (labyrinth[posHero - 1] == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					labyrinth[posHero] = "   ";
					labyrinth[posHero - 1] = heroChar[0];
					posHero--;
				}

			} else if (input.equals("s") && (labyrinth[posHero + size] != " X ")) {

				if ((labyrinth[posHero + size] == " S ")
						&& (heroChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHero + size] == " E ") {
						heroChar[0] = " A ";
					} else if (labyrinth[posHero + size] == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";

					}
					labyrinth[posHero] = "   ";
					labyrinth[posHero + size] = heroChar[0];
					posHero += size;
				}

			} else if (input.equals("w") && (labyrinth[posHero - size] != " X ")) {

				if ((labyrinth[posHero - size] == " S ")
						&& (heroChar[0] == " A ")) {
					message = "Sword's missing. Go get it first.";
				} else {
					if (labyrinth[posHero - size] == " E ") {
						heroChar[0] = " A ";
					} else if (labyrinth[posHero - size] == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";

					}
					labyrinth[posHero] = "   ";
					labyrinth[posHero - size] = heroChar[0];
					posHero -= size;

				}
			} else if (input.equals("q"))
				message = "Game aborted.";

			// check for possible adjacent dragon
			if (((labyrinth[posHero + 1] == labyrinth[posDragon])
					|| (labyrinth[posHero - 1] == labyrinth[posDragon])
					|| (labyrinth[posHero + size] == labyrinth[posDragon])
					|| (labyrinth[posHero - size] == labyrinth[posDragon])
					|| (labyrinth[posHero + 9] == labyrinth[posDragon])
					|| (labyrinth[posHero + 11] == labyrinth[posDragon])
					|| (labyrinth[posHero - 9] == labyrinth[posDragon]) || (labyrinth[posHero - 11] == labyrinth[posDragon]))
					&& (dragonChar[0] != "   ")) {
				if (heroChar[0] == " A ") {

					dragonChar[0] = "   ";
					labyrinth[posDragon] = dragonChar[0];
					message = "Dragon slayed!";
				} else {

					heroChar[0] = "   ";
					labyrinth[posHero] = heroChar[0];
					message = "Hero Died. Avoid the dragon if unnarmed.";
					input = quit;
				}
			}

			DragonMove();
			DisplayLabyrinth();

			// prints game messages if any
			if (message != null) {
				System.out.println(message);
				message = null;
			}
		}
	}

	public static void DragonMove() {
		// generate random move from 0 to 4
		Random r = new Random();
		int randomNum = r.nextInt(4);
		switch (randomNum) {
		case 0:
			if (labyrinth[posDragon + 1] != " X ") {
				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth[posSword] = " E ";
				} else if (posDragon + 1 == posSword) {
					dragonChar[0] = " B ";
					labyrinth[posDragon] = "   ";
				} else {
					labyrinth[posDragon] = "   ";
				}
				posDragon++;
			}
			break;
		case 1:
			if (labyrinth[posDragon - 1] != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth[posSword] = " E ";
				} else if (posDragon - 1 == posSword) {
					dragonChar[0] = " B ";
					labyrinth[posDragon] = "   ";
				} else {
					labyrinth[posDragon] = "   ";
				}
				posDragon--;
			}
			break;
		case 2:
			if (labyrinth[posDragon + size] != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth[posSword] = " E ";
				} else if (posDragon + size == posSword) {
					dragonChar[0] = " F ";
					labyrinth[posDragon] = "   ";
				} else {
					labyrinth[posDragon] = "   ";
				}
				posDragon += size;
			}
			break;
		case 3:
			if (labyrinth[posDragon - size] != " X ") {
				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth[posSword] = " E ";
				} else if (posDragon - size == posSword) {
					dragonChar[0] = " B ";
					labyrinth[posDragon] = "   ";
				} else {
					labyrinth[posDragon] = "   ";
				}
				posDragon -= size;
			}
			break;
		default:
			break;
		}
		labyrinth[posDragon] = dragonChar[0];

	}

	public static String getkey() {
		System.out.print("(w/a/s/d) to move (q) to abort: ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

}