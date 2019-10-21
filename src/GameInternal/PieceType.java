package GameInternal;

public enum PieceType {
	FOX_EW("Fox"),
	FOX_NS("Fox"),
	RABBIT("Rabbit"),
	HILL("Hill"),
	HOLE("Hole"),
	MUSHROOM("Mushroom");
	
	private String pieceName;
	PieceType(String name){
		this.pieceName = name;
	}
	
	public String getName() {
		return pieceName;
	}
}
