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
   
    public Hole(String ID){
        this.ID = ID;
    }
    @Override
    public boolean enter(GamePiece piece){
        if(contains == null){
            contains = piece;
            return true;
        }
        else{
            return false;
        }
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
