package GameInternal;

/**
 * Class FoxBit is used to represent a Fox on the game board that spans multiple pieces
 * @author James Horner
 */
public class FoxBit extends GamePiece {

	private Fox partOfFox;
	private FoxBit ahead;
	private FoxBit behind;

	public FoxBit(Fox fox) {
		super();
		this.movable = true;
		partOfFox = fox;
	}
	
	/**
	 * Method setAhead is used to set which FoxBit lies in front of this FoxBit.
	 * @param ahead FoxBit that is ahead of this FoxBit.
	 */
	public void setAhead(FoxBit ahead) {
		this.ahead = ahead;
	}
	
	/**
	 * Method setBehind is used to set which FoxBit lies behind of this FoxBit.
	 * @param ahead FoxBit that is behind of this FoxBit.
	 */
	public void setBehind(FoxBit behind) {
		this.behind = behind;
	}
	
	/**
	 * Method getFox is used to find out what Fox Object this FocBit belongs to.
	 * @return Fox that this FoxBit is a part of.
	 */
	public Fox getFox() {
		return partOfFox;
	}

	/**
	 * Method getAhead is used to find out which FoxBit lies in front of this FoxBit.
	 * @returns FoxBit that is ahead of this FoxBit.
	 */
	public FoxBit getAhead() {
		return ahead;
	}
	
	/**
	 * Method getBehind is used to find out which FoxBit lies behind this FoxBit.
	 * @returns FoxBit that is behind this FoxBit.
	 */
	public FoxBit getBehind() {
		return behind;
	}
	
	/**
	 * Method getHead is used to find the head FoxBit of the Fox.
	 * @returns FoxBit that is the head of this FoxBits Fox.
	 */
	public FoxBit getHead() {
		FoxBit tempHead = this;
		while (tempHead.getAhead() != null) {//iterate through the FoxBits working forward until the ahead is null.
			tempHead = tempHead.getAhead();
		}
		return tempHead;//return the head.
	}
	
	/**
	 * Method getTail is used to find the tail FoxBit of the Fox.
	 * @returns FoxBit that is the tail of this FoxBits Fox.
	 */
	public FoxBit getTail() {
		FoxBit tempTail = this;
		while (tempTail.getBehind() != null) {//iterate through the FoxBits working backward until the behind is null.
			tempTail = tempTail.getBehind();
		}
		return tempTail;

	}
	
	/**
	 * Method canBeJumped inherited from GamePiece used for determining if a piece can be jumped.
	 * @return boolean true because a FoxBit can always be jumped.
	 */
	@Override
	public boolean canBeJumped() {
		return true;
	}
	
	public String toString() {
		StringBuffer ret = new StringBuffer();
		ret.append("<FoxBit, parOfFox=" + partOfFox.toString() + "<FoxBit>");
		return ret.toString();
	}
}
