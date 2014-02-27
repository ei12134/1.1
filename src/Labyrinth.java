import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Labyrinth {
	private int size;
	private Hero hero;
	private Dragon dragon;

	static ArrayList<ArrayList<Position>> labyrinth = new ArrayList<ArrayList<Position>>();

	public Labyrinth() {

	}

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

		// // hero position
		hero = new Hero(labyrinth.get(1).get(1), Status.alive);
		labyrinth.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY())
				.SetId(Piece.heroChar);

		// labyrinth.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.emptyChar);
		// hero.SetPosition(labyrinth.get(1).get(2));
		// labyrinth.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.heroChar);

		// dragon position
		dragon = new Dragon(labyrinth.get(1).get(3), Status.alive,
				Piece.dragonChar);
		labyrinth.get(dragon.GetPosition().GetX())
				.get(dragon.GetPosition().GetY()).SetId(Piece.dragonChar);

		// exit position
		labyrinth.get(9).get(5).SetId(Piece.exitChar);

		// sword position
		labyrinth.get(1).get(8).SetId(Piece.swordChar);

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

	public String getkey() {
		System.out.print("(w/a/s/d) to move (q) to abort: ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}

	public void HeroMove() {

		String input = "x";
		String quit = "q";
		String message = null;
		String[] heroPiece = Piece.heroChar;
		while (!input.equals(quit)) {
			input = getkey();
			String right = "d";

			if (input.equals(right)
					&& (labyrinth.get(hero.GetPosition().GetX() + 1)
							.get(hero.GetPosition().GetY()).GetId() != Piece.wallChar)) {

				if ((labyrinth.get(hero.GetPosition().GetX() + 1)
						.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(hero.GetPosition().GetX() + 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (labyrinth.get(hero.GetPosition().GetX() + 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar) {
						input = quit;
						hero.SetStatus(Status.cleared);
						message = "Level Cleared! Congratulations!";
					}
					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetX(hero.GetPosition().GetX() + 1);

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);

				}

			} else if (input.equals("a")
					&& (labyrinth.get(hero.GetPosition().GetX() - 1)
							.get(hero.GetPosition().GetY()).GetId() != Piece.wallChar)) {
				if ((labyrinth.get(hero.GetPosition().GetX() - 1)
						.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(hero.GetPosition().GetX() - 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (labyrinth.get(hero.GetPosition().GetX() - 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar) {
						hero.SetStatus(Status.cleared);
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetX(hero.GetPosition().GetX() - 1);

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);
				}
			} else if (input.equals("s")
					&& (labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() + 1).GetId() != Piece.wallChar)) {
				if ((labyrinth.get(hero.GetPosition().GetX())
						.get(hero.GetPosition().GetY() + 1).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() + 1).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() + 1).GetId() == Piece.exitChar) {
						hero.SetStatus(Status.cleared);
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetY(hero.GetPosition().GetY() + 1);

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);
				}

			} else if (input.equals("w")
					&& (labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() - 1).GetId() != Piece.wallChar)) {
				if ((labyrinth.get(hero.GetPosition().GetX())
						.get(hero.GetPosition().GetY() - 1).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() - 1).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() - 1).GetId() == Piece.exitChar) {
						hero.SetStatus(Status.cleared);
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetY(hero.GetPosition().GetY() - 1);

					labyrinth.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);
				}
			} else if (input.equals("q"))
				message = "Game aborted.";

			// check for possible adjacent dragon
			if ((dragon.GetStatus() == Status.alive || dragon.GetStatus() == Status.guarding)
					&& (hero.GetStatus() != Status.cleared)) {
				if ((labyrinth.get(hero.GetPosition().GetX() + 1)
						.get(hero.GetPosition().GetY()).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX() - 1)
								.get(hero.GetPosition().GetY()).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX())
								.get(hero.GetPosition().GetY() + 1).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX())
								.get(hero.GetPosition().GetY() - 1).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX() - 1)
								.get(hero.GetPosition().GetY() + 1).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX() + 1)
								.get(hero.GetPosition().GetY() + 1).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX() + 1)
								.get(hero.GetPosition().GetY() - 1).GetId() == Piece.dragonChar)
						|| (labyrinth.get(hero.GetPosition().GetX() - 1)
								.get(hero.GetPosition().GetY() - 1).GetId() == Piece.dragonChar)) {
					if (hero.GetStatus() == Status.armed) {
						dragon.SetStatus(Status.dead);
						labyrinth.get(dragon.GetPosition().GetX())
								.get(dragon.GetPosition().GetY())
								.SetId(Piece.emptyChar);
						message = "Dragon slayed!";
					} else {
						hero.SetStatus(Status.dead);
						labyrinth.get(hero.GetPosition().GetX())
								.get(hero.GetPosition().GetY())
								.SetId(Piece.emptyChar);
						message = "Hero Died. Avoid the dragon if unnarmed.";
						input = quit;
					}
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

	public void DragonMove() {

		// generate random move from 0 to 4
		Random r = new Random();
		int randomNum = r.nextInt(4);
		switch (randomNum) {
		case 0:
			if (labyrinth.get(dragon.GetPosition().GetX() + 1)
					.get(dragon.GetPosition().GetY()).GetId() != Piece.wallChar) {
				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX() + 1)
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (labyrinth.get(dragon.GetPosition().GetX() + 1)
						.get(dragon.GetPosition().GetY()).GetId() != Piece.swordChar) {
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetX(dragon.GetPosition().GetX() + 1);
			}
			break;
		case 1:
			if (labyrinth.get(dragon.GetPosition().GetX() - 1)
					.get(dragon.GetPosition().GetY()).GetId() != Piece.wallChar) {
				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX() - 1)
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (labyrinth.get(dragon.GetPosition().GetX() - 1)
						.get(dragon.GetPosition().GetY()).GetId() != Piece.swordChar) {
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetX(dragon.GetPosition().GetX() - 1);
			}
			break;
		case 2:
			if (labyrinth.get(dragon.GetPosition().GetX())
					.get(dragon.GetPosition().GetY() + 1).GetId() != Piece.wallChar) {
				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY() + 1)
							.SetId(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (labyrinth.get(dragon.GetPosition().GetX())
						.get(dragon.GetPosition().GetY() + 1).GetId() != Piece.swordChar) {
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetY(dragon.GetPosition().GetY() + 1);
			}
			break;
		case 3:
			if (labyrinth.get(dragon.GetPosition().GetX())
					.get(dragon.GetPosition().GetY() - 1).GetId() != Piece.wallChar) {
				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY() - 1)
							.SetId(Piece.dragonChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (labyrinth.get(dragon.GetPosition().GetX())
						.get(dragon.GetPosition().GetY() - 1).GetId() != Piece.swordChar) {
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					labyrinth.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetY(dragon.GetPosition().GetY() - 1);
			}
			break;
		default:
			break;
		}
		labyrinth.get(dragon.GetPosition().GetX())
				.get(dragon.GetPosition().GetY()).SetId(dragon.GetPiece());

	}
}