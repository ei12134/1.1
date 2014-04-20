package logic;

public enum PieceType {

	FREE("   "), WALL(" X "), EXIT(" S "), HERO_UNARMED(" H "), HERO_ARMED(
			" A "), HERO_UNARMED_EAGLE(" HG"), HERO_ARMED_EAGLE(" AG"), SWORD(
			" E "), RETURNING_DRAGON_ASLEEP_EAGLE("EDG"), RETURNING_DRAGON_EAGLE(
			"EDG"), RETURNING_WALL_EAGLE("EXG"), GROUND_EAGLE("E G"), PURSUING_EAGLE(
			"  G"), PURSUING_DRAGON_EAGLE(" DG"), PURSUING_DRAGON_ASLEEP_EAGLE(
			" DG"), PURSUING_WALL_EAGLE(" XG"), DRAGON(" D "), DRAGON_GUARDING(
			" F "), DRAGON_ASLEEP(" d "), DRAGON_GUARDING_ASLEEP(" f ");

	private final String asString;

	//Return the type of the Piece as a string
	public String asString() {
		return asString;
	}

	
	private PieceType(String asString) {
		this.asString = asString;
	}
}
