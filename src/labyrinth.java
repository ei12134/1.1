import java.util.Scanner;
import java.util.Random;

public class labyrinth {

	private static int posHeroe = 11;
	private static int posSword = 81;
	private static int posDragon = 31;
	private static int posExit = 59;
	static String[] heroeChar = { " H " };
	static String[] dragonChar = { " D " };
	static String[] exitChar = { " S " };
	static String[] swordChar = { " E " };
	static String[] labyrinth = new String[100];

	public static void main(String[] args) {

		// Preenche com espacos
		for (int i = 0; i < 100; i++)
			labyrinth[i] = "   ";

		// Parede
		// Linhas superior e inferiror
		for (int i = 0; i < 10; i++)
			labyrinth[i] = " X ";
		for (int i = 90; i < 100; i++)
			labyrinth[i] = " X ";

		// Colunas esquerda direita
		for (int i = 10; i < 90; i += 10)
			labyrinth[i] = " X ";
		for (int i = 19; i < 90; i += 10)
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

		// Posição do Heroi
		// int posHeroe=11;
		labyrinth[posHeroe] = heroeChar[0];

		// Posicao do dragao
		labyrinth[posDragon] = dragonChar[0];

		// Posição da Saida
		labyrinth[posExit] = exitChar[0];

		// Posicao da Espada
		labyrinth[posSword] = swordChar[0];

		DisplayLabyrinth();
		HeroeMove();

	}

	public static void SetPosition(int pos) {
		labyrinth[pos] = " H ";

	}

	public static void DisplayLabyrinth() {

		for (int i = 0; i < labyrinth.length; i++) {
			if ((i + 1) % 10 != 0)
				System.out.print(labyrinth[i]);
			else
				System.out.println(labyrinth[i]);
		}
	}

	public static void HeroeMove() { // / heroi tem de ser uma classe

		String input = "x";
		String quit = "q";
		String message = null;
		while (!input.equals(quit)) {
			input = getkey();
			String right = "d";
			if (input.equals(right) && (labyrinth[posHeroe + 1] != " X ")) {

				if ((labyrinth[posHeroe + 1] == " S ")
						&& (heroeChar[0] != " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHeroe + 1] == " E ") {
						heroeChar[0] = " A ";
					} else if (labyrinth[posHeroe + 1] == " S ") {
						input = quit;
						message = "Level Cleared!";
					}
					labyrinth[posHeroe] = "   ";
					labyrinth[posHeroe + 1] = heroeChar[0];
					posHeroe++;

				}

			} else if (input.equals("a") && (labyrinth[posHeroe - 1] != " X ")) {
				if ((labyrinth[posHeroe - 1] == " S ")
						&& (heroeChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHeroe - 1] == " E ") {

						heroeChar[0] = " A ";
					} else if (labyrinth[posHeroe - 1] == " S ") {
						input = quit;
						message = "Level Cleared!";
					}

					labyrinth[posHeroe] = "   ";
					labyrinth[posHeroe - 1] = heroeChar[0];
					posHeroe--;
				}

			} else if (input.equals("s") && (labyrinth[posHeroe + 10] != " X ")) {

				if ((labyrinth[posHeroe + 10] == " S ")
						&& (heroeChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHeroe + 10] == " E ") {
						heroeChar[0] = " A ";
					} else if (labyrinth[posHeroe + 10] == " S ") {
						input = quit;
						message = "Level Cleared!";

					}
					labyrinth[posHeroe] = "   ";
					labyrinth[posHeroe + 10] = heroeChar[0];
					posHeroe += 10;
				}

			} else if (input.equals("w") && (labyrinth[posHeroe - 10] != " X ")) {

				if ((labyrinth[posHeroe - 10] == " S ")
						&& (heroeChar[0] == " A ")) {
					message = "Sword missing!";
				} else {
					if (labyrinth[posHeroe - 10] == " E ") {
						heroeChar[0] = " A ";
					} else if (labyrinth[posHeroe - 10] == " S ") {
						input = quit;
						message = "Level Cleared!";

					}
					labyrinth[posHeroe] = "   ";
					labyrinth[posHeroe - 10] = heroeChar[0];
					posHeroe -= 10;

				}
			}
			if (((labyrinth[posHeroe + 1] == labyrinth[posDragon])
					|| (labyrinth[posHeroe - 1] == labyrinth[posDragon])
					|| (labyrinth[posHeroe + 10] == labyrinth[posDragon])
					|| (labyrinth[posHeroe - 10] == labyrinth[posDragon])
					|| (labyrinth[posHeroe + 9] == labyrinth[posDragon])
					|| (labyrinth[posHeroe + 11] == labyrinth[posDragon])
					|| (labyrinth[posHeroe - 9] == labyrinth[posDragon]) || (labyrinth[posHeroe - 11] == labyrinth[posDragon]))
					&& (dragonChar[0] != "   ")) {
				if (heroeChar[0] == " A ") {

					dragonChar[0] = "   ";
					labyrinth[posDragon] = dragonChar[0];
					message = "Dragon Slayed";
				} else {

					heroeChar[0] = "   ";
					labyrinth[posHeroe] = heroeChar[0];
					message = "Heroe Died";
					input = quit;
				}
			}

			DragonMove();
			DisplayLabyrinth();

			if (message != null) {
				System.out.println(message);
				message = null;
			}
		}
	}

	public static void DragonMove() {
		Random r = new Random();
		int num = r.nextInt(4);
		switch (4) {
		case 0:
			if (labyrinth[posDragon + 1] != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					posDragon++;
					labyrinth[posSword] = " E ";
				}

				if (labyrinth[posDragon + 1] == " E ") {
					dragonChar[0] = " F ";
					posDragon++;
					labyrinth[posDragon] = "   ";
				}
			}
		case 1:
			if (labyrinth[posDragon - 1] != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					posDragon--;
					labyrinth[posSword] = " E ";
				}

				if (labyrinth[posDragon - 1] == " E ") {
					dragonChar[0] = " F ";
					posDragon--;
					labyrinth[posDragon] = "   ";
				}
			}
		case 2:
			if (labyrinth[posDragon + 10] != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					posDragon += 10;
					labyrinth[posSword] = " E ";
				} else if (labyrinth[posDragon + 10] == " E ") {
					labyrinth[posSword] = "   ";
					dragonChar[0] = " F ";
					labyrinth[posDragon + 10] = dragonChar[0];
				}

				labyrinth[posDragon] = "   ";
				posDragon += 10;
				labyrinth[posDragon] = dragonChar[0];

			}
		case 3:
			if (labyrinth[posDragon - 10] != " X ") {

				if (dragonChar[0] == " F ") {
					dragonChar[0] = " D ";
					posDragon -= 10;
					labyrinth[posSword] = " E ";
				}

				if (labyrinth[posDragon - 10] == " E ") {
					dragonChar[0] = " F ";
					posDragon -= 10;
					labyrinth[posDragon] = "   ";
				}
			}
		}
		// labyrinth[posDragon] = dragonChar[0];

	}

	public static String getkey() {
		String input = null;
		System.out.print("w/a/s/d?: ");

		Scanner scan = new Scanner(System.in);
		String a = scan.nextLine();
		// input=sc.nextLine();
		return a;
	}

}