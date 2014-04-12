package logic;

public enum PieceType {

	FREE("   "), WALL(" X "), EXIT(" S "), HERO_UNARMED(" H "), HERO_ARMED(
			" A "), HERO_UNARMED_EAGLE(" HG"), HERO_ARMED_EAGLE(" AG"), SWORD(
			" E "), GROUND_EAGLE("E G"), PURSUING_EAGLE("  G"), PURSUING_WALL_EAGLE(
			" XG"), DRAGON(" D "), DRAGON_GUARDING(" F "), DRAGON_ASLEEP(" d "), DRAGON_GUARDING_ASLEEP(
			" f ");

	private final String asString;

	public String asString() {
		return asString;
	}

	private PieceType(String asString) {
		this.asString = asString;
	}
}
