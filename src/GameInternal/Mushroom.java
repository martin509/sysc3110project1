package GameInternal;

/**
 * Class Mushroom represents the mushroom piece in the game.
 * @author James Horner
 */
public class Mushroom extends GamePiece {

	public Mushroom() {
		super();
	}

	@Override
	public boolean canBeJumped() {
		return true;
	}

	/**
	 * Method canBeMoved inherited from GamePiece indicates whether a piece can be moved or not.
	 * @return boolean false because mushrooms cannot be moved.
	 */
	@Override
	public boolean canBeMoved() {
		return false;
	}
	
	/**
	 * 
	 */
	public String toString() {
		return "<Mushroom></Mushroom>";
	}

}
