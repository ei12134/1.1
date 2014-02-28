import java.util.Scanner;


public class Builder {
	
	public Builder() {
	}
	
	public void BuildLabyrinth(){
		int size;
		Labyrinth game = new Labyrinth();
		System.out.print("Enter labyrinth size (0 standard maze): ");
		Scanner scan = new Scanner(System.in);
		size = scan.nextInt();
		
		if (size == 0)
			game.StandardLabyrinth();
	}
}
