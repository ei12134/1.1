import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class labyrinth {
	private static int size = 10;
	private static int sizeSquare = size * size;
	private static int posHero = 11;
	private static int posSword = 81;
	private static int posDragon = 31;
	private static int posExit = 59;
	static String[] heroChar = { " H " };
	static String[] dragonChar = { " D " };
	static String[] exitChar = { " S " };
	static String[] swordChar = { " E " };
	static ArrayList<String> labyrinth = new ArrayList<String>(sizeSquare);

	public static void main(String[] args) {
		// fill with spaces
		for (int i = 0; i < sizeSquare; i++)
			labyrinth.add("   ");

		// upper and lower labyrinth border limits
		for (int i = 0; i < size; i++)
			labyrinth.set(i, " X ");
		for (int i = sizeSquare - size; i < sizeSquare; i++)
			labyrinth.set(i, " X ");

		// left and right labyrinth border limits
		for (int i = size; i < sizeSquare - size; i += 10)
			labyrinth.set(i, " X ");
		for (int i = size * 2 - 1; i < sizeSquare - size; i += 10)
			labyrinth.set(i, " X ");

		labyrinth.set(22, " X ");
		labyrinth.set(32, " X ");
		labyrinth.set(42, " X ");
		;
		labyrinth.set(62, " X ");
		labyrinth.set(72, " X ");
		labyrinth.set(82, " X ");

		labyrinth.set(23, " X ");
		labyrinth.set(33, " X ");
		labyrinth.set(43, " X ");
		labyrinth.set(63, " X ");
		labyrinth.set(73, " X ");
		labyrinth.set(83, " X ");

		labyrinth.set(25, " X ");
		labyrinth.set(35, " X ");
		labyrinth.set(45, " X ");
		labyrinth.set(65, " X ");
		labyrinth.set(75, " X ");
		;

		labyrinth.set(27, " X ");
		labyrinth.set(37, " X ");
		labyrinth.set(47, " X ");
		labyrinth.set(57, " X ");
		labyrinth.set(67, " X ");
		labyrinth.set(77, " X ");

		// hero position
		labyrinth.set(posHero, heroChar[0]);

		// dragon position
		labyrinth.set(posDragon, dragonChar[0]);

		// exit position
		labyrinth.set(posExit, exitChar[0]);

		// sword position
		labyrinth.set(posSword, swordChar[0]);

		DisplayLabyrinth();
		HeroMove();

	}

	public static void DisplayLabyrinth() {
		for (int i = 0; i < labyrinth.size(); i++) {
			if ((i + 1) % size != 0)
				System.out.print(labyrinth.get(i));
			else
				System.out.println(labyrinth.get(i));
		}
	}

	public static void HeroMove() {

		String input = "x";
		String quit = "q";
		String message = null;
		while (!input.equals(quit)) {
			input = getkey();
			String right = "d";
			
			if (input.equals(right) && (labyrinth.get(posHero + 1) != " X ")) {

				if ((labyrinth.get(posHero + 1) == " S ") && (heroChar[0] != " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(posHero + 1) == " E ") {
						heroChar[0] = " A ";
					} else if (labyrinth.get(posHero + 1) == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";
					}
					labyrinth.set(posHero, "   ");
					labyrinth.set(posHero + 1, heroChar[0]);
					posHero++;

				}

			} else if (input.equals("a") && (labyrinth.get(posHero - 1) != " X ")) {
				if ((labyrinth.get(posHero - 1) == " S ") && (heroChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(posHero - 1) == " E ") {

						heroChar[0] = " A ";
					} else if (labyrinth.get(posHero - 1) == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					labyrinth.set(posHero, "   ");
					labyrinth.set(posHero - 1, heroChar[0]);
					posHero--;
				}

				labyrinth.get(posHero + size);
			} else if (input.equals("s") && (labyrinth.get(posHero + size) != " X ")) {

				if ((labyrinth.get(posHero + size) == " S ")
						&& (heroChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(posHero + size) == " E ") {
						heroChar[0] = " A ";
					} else if (labyrinth.get(posHero + size) == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";

					}
					labyrinth.set(posHero, "   ");
					labyrinth.set(posHero + size, heroChar[0]);
					posHero += size;
				}

			} else if (input.equals("w") && (labyrinth.get(posHero - size) != " X ")) {

				if ((labyrinth.get(posHero - size) == " S ")
						&& (heroChar[0] == " A ")) {
					message = "Sword's missing. Go get it first.";
				} else {
					if (labyrinth.get(posHero - size) == " E ") {
						heroChar[0] = " A ";
					} else if (labyrinth.get(posHero - size) == " S ") {
						input = quit;
						message = "Level Cleared! Congratulations!";

					}
					labyrinth.set(posHero, "   ");
					labyrinth.set(posHero - size, heroChar[0]);
					posHero -= size;

				}
			} else if (input.equals("q"))
				message = "Game aborted.";

			// check for possible adjacent dragon
			if (((labyrinth.get(posHero + 1) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero - 1) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero + size) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero - size) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero + 9) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero + 1) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero - 9) == labyrinth.get(posDragon)) || ((labyrinth.get(posHero - 11) == labyrinth.get(posDragon)))
					&& (dragonChar[0] != "   "))) {
				if (heroChar[0] == " A ") {
					dragonChar[0] = "   ";
					labyrinth.set(posDragon, dragonChar[0]);
					message = "Dragon slayed!";
				} else {
					heroChar[0] = "   ";
					labyrinth.set(posHero, heroChar[0]);
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
			if (labyrinth.get(posDragon + 1) != " X ") {
				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword," E ");
				} else if (posDragon + 1 == posSword) {
					dragonChar[0] = " B ";
					labyrinth.set(posDragon,"   ");
				} else {
					labyrinth.set(posDragon,"   ");
				}
				posDragon++;
			}
			break;
		case 1:
			if (labyrinth.get(posDragon - 1) != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword," E ");
				} else if (posDragon - 1 == posSword) {
					dragonChar[0] = " B ";
					labyrinth.set(posDragon,"   ");
				} else {
					labyrinth.set(posDragon,"   ");
				}
				posDragon--;
			}
			break;
		case 2:
			if (labyrinth.get(posDragon + size) != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword," E ");
				} else if (posDragon + size == posSword) {
					dragonChar[0] = " F ";
					labyrinth.set(posDragon,"   ");
				} else {
					labyrinth.set(posDragon,"   ");
				}
				posDragon += size;
			}
			break;
		case 3:
			if (labyrinth.get(posDragon - size) != " X ") {
				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword," E ");
				} else if (posDragon - size == posSword) {
					dragonChar[0] = " B ";
					labyrinth.set(posDragon,"   ");
				} else {
					labyrinth.set(posDragon,"   ");
				}
				posDragon -= size;
			}
			break;
		default:
			break;
		}
		labyrinth.set(posDragon,dragonChar[0]);

	}

	public static String getkey() {
		System.out.print("(w/a/s/d) to move (q) to abort: ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

}