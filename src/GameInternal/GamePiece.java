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
public abstract class GamePiece{
    protected String ID;
    protected boolean movable = false;
    public abstract boolean canBeJumped();
    public abstract String getID();
        
    public boolean canBeMoved() {return movable; }
    public GamePiece(String ID) {
    	this.ID = ID;
    }
    public boolean equals(GamePiece piece){
        return ID.equals(piece.getID());
    }
}
