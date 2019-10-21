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
class Hole extends ContainerPiece{
   // check ContainerPiece for method descriptions
    public Hole(String ID, int x, int y){
    	super(ID, x, y);
    }
    
    @Override
    public boolean canEnter(){
        if(contains==null){
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public boolean putIn(GamePiece piece){
        if(contains==null){
            contains = piece;
            return true;
        }else{
            return false;
        }
    }
    
    @Override
    public GamePiece takeOut(){
        GamePiece tempContains = contains;
        contains = null;
        return tempContains;
    }
    
    @Override
    public GamePiece check(){
        return contains;
    }
        
    
    @Override
    public boolean canBeJumped() {
        if(contains == null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    public boolean canBeMoved() {
        return false;
    }
    
}
