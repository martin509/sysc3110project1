package GameInternal;

import java.awt.Point;

/**
 * @author James Horner
 *
 */
public class MoveEvent {
	private final MovablePiece piece;
	private final Point sourceLocation;
	private final Point destinationLocation;
	
	/**
	 * 
	 * @param piece
	 * @param sourceLocation
	 * @param destinationLocation
	 */
	public MoveEvent(MovablePiece piece, Point sourceLocation, Point destinationLocation) {
		this.piece = piece;
		this.sourceLocation = sourceLocation;
		this.destinationLocation = destinationLocation;
	}
	/**
	 * @return GamePiece for the piece that was moved.
	 */
	public MovablePiece getPiece() {
		return piece;
		
	}
	
	/**
	 * 
	 * @return Point from which the piece was moved.
	 */
	public Point getSourceLocation() {
		return sourceLocation;
	}
	
	/**
	 * 
	 * @return Point for the destination the piece was moved to.
	 */
	public Point getDestinationLocation() {
		return destinationLocation;
	}
	
	public String toString() {
		return "Move " +  piece.toString() + " from " + sourceLocation + " to " + destinationLocation;
	}
}
