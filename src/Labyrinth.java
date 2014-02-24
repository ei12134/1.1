import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Labyrinth {
	private int size = 10;
	private int posHero = 11;
	private int posSword = 81;
	private int posDragon = 31;
	private int posExit = 59;

	static ArrayList<ArrayList<Position>> labyrinth = new ArrayList<ArrayList<Position>>();
	public Labyrinth(){}
	
	public void BuildLabyrinth() {
		System.out.print("Enter labyrinth size: ");
		Scanner scan = new Scanner(System.in);
		size = scan.nextInt();

		// fill with spaces
		for (int i = 0; i < size; i++) {
			ArrayList<Position> labyrinthLine = new ArrayList<Position>();
			for (int z = 0; z < size; z++)
				labyrinthLine.add(new Position(i, z, Piece.emptyChar));
			labyrinth.add(labyrinthLine);
		}

		for (int i = 0; i < size; i++) {
			// upper and lower labyrinth border limits
			labyrinth.get(i).get(0).SetId(Piece.wallChar);
			labyrinth.get(i).get(size - 1).SetId(Piece.wallChar);

			// left and right labyrinth border limits
			labyrinth.get(0).get(i).SetId(Piece.wallChar);
			labyrinth.get(size - 1).get(i).SetId(Piece.wallChar);
		}

		labyrinth.get(2).get(2).SetId(Piece.wallChar);
		labyrinth.get(2).get(3).SetId(Piece.wallChar);
		labyrinth.get(2).get(4).SetId(Piece.wallChar);
		labyrinth.get(2).get(6).SetId(Piece.wallChar);
		labyrinth.get(2).get(7).SetId(Piece.wallChar);
		labyrinth.get(2).get(8).SetId(Piece.wallChar);

		labyrinth.get(3).get(2).SetId(Piece.wallChar);
		labyrinth.get(3).get(3).SetId(Piece.wallChar);
		labyrinth.get(3).get(4).SetId(Piece.wallChar);
		labyrinth.get(3).get(6).SetId(Piece.wallChar);
		labyrinth.get(3).get(7).SetId(Piece.wallChar);
		labyrinth.get(3).get(8).SetId(Piece.wallChar);

		labyrinth.get(5).get(2).SetId(Piece.wallChar);
		labyrinth.get(5).get(3).SetId(Piece.wallChar);
		labyrinth.get(5).get(4).SetId(Piece.wallChar);
		labyrinth.get(5).get(6).SetId(Piece.wallChar);
		labyrinth.get(5).get(7).SetId(Piece.wallChar);

		labyrinth.get(7).get(2).SetId(Piece.wallChar); 
		labyrinth.get(7).get(3).SetId(Piece.wallChar);
		labyrinth.get(7).get(4).SetId(Piece.wallChar);
		labyrinth.get(7).get(5).SetId(Piece.wallChar);
		labyrinth.get(7).get(6).SetId(Piece.wallChar);
		labyrinth.get(7).get(7).SetId(Piece.wallChar);

//		 // hero position
		Hero hero = new Hero(new Position(1,1, Piece.heroChar), Status.alive);
		labyrinth.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.heroChar);
		
		labyrinth.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.emptyChar);
		hero.SetPosition(labyrinth.get(1).get(2));
		labyrinth.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.heroChar);
		
		
		//		
//		 // dragon position
//		 labyrinth.set(posDragon, dragonChar[0]);
//		
//		 // exit position
//		 labyrinth.set(posExit, exitChar[0]);
//		
//		 // sword position
//		 labyrinth.set(posSword, swordChar[0]);

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
		// Random randomPosition = new Random();
		// int randomStartIndex = randomPosition.nextInt(insideWallsList.size());
		// int startPosition = insideWallsList.get(randomStartIndex);
		//
		// // add start cell to the maze
		// labyrinth.set(startPosition, Piece.emptyChar);
		// insideWallsList.remove(insideWallsList.get(randomStartIndex));
		//
		// // boolean done = false;
		//
		// ArrayList<Integer> neighborPositions = new ArrayList<Integer>(4);
		// neighborPositions.add(startPosition - size); // Up
		// neighborPositions.add(startPosition + size); // Down
		// neighborPositions.add(startPosition - 1); // Left
		// neighborPositions.add(startPosition + 1); // Right
		//
		// int iterations = 0;
		//
		// while (iterations < 400) {
		// // set neighbor cells
		// neighborPositions.set(0, startPosition - size); // Up
		// neighborPositions.set(1, startPosition + size); // Down
		// neighborPositions.set(2, startPosition - 1); // Left
		// neighborPositions.set(3, startPosition + 1); // Right
		//
		// Random randomNeighborPosition = new Random();
		// int randomNeighborPositionIndex = randomNeighborPosition.nextInt(3);
		// // int neighborPosition = neighborPositions[randomNeighborPositionIndex];
		//
		// switch (randomNeighborPositionIndex) {
		//
		// case 0:
		// if (labyrinth.get(neighborPositions.get(1)) != Piece.emptyChar) {
		// labyrinth.set(neighborPositions.get(0), Piece.emptyChar);
		// //insideWallsList.remove(neighborPositions.get(0));
		// insideWallsList.remove(neighborPositions.get(1));
		// if (insideWallsList.contains(startPosition - size))
		// startPosition -= size;
		// }
		// break;
		//
		// case 1:
		// if (labyrinth.get(neighborPositions.get(0)) != Piece.emptyChar) {
		// labyrinth.set(neighborPositions.get(1), Piece.emptyChar);
		// insideWallsList.remove(neighborPositions.get(0));
		// //insideWallsList.remove(neighborPositions.get(1));
		// if (insideWallsList.contains(startPosition + size))
		// startPosition += size;
		// // remove opposite neighbor from list creating a passage
		// }
		// break;
		// case 2:
		// if (labyrinth.get(neighborPositions.get(3)) != Piece.emptyChar) {
		// labyrinth.set(neighborPositions.get(2), Piece.emptyChar);;
		// //insideWallsList.remove(neighborPositions.get(2));
		// insideWallsList.remove(neighborPositions.get(3));
		//
		// if (insideWallsList.contains(startPosition - 1))
		// startPosition -= 1;
		// // remove opposite neighbor from list creating a passage
		// }
		// break;
		// case 3:
		// if (labyrinth.get(neighborPositions.get(2)) != Piece.emptyChar) {
		// labyrinth.set(neighborPositions.get(3), Piece.emptyChar);
		// insideWallsList.remove(neighborPositions.get(2));
		// //insideWallsList.remove(neighborPositions.get(3));
		// if (insideWallsList.contains(startPosition + 1))
		// startPosition += 1;
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

	public void DisplayLabyrinth() {
		for (int i = 0; i < size; i++) {

			for (int z = 0; z < size; z++) {
				if ((z + 1) % size != 0)
					System.out.print(labyrinth.get(z).get(i).GetId()[0]);
				else
					System.out.println(labyrinth.get(z).get(i).GetId()[0]);
			}
		}
	}

	// public static void HeroMove() {
	//
	// String input = "x";
	// String quit = "q";
	// String message = null;
	// while (!input.equals(quit)) {
	// input = getkey();
	// String right = "d";
	//
	// if (input.equals(right)
	// && (labyrinth.get(posHero + 1) != Piece.emptyChar)) {
	//
	// if ((labyrinth.get(posHero + 1) == " S ")
	// && (heroChar[0] != " A ")) {
	// message = "Sword missing!";
	// } else {
	// if (labyrinth.get(posHero + 1) == " E ") {
	// heroChar[0] = " A ";
	// } else if (labyrinth.get(posHero + 1) == " S ") {
	// input = quit;
	// message = "Level Cleared! Congratulations!";
	// }
	// labyrinth.set(posHero, "   ");
	// labyrinth.set(posHero + 1, heroChar[0]);
	// posHero++;
	//
	// }
	//
	// } else if (input.equals("a")
	// && (labyrinth.get(posHero - 1) != Piece.emptyChar)) {
	// if ((labyrinth.get(posHero - 1) == " S ")
	// && (heroChar[0] == " A ")) {
	// message = "Sword missing!";
	// } else {
	// if (labyrinth.get(posHero - 1) == " E ") {
	//
	// heroChar[0] = " A ";
	// } else if (labyrinth.get(posHero - 1) == " S ") {
	// input = quit;
	// message = "Level Cleared! Congratulations!";
	// }
	//
	// labyrinth.set(posHero, "   ");
	// labyrinth.set(posHero - 1, heroChar[0]);
	// posHero--;
	// }
	//
	// labyrinth.get(posHero + size);
	// } else if (input.equals("s")
	// && (labyrinth.get(posHero + size) != Piece.emptyChar)) {
	//
	// if ((labyrinth.get(posHero + size) == " S ")
	// && (heroChar[0] == " A ")) {
	// message = "Sword missing!";
	// } else {
	// if (labyrinth.get(posHero + size) == " E ") {
	// heroChar[0] = " A ";
	// } else if (labyrinth.get(posHero + size) == " S ") {
	// input = quit;
	// message = "Level Cleared! Congratulations!";
	//
	// }
	// labyrinth.set(posHero, "   ");
	// labyrinth.set(posHero + size, heroChar[0]);
	// posHero += size;
	// }
	//
	// } else if (input.equals("w")
	// && (labyrinth.get(posHero - size) != Piece.emptyChar)) {
	//
	// if ((labyrinth.get(posHero - size) == " S ")
	// && (heroChar[0] == " A ")) {
	// message = "Sword's missing. Go get it first.";
	// } else {
	// if (labyrinth.get(posHero - size) == " E ") {
	// heroChar[0] = " A ";
	// } else if (labyrinth.get(posHero - size) == " S ") {
	// input = quit;
	// message = "Level Cleared! Congratulations!";
	//
	// }
	// labyrinth.set(posHero, "   ");
	// labyrinth.set(posHero - size, heroChar[0]);
	// posHero -= size;
	//
	// }
	// } else if (input.equals("q"))
	// message = "Game aborted.";
	//
	// // check for possible adjacent dragon
	// if (((labyrinth.get(posHero + 1) == labyrinth.get(posDragon))
	// || (labyrinth.get(posHero - 1) == labyrinth.get(posDragon))
	// || (labyrinth.get(posHero + size) == labyrinth
	// .get(posDragon))
	// || (labyrinth.get(posHero - size) == labyrinth
	// .get(posDragon))
	// || (labyrinth.get(posHero + 9) == labyrinth.get(posDragon))
	// || (labyrinth.get(posHero + 1) == labyrinth.get(posDragon))
	// || (labyrinth.get(posHero - 9) == labyrinth.get(posDragon)) ||
	// ((labyrinth
	// .get(posHero - 11) == labyrinth.get(posDragon)))
	// && (dragonChar[0] != "   "))) {
	// if (heroChar[0] == " A ") {
	// dragonChar[0] = "   ";
	// labyrinth.set(posDragon, dragonChar[0]);
	// message = "Dragon slayed!";
	// } else {
	// heroChar[0] = "   ";
	// labyrinth.set(posHero, heroChar[0]);
	// message = "Hero Died. Avoid the dragon if unnarmed.";
	// input = quit;
	// }
	// }
	//
	// DragonMove();
	// DisplayLabyrinth();
	//
	// // prints game messages if any
	// if (message != null) {
	// System.out.println(message);
	// message = null;
	// }
	// }
	// }
	//
	// public static void DragonMove() {
	// // generate random move from 0 to 4
	// Random r = new Random();
	// int randomNum = r.nextInt(4);
	// switch (randomNum) {
	// case 0:
	// if (labyrinth.get(posDragon + 1) != Piece.emptyChar) {
	// if (dragonChar[0] == " F ") {
	// dragonChar[0] = " D ";
	// labyrinth.set(posSword, " E ");
	// } else if (posDragon + 1 == posSword) {
	// dragonChar[0] = " B ";
	// labyrinth.set(posDragon, "   ");
	// } else {
	// labyrinth.set(posDragon, "   ");
	// }
	// posDragon++;
	// }
	// break;
	// case 1:
	// if (labyrinth.get(posDragon - 1) != Piece.emptyChar) {
	//
	// if (dragonChar[0] == " F ") {
	// dragonChar[0] = " D ";
	// labyrinth.set(posSword, " E ");
	// } else if (posDragon - 1 == posSword) {
	// dragonChar[0] = " B ";
	// labyrinth.set(posDragon, "   ");
	// } else {
	// labyrinth.set(posDragon, "   ");
	// }
	// posDragon--;
	// }
	// break;
	// case 2:
	// if (labyrinth.get(posDragon + size) != Piece.emptyChar) {
	//
	// if (dragonChar[0] == " F ") {
	// dragonChar[0] = " D ";
	// labyrinth.set(posSword, " E ");
	// } else if (posDragon + size == posSword) {
	// dragonChar[0] = " F ";
	// labyrinth.set(posDragon, "   ");
	// } else {
	// labyrinth.set(posDragon, "   ");
	// }
	// posDragon += size;
	// }
	// break;
	// case 3:
	// if (labyrinth.get(posDragon - size) != Piece.emptyChar) {
	// if (dragonChar[0] == " F ") {
	// dragonChar[0] = " D ";
	// labyrinth.set(posSword, " E ");
	// } else if (posDragon - size == posSword) {
	// dragonChar[0] = " B ";
	// labyrinth.set(posDragon, "   ");
	// } else {
	// labyrinth.set(posDragon, "   ");
	// }
	// posDragon -= size;
	// }
	// break;
	// default:
	// break;
	// }
	// labyrinth.set(posDragon, dragonChar[0]);
	//
	// }
	//

	public String getkey() {
		System.out.print("(w/a/s/d) to move (q) to abort: ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

}