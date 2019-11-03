package GameInternal;

/**
 * Class Hole represents the holes that rabbits are suppose to jump into in the game.
 * @author James Horner
 */
public class Hole extends ContainerPiece {
	
	private GamePiece contains;
	// check ContainerPiece for method descriptions
	public Hole(String ID) {
		super(ID);
		contains = null;
	}
	
	/**
	 * Method canEnter indicates whether it is possible for a piece to enter the hole based on it's contents.
	 * @return boolean true if hole is empty, false otherwise.
	 */
	@Override
	public boolean canEnter() {
		if (contains == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method puIn is used to add a piece to a hole and return whether it was successful.
	 * @return boolean true is piece was successfully added, false otherwise.
	 */
	@Override
	public boolean putIn(GamePiece piece) {
		if (contains == null) {
			contains = piece;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method takeOut removes the GamePice from a hole and returns it to the client.
	 * @return GamePiece for piece that was in the hole, null if hole was empty.
	 */
	@Override
	public GamePiece takeOut() {
		GamePiece tempContains = contains;
		contains = null;
		return tempContains;
	}
	
	/**
	 * Method check checks if the hole contains a GamePiece.
	 * @return GamePiece item that the hole contains, null is hole is empty.
	 */
	@Override
	public GamePiece check() {
		return contains;
	}
	/**
	 * Method canBeJumped from GamePiece is used to check if the piece can be jumped in its current state.
	 * @return boolean true is hole can be jumped, false otherwise.
	 */
	@Override
	public boolean canBeJumped() {
		if (contains == null) {//if hole is empty it cannot be jumped.
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getID() {
		return this.ID;
	}

	@Override
	public boolean canBeMoved() {
		return false;
	}

}
