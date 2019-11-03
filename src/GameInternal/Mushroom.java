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
public class Mushroom extends GamePiece {

	public Mushroom(String ID) {
		super(ID);
	}

	@Override
	public boolean canBeJumped() {
		return true;
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
