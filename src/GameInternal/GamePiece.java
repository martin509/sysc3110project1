package GameInternal;

/**
 * Class GamePiece forms the basis from which all pieces are derived.
 * @author James Horner 
 */
public abstract class GamePiece{
    protected boolean movable = false;
    public abstract boolean canBeJumped();
        
    public boolean canBeMoved() {return movable;}
    public GamePiece() {}   
}