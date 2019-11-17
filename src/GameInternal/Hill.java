package GameInternal;

/**
 * Class Hill is used to represent pieces rabbits can jump onto but foxes cannot move onto.
 * @author James Horner
 */
public class Hill extends ContainerPiece {

	// check ContainerPiece for method descriptions
	public Hill() {
		super();
	}

	@Override
	public boolean isEmpty() {
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
	
	/**
	 * 
	 */
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("<Hill>");
		if(!isEmpty()) {
			ret.append(contains.toString());
		}
		ret.append("</Hill>");
		return ret.toString();
	}
}
