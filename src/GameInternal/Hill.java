package GameInternal;

/**
 * Class Hill is used to represent pieces rabbits can jump onto but foxes cannot move onto.
 * @author James Horner
 */
public class Hill extends ContainerPiece {

	// check ContainerPiece for method descriptions
	public Hill(String ID) {
		super(ID);
	}

	@Override
	public boolean canEnter() {
		if (contains == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean putIn(GamePiece piece) {
		if (contains == null) {
			contains = piece;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public GamePiece takeOut() {
		GamePiece tempContains = contains;
		contains = null;
		return tempContains;
	}

	@Override
	public GamePiece check() {
		return contains;
	}

	@Override
	public boolean canBeJumped() {
		if (contains == null) {
			return false;
		} else {
			return contains.canBeJumped();
		}
	}

	@Override
	public String getID() {
		return this.ID;
	}

}
