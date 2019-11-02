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
abstract class ContainerPiece extends GamePiece {
    protected GamePiece contains;
    public abstract boolean canEnter(); // can a piece be placed inside?
    public abstract boolean putIn(GamePiece piece); //putting a piece in
    public abstract GamePiece takeOut(); // taking a piece out
    public abstract GamePiece check(); // what does this contain?
    ContainerPiece(String ID){
    	super(ID);
    }
}
