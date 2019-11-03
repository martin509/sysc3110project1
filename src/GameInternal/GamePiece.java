package GameInternal;

/**
 * Class GamePiece forms the basis from which all pieces are derived.
 * @author James Horner 
 */
public abstract class GamePiece{
    protected String ID;
    protected boolean movable = false;
    public abstract boolean canBeJumped();
    public abstract String getID();
        
    public boolean canBeMoved() {return movable;}
    public GamePiece(String ID) {
    	this.ID = ID;
    }
    public boolean equals(GamePiece piece){
        return ID.equals(piece.getID());
    }
}