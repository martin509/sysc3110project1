package GameInternal;

/**
 * Class Mushroom represents the mushroom piece in the game.
 * @author James Horner
 */
public class Mushroom extends GamePiece {

	public Mushroom(String ID) {
		super(ID);
	}

	@Override
	public boolean canBeJumped() {
		return true;
	}

	@Override
	public String getID() {
		return this.ID;
	}

	/**
	 * Method canBeMoved inherited from GamePiece indicates whether a piece can be moved or not.
	 * @return boolean false because mushrooms cannot be moved.
	 */
	@Override
	public boolean canBeMoved() {
		return false;
	}

}
