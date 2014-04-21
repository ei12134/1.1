package logic;

/**
 * Contains the states of all characters
 * (Hero, Dragon, Eagle)
 */
public enum State {

	HERO_WON("Hero won :)"), HERO_DEAD("Hero died!"), DRAGON_DEAD("Dragon died"), EAGLE_DEAD(
			"Eagle died!"), EAGLE_FOLLOWING("eagle_following"), EAGLE_RETURNING(
			"eagle_returning"), EAGLE_PURSUING("eagle_pursuing"), EAGLE_GROUND(
			"eagle_ground");

	private String state;

	private State(String state) {
		this.state = state;
	}
	

	//Returns the state as a String object
	public String toString() {
		return state;
	}
}
