package logic;

public enum PieceType {

	FREE("   "), WALL(" X "), EXIT(" S "), HERO(" HG"), HERO_ARMED(" AG"), SWORD(
			" E "), GROUND_EAGLE("E G"), PURSUING_EAGLE("  G"), DRAGON(" D "), DRAGON_GUARDING(
			" F "), DRAGON_ASLEEP(" d "), DRAGON_ASLEEP_GUARDING(" f ");

	private final String asString;

	public String asString() {
		return asString;
	}

	private PieceType(String asString) {
		this.asString = asString;
	}
}
