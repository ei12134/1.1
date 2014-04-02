package logic;

public enum State {

	HERO_WON("hero_won"), HERO_DEAD("hero_dead"), DRAGON_DEAD("dragon_dead"), EAGLE_FOLLOWING(
			"eagle_following"), EAGLE_RETURNING("eagle_returning"), EAGLE_PURSUING(
			"eagle_pursuing"), EAGLE_GROUND("eagle_ground"), GAME_CONTINUE("continue_game");

	private String state;

	private State(String state) {
		this.state = state;
	}

	public String toString() {
		return state;
	}
}
