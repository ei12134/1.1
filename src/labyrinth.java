import java.io.InputStream;
import java.util.Scanner;

public class labyrinth {

	// public static void main(String[] args){
	// Scanner sc = new Scanner(System.in);
	// String input = sc.nextLine();
	// System.out.println(input);
	// }

	private static int posheroi = 11;
	private static int posespada = 81;
	static String[] heroeChar = { " H " };
	static String[] labirinto = new String[100]; // Static char a[][]=....

	public static void main(String[] args) {

		// Preenche com espacos
		for (int i = 0; i < 100; i++)
			labirinto[i] = "   ";

		// Parede
		// Linhas superior e inferiror
		for (int i = 0; i < 10; i++)
			labirinto[i] = " X ";
		for (int i = 90; i < 100; i++)
			labirinto[i] = " X ";

		// Colunas esquerda direita
		for (int i = 10; i < 90; i += 10)
			labirinto[i] = " X ";
		for (int i = 19; i < 90; i += 10)
			labirinto[i] = " X ";

		labirinto[22] = " X ";
		labirinto[32] = " X ";
		labirinto[42] = " X ";
		labirinto[62] = " X ";
		labirinto[72] = " X ";
		labirinto[82] = " X ";

		labirinto[23] = " X ";
		labirinto[33] = " X ";
		labirinto[43] = " X ";
		labirinto[63] = " X ";
		labirinto[73] = " X ";
		labirinto[83] = " X ";

		labirinto[25] = " X ";
		labirinto[35] = " X ";
		labirinto[45] = " X ";
		labirinto[65] = " X ";
		labirinto[75] = " X ";

		labirinto[27] = " X ";
		labirinto[37] = " X ";
		labirinto[47] = " X ";
		labirinto[57] = " X ";
		labirinto[67] = " X ";
		labirinto[77] = " X ";

		// Posição do Heroi
		// int posheroi=11;
		labirinto[posheroi] = " H ";

		// Posição da Saida
		labirinto[59] = " S ";

		// Posicao da Espada
		labirinto[posespada] = " E ";

		DisplayLabyrinth();
		HeroeMove();
		DisplayLabyrinth();
	}

	public static void SetPosition(int pos) {
		labirinto[pos] = " H ";

	}

	public static void DisplayLabyrinth() {

		for (int i = 0; i < labirinto.length; i++) {
			if ((i + 1) % 10 != 0)
				System.out.print(labirinto[i]);
			else
				System.out.println(labirinto[i]);
		}
	}

	public static void HeroeMove() { // / heroi tem de ser uma classe

		String input = "x";
		String quit = "q";
		while (!input.equals(quit)) {
			input = getkey();
			String right = "d";
			if (input.equals(right) && (labirinto[posheroi + 1] != " X ")) {

				if ((labirinto[posheroi + 1] == " S ")
						&& (heroeChar[0] != " A ")) {
					System.out.println("Sword missing!");

				} else {
					if (labirinto[posheroi + 1] == " E ") {
						heroeChar[0] = " A ";
					} else if (labirinto[posheroi + 1] == " S ") {
						input = quit;
					}

					labirinto[posheroi] = "   ";
					labirinto[posheroi + 1] = heroeChar[0];
					posheroi++;
				}

			} else if (input.equals("a") && (labirinto[posheroi - 1] != " X ")) {
				if ((labirinto[posheroi - 1] == " S ")
						&& (heroeChar[0] == " A ")) {
					System.out.println("Sword missing!");
				} else {
					if (labirinto[posheroi - 1] == " E ") {

						heroeChar[0] = " A ";
					} else if (labirinto[posheroi - 1] == " S ") {
						input = quit;
					}

					labirinto[posheroi] = "   ";
					labirinto[posheroi - 1] = heroeChar[0];
					posheroi--;
				}

			} else if (input.equals("s") && (labirinto[posheroi + 10] != " X ")) {

				if ((labirinto[posheroi + 10] == " S ")
						&& (heroeChar[0] == " A ")) {
					System.out.println("Sword missing!");
				} else {
					if (labirinto[posheroi + 10] == " E ") {
						heroeChar[0] = " A ";
					} else if (labirinto[posheroi + 10] == " S ") {
						input = quit;

					}
					labirinto[posheroi] = "   ";
					labirinto[posheroi + 10] = heroeChar[0];
					posheroi += 10;
				}

			} else if (input.equals("w") && (labirinto[posheroi - 10] != " X ")) {

				if ((labirinto[posheroi - 10] == " S ")
						&& (heroeChar[0] == " A ")) {
					System.out.println("Sword missing!");
				} else {
					if (labirinto[posheroi - 10] == " E ") {
						heroeChar[0] = " A ";
					} else if (labirinto[posheroi - 10] == " S ") {
						input = quit;

					}
					labirinto[posheroi] = "   ";
					labirinto[posheroi - 10] = heroeChar[0];
					posheroi -= 10;

				}
			}
			DisplayLabyrinth();

		}
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