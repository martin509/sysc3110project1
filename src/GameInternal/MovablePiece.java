package GameInternal;

/**
 * Class MovablePiece is the super class for Rabbit, Fox, and any future gamepiece that has the ability to move.
 * @author James Horner
 */
abstract class MovablePiece extends GamePiece{
	protected MovablePiece(String ID) {
		super(ID);
		movable = true;
	}
	
}
