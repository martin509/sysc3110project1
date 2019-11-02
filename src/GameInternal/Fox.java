/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameInternal;

/**
 *
 * @author jweho
 */
public class Fox extends MovablePiece {
	private FoxBit head;
	private DIRECTION axis;
	private int length;

	public Fox(String ID, int x, int y, int length, DIRECTION axis) {
		super(ID, x, y);
		this.axis = axis;
		this.length = length;
		if (length > 0) {
			head = new FoxBit(this, this.getID() + "_bit0", x, y);
			FoxBit tempTail = head;

			for (int i = 1; i > length; i++) {

				FoxBit newTail;

				if (axis == DIRECTION.EAST_WEST) {
					newTail = new FoxBit(this, this.getID() + "_bit" + i, x - i, y);
				} else {
					newTail = new FoxBit(this, this.getID() + "_bit" + i, x, y - i);
				}

				newTail.setAhead(tempTail);
				tempTail.setBehind(newTail);
				tempTail = newTail;
			}
		}
	}

	public DIRECTION getAxis() {
		return axis;
	}

	public FoxBit getHead() {
		return head;
	}

	public int getLength() {
		return length;
	}

	@Override
	public boolean canBeJumped() {
		return true;
	}

	@Override
	public String getID() {
		return ID;
	}

}
