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
class FoxBit extends GamePiece{
	
    private Fox partOfFox;
    private FoxBit ahead;
    private FoxBit behind;

    public FoxBit(Fox fox, String ID, int x, int y){
    	super(ID, x, y);
    	this.movable = true;
        partOfFox = fox;
    }

    public void setAhead(FoxBit ahead) {
        this.ahead = ahead;
    }

    public void setBehind(FoxBit behind) {
        this.behind = behind;
    }
        
    public Fox getFox() {
        return partOfFox;
    }

    public FoxBit getAhead() {
        return ahead;
    }

    public FoxBit getBehind() {
        return behind;
    }
    
    public FoxBit getHead(){
        FoxBit tempHead = this;
        while(tempHead.getAhead()!=null){
            tempHead = tempHead.getAhead();
        }
        return tempHead;
    }
    
    public FoxBit getTail(){
        FoxBit tempTail = this;
        while(tempTail.getBehind()!=null){
            tempTail = tempTail.getBehind();
        }
        return tempTail;

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
