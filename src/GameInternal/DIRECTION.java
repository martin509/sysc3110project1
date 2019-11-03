package GameInternal;

/**
 * Enum DIRECTION provides a reference for directions on the board.
 * @author James Horner
 */
public enum DIRECTION {
    NORTH,
    SOUTH,
    EAST,
    WEST;
	
	//Each Direction has it's associated opposite.
    private DIRECTION opposite;
    static {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
    }
    //getOppositeDirection returns a directions opposite.
    public DIRECTION getOppositeDirection() {
        return opposite;
    }
}
