package GameInternal;

/**
 * Class Fox represents the Fox piece in the game and is a collection of FoxBits that actually get put onto the board.
 * @author James Horner
 */
public class Fox extends MovablePiece {

    private FoxBit head;
    private final DIRECTION axisForward;
    private final DIRECTION axisBackward;
    private final int length;

    public Fox(int length, DIRECTION axisForward) {
        super();
        this.axisForward = axisForward;
        this.axisBackward = axisForward.getOppositeDirection();
        this.length = length;
        
        //Based on the value of length the fox is initialized with FoxBits.
        if (length > 0) {
            head = new FoxBit(this);//start with the head
            FoxBit tempTail = head;
            for (int i = 1; i < length; i++) {//for the length of the fox
                FoxBit newTail = new FoxBit(this);//make a new FoxBit
                newTail.setAhead(tempTail);//set it to be behind the previous one
                tempTail.setBehind(newTail);//set the previous one to be ahead of the new one
                tempTail = newTail;//make the new one the old one and repeat
            }
        }
    }
    /**
     * Method returns what direction is forward for the fox.
     * @return DIRECTION for the forward direction of the fox.
     */
    public DIRECTION getAxisForward() {
        return axisForward;
    }
    /**
     * Method returns what direction is backward for the fox.
     * @return DIRECTION for the backward direction of the fox.
     */
    public DIRECTION getAxisBackward() {
        return axisBackward;
    }
    
    /**
     * Method returns the head of the fox.
     * @return FoxBit that is the head of the fox.
     */
    public FoxBit getHead() {
        return head;
    }
    /**
     * Method returns the length of the fox.
     * @return int length of the fox.
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Method canBeJumped inherited from GamePiece
     * @return true because a fox can always be jumped.
     */
    @Override
    public boolean canBeJumped() {
        return true;
    }
    
    /**
     * 
     */
    public String toString() {
    	return "<Fox, axisForward=" + axisForward + ", length=" + length + "></Fox>";
    }

}
