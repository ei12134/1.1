package maze;

public enum GameState {

	HERO_WON("hero_won"), HERO_DIED("hero_died"), DRAGON_KILLED("dragon_killed"), CONTINUE_GAME(
			"continue_game");
	private String state;

	private GameState(String state) {
		this.state = state;
	}

	public String toString() {
		return state;
	}
}
