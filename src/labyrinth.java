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
	static String[] wallChar = { " X " };
	static String[] emptyChar = { "   " };
	static ArrayList<String> labyrinth = new ArrayList<String>(sizeSquare);

	public static void main(String[] args) {
	
		BuildLabyrinth();
		DisplayLabyrinth();
		HeroMove();

	}

	public static void BuildLabyrinth() {
		System.out.print("Enter labyrinth size: ");
		Scanner scan = new Scanner(System.in);
		size = scan.nextInt();
		sizeSquare = size * size;
		labyrinth = new ArrayList<String>(sizeSquare);
		
		// fill with spaces
		for (int i = 0; i < sizeSquare; i++)
			labyrinth.add(emptyChar[0]);

		// upper and lower labyrinth border limits
		for (int i = 0; i < size; i++)
			labyrinth.set(i, wallChar[0]);
		for (int i = sizeSquare - size; i < sizeSquare; i++)
			labyrinth.set(i, wallChar[0]);

		// left and right labyrinth border limits
		for (int i = size; i < sizeSquare - size; i += size)
			labyrinth.set(i, wallChar[0]);
		for (int i = size * 2 - 1; i < sizeSquare - size; i += size)
			labyrinth.set(i, wallChar[0]);

		labyrinth.set(22, wallChar[0]);
		labyrinth.set(32, wallChar[0]);
		labyrinth.set(42, wallChar[0]);
		labyrinth.set(62, wallChar[0]);
		labyrinth.set(72, wallChar[0]);
		labyrinth.set(82, wallChar[0]);

		labyrinth.set(23, wallChar[0]);
		labyrinth.set(33, wallChar[0]);
		labyrinth.set(43, wallChar[0]);
		labyrinth.set(63, wallChar[0]);
		labyrinth.set(73, wallChar[0]);
		labyrinth.set(83, wallChar[0]);

		labyrinth.set(25, wallChar[0]);
		labyrinth.set(35, wallChar[0]);
		labyrinth.set(45, wallChar[0]);
		labyrinth.set(65, wallChar[0]);
		labyrinth.set(75, wallChar[0]);

		labyrinth.set(27, wallChar[0]);
		labyrinth.set(37, wallChar[0]);
		labyrinth.set(47, wallChar[0]);
		labyrinth.set(57, wallChar[0]);
		labyrinth.set(67, wallChar[0]);
		labyrinth.set(77, wallChar[0]);

		// hero position
		labyrinth.set(posHero, heroChar[0]);

		// dragon position
		labyrinth.set(posDragon, dragonChar[0]);

		// exit position
		labyrinth.set(posExit, exitChar[0]);

		// sword position
		labyrinth.set(posSword, swordChar[0]);
			
		// // upper and lower labyrinth border limits
		// for (int i = 0; i < size; i++)
		// labyrinth.set(i, " X ");
		// for (int i = sizeSquare - size; i < sizeSquare; i++)
		// labyrinth.set(i, " X ");
		//
		// // left and right labyrinth border limits
		// for (int i = size; i < sizeSquare - size; i += size)
		// labyrinth.set(i, " X ");
		// for (int i = size * 2 - 1; i < sizeSquare - size; i += size)
		// labyrinth.set(i, " X ");
		// fully walled grid
		// ArrayList<Integer> fullWallList = new ArrayList<Integer>(); //(size -
		// 2)* (size - 2));
		// int columnCounter = 0, labyrinthIndex = size + 1;
		//
		// while (labyrinthIndex < sizeSquare - size - 2) {
		// while (columnCounter < size - 2) {
		// fullWallList.add(labyrinthIndex);
		// labyrinthIndex++;
		// columnCounter++;
		// }
		// labyrinthIndex += 2;
		// columnCounter = 0;
		// }
		//
		// // start cell
		// Random randomCell = new Random();
		// int randomStartIndex = randomCell.nextInt(insideWallsList.size());
		// int startCell = insideWallsList.get(randomStartIndex);
		//
		// // add start cell to the maze
		// labyrinth.set(startCell, wallChar[0]);
		// insideWallsList.remove(insideWallsList.get(randomStartIndex));
		//
		// // boolean done = false;
		//
		// ArrayList<Integer> neighborCells = new ArrayList<Integer>(4);
		// neighborCells.add(startCell - size); // Up
		// neighborCells.add(startCell + size); // Down
		// neighborCells.add(startCell - 1); // Left
		// neighborCells.add(startCell + 1); // Right
		//
		// int iterations = 0;
		//
		// while (iterations < 400) {
		// // set neighbor cells
		// neighborCells.set(0, startCell - size); // Up
		// neighborCells.set(1, startCell + size); // Down
		// neighborCells.set(2, startCell - 1); // Left
		// neighborCells.set(3, startCell + 1); // Right
		//
		// Random randomNeighborCell = new Random();
		// int randomNeighborCellIndex = randomNeighborCell.nextInt(3);
		// // int neighborCell = neighborCells[randomNeighborCellIndex];
		//
		// switch (randomNeighborCellIndex) {
		//
		// case 0:
		// if (labyrinth.get(neighborCells.get(1)) != wallChar[0]) {
		// labyrinth.set(neighborCells.get(0), wallChar[0]);
		// //insideWallsList.remove(neighborCells.get(0));
		// insideWallsList.remove(neighborCells.get(1));
		// if (insideWallsList.contains(startCell - size))
		// startCell -= size;
		// }
		// break;
		//
		// case 1:
		// if (labyrinth.get(neighborCells.get(0)) != wallChar[0]) {
		// labyrinth.set(neighborCells.get(1), wallChar[0]);
		// insideWallsList.remove(neighborCells.get(0));
		// //insideWallsList.remove(neighborCells.get(1));
		// if (insideWallsList.contains(startCell + size))
		// startCell += size;
		// // remove opposite neighbor from list creating a passage
		// }
		// break;
		// case 2:
		// if (labyrinth.get(neighborCells.get(3)) != wallChar[0]) {
		// labyrinth.set(neighborCells.get(2), wallChar[0]);;
		// //insideWallsList.remove(neighborCells.get(2));
		// insideWallsList.remove(neighborCells.get(3));
		//
		// if (insideWallsList.contains(startCell - 1))
		// startCell -= 1;
		// // remove opposite neighbor from list creating a passage
		// }
		// break;
		// case 3:
		// if (labyrinth.get(neighborCells.get(2)) != wallChar[0]) {
		// labyrinth.set(neighborCells.get(3), wallChar[0]);
		// insideWallsList.remove(neighborCells.get(2));
		// //insideWallsList.remove(neighborCells.get(3));
		// if (insideWallsList.contains(startCell + 1))
		// startCell += 1;
		// }
		// break;
		// default:
		// break;
		// }
		//
		// iterations++;
		// }

		// hero start position
		// labyrinth[posHero] = heroChar[0];
		// dragon start position
		// labyrinth[posDragon] = dragonChar[0];

		// exit start position

		// int[] randomExit = new int[(size - 2) * 4];
		// int j = 0;
		// // upper and lower labyrinth border limits
		// for (int i = 1; i < size - 1; i++, j++)
		// randomExit[j] = i;
		// for (int i = sizeSquare - size + 1; i < sizeSquare - 1; i++, j++)
		// randomExit[j] = i;
		// // left and right labyrinth border limits
		// for (int i = size; i < sizeSquare - size; i += size, j++)
		// randomExit[j] = i;
		// for (int i = size * 2 - 1; i < sizeSquare - size; i += size, j++)
		// randomExit[j] = i;
		//
		// // generate random index value from 0 to randomExit.length
		// Random r = new Random();
		// int randomExitNum = r.nextInt(randomExit.length);
		//
		// posExit = randomExit[randomExitNum];
		// labyrinth[posExit] = exitChar[0];

		// sword start position
		// labyrinth[posSword] = swordChar[0];

		// // hero position
		// labyrinth.set(posHero, heroChar[0]);
		//
		// // dragon position
		// labyrinth.set(posDragon, dragonChar[0]);
		//
		// // exit position
		// labyrinth.set(posExit, exitChar[0]);
		//
		// // sword position
		// labyrinth.set(posSword, swordChar[0]);

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

			if (input.equals(right)
					&& (labyrinth.get(posHero + 1) != wallChar[0])) {

				if ((labyrinth.get(posHero + 1) == " S ")
						&& (heroChar[0] != " A ")) {
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

			} else if (input.equals("a")
					&& (labyrinth.get(posHero - 1) != wallChar[0])) {
				if ((labyrinth.get(posHero - 1) == " S ")
						&& (heroChar[0] == " A ")) {
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
			} else if (input.equals("s")
					&& (labyrinth.get(posHero + size) != wallChar[0])) {

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

			} else if (input.equals("w")
					&& (labyrinth.get(posHero - size) != wallChar[0])) {

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
					|| (labyrinth.get(posHero + size) == labyrinth
							.get(posDragon))
					|| (labyrinth.get(posHero - size) == labyrinth
							.get(posDragon))
					|| (labyrinth.get(posHero + 9) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero + 1) == labyrinth.get(posDragon))
					|| (labyrinth.get(posHero - 9) == labyrinth.get(posDragon)) || ((labyrinth
					.get(posHero - 11) == labyrinth.get(posDragon)))
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
			if (labyrinth.get(posDragon + 1) != wallChar[0]) {
				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword, " E ");
				} else if (posDragon + 1 == posSword) {
					dragonChar[0] = " B ";
					labyrinth.set(posDragon, "   ");
				} else {
					labyrinth.set(posDragon, "   ");
				}
				posDragon++;
			}
			break;
		case 1:
			if (labyrinth.get(posDragon - 1) != wallChar[0]) {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword, " E ");
				} else if (posDragon - 1 == posSword) {
					dragonChar[0] = " B ";
					labyrinth.set(posDragon, "   ");
				} else {
					labyrinth.set(posDragon, "   ");
				}
				posDragon--;
			}
			break;
		case 2:
			if (labyrinth.get(posDragon + size) != wallChar[0]) {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword, " E ");
				} else if (posDragon + size == posSword) {
					dragonChar[0] = " F ";
					labyrinth.set(posDragon, "   ");
				} else {
					labyrinth.set(posDragon, "   ");
				}
				posDragon += size;
			}
			break;
		case 3:
			if (labyrinth.get(posDragon - size) != wallChar[0]) {
				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					labyrinth.set(posSword, " E ");
				} else if (posDragon - size == posSword) {
					dragonChar[0] = " B ";
					labyrinth.set(posDragon, "   ");
				} else {
					labyrinth.set(posDragon, "   ");
				}
				posDragon -= size;
			}
			break;
		default:
			break;
		}
		labyrinth.set(posDragon, dragonChar[0]);

	}

	public static String getkey() {
		System.out.print("(w/a/s/d) to move (q) to abort: ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

}