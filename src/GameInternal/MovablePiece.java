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
abstract class MovablePiece extends GamePiece{
	protected MovablePiece(String ID) {
		super(ID);
		movable = true;
	}
}
