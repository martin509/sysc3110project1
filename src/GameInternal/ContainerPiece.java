package GameInternal;

/**
 * Class ContainerPiece functions as super class for hole, hill and any other future piece that could contain another piece.
 * @author James Horner
 */
public abstract class ContainerPiece extends GamePiece {
    protected GamePiece contains;
    public abstract boolean isEmpty(); // can a piece be placed inside?
    public abstract boolean putIn(GamePiece piece); //putting a piece in
    public abstract GamePiece takeOut(); // taking a piece out
    public abstract GamePiece check(); // what does this contain?
    ContainerPiece(){
    	super();
    }
}
