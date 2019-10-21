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
abstract class GamePiece{
    protected String ID;
    protected boolean movable = false;
    public abstract boolean canBeJumped();
    public abstract String getID();
    
    protected int x;
    protected int y;
    public int getX(){return x; }
    public int getY(){return y; }
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    
    public boolean canBeMoved() {return movable; }
    public GamePiece(String ID, int x, int y) {
    	this.ID = ID;
        this.x = x;
        this.y = y;
    }
}
