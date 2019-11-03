package GameInternal;

/**
 * Class Rabbit represents the piece rabbit in the game.
 * @author James Horner
 */
public class Rabbit extends MovablePiece{
    private boolean inHole = false;
    
    public Rabbit(String ID){
    	super(ID);
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

    @Override
    public String getID() {
        return this.ID;
    }
    
}