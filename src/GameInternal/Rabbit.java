package GameInternal;

/**
 * Class Rabbit represents the piece rabbit in the game.
 * @author James Horner
 */
public class Rabbit extends MovablePiece{
    private boolean inHole = false;
    
    public Rabbit(){
    	super();
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
    
    /**
     * 
     */
	public String toString() {
		return "<Rabbit></Rabbit>";
	}

}