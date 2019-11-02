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
public enum DIRECTION {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    private DIRECTION opposite;
    static {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
    }

    public DIRECTION getOppositeDirection() {
        return opposite;
    }
}
