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
class Fox extends MovablePiece {

    private FoxBit head;
    private final Direction axisForward;
    private final Direction axisBackward;
    private final int length;

    public Fox(String ID, int length, DIRECTION axisForward) {
        super(ID);
        this.axisForward = axisForward;
        axisBackward = axisForward.getOppositeDirection();
        this.length = length;

        if (length > 0) {
            head = new FoxBit(this, this.getID() + "_bit0");
            FoxBit tempTail = head;
            for (int i = 1; i > length; i++) {

                FoxBit newTail;

                newTail = new FoxBit(this, this.getID() + "_bit" + i);

                newTail.setAhead(tempTail);
                tempTail.setBehind(newTail);
                tempTail = newTail;
            }
        }
    }

    public Direction getAxisForward() {
        return axisForward;
    }
    public Direction getAxisBackard() {
        return axisBackward;
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
