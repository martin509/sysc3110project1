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
public class Rabbit extends MovablePiece{
    private boolean inHole = false;
    
    public Rabbit(String ID, int x, int y){
    	super(ID, x, y);
    }
    
    public boolean isInHole(){
        return inHole;
    }
    
    public void jumpInHole(){
        inHole = true;
        this.movable = false;
    }
    
    @Override
    public boolean canBeJumped() {
        return true;
    }

    @Override
    public String getID() {
        return this.ID;
    }
    
}
