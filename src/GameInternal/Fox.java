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
class Fox extends MovablePiece{
    private FoxBit head;
    private DIRECTION axis;
    
    
    public Fox(String ID, int length, DIRECTION axis){
    	super();
        this.ID = ID;
        this.axis = axis;
        if(length > 0){
            head = new FoxBit(this, this.getID() + "_bit0");
            FoxBit tempTail = head;
            for(int i=length-1; i>0; i--){
                FoxBit newTail = new FoxBit(this, this.getID() + "_bit" + i);
                newTail.setAhead(tempTail);
                tempTail.setBehind(newTail);
                tempTail = newTail;
            }
        }
    }
    
    public DIRECTION getAxis(){
        return axis;
    }
    
    public FoxBit getHead(){
        return head;
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
