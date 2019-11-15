package GameInternal;


/**
 * @author James Horner
 *
 */
public class MoveEvent {
	private final MovablePiece piece;
	private final DIRECTION direction;
	private final int numSpaces;
	
	/**
	 * 
	 * @param piece
	 * @param direction
	 * @param numSpaces
	 */
	public MoveEvent(MovablePiece piece, DIRECTION direction, int numSpaces) {
		this.piece = piece;
		this.direction = direction;
		this.numSpaces = numSpaces;
	}
	/**
	 * @return GamePiece for the piece that was moved.
	 */
	public MovablePiece getPiece() {
		return piece;
		
	}
	
	/**
	 * 
	 * @return DIRECTION in which the piece was moved.
	 */
	public DIRECTION getDirection() {
		return direction;
	}
	
	/**
	 * 
	 * @return int for the number of spaces the piece was moved.
	 */
	public int getNumSpaces() {
		return numSpaces;
	}
}
