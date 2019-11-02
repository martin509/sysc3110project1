package GameInternal;

import java.util.ArrayList;

public class Game {


	private ArrayList<String> idList;
        private Board board;
	/**
	 * Checks for a win state in the game.
	 * @return whether or not all rabbits are in holes. Defaults to true if there are no rabbits.
	 */
	public boolean isGameWon() {
		ArrayList<GamePiece> rabbits = board.getPiecesOfType(new Rabbit(""));
		for(GamePiece r: rabbits) {
			if(!((Rabbit)r).isInHole()) {
				return false;
			}
		}
		return true;
	}
	
	/**
     * Checks to see if you can move a piece to a desired location
     * @param piece this is the piece that wants to be moved
     * @param x x coordinate of the final location
     * @param y y coordinate of the final location
     * @return if the piece can move to the given location
     */
	
	private boolean canRabbitMove(GamePiece piece, int x, int y, int currPos, int finalPos, boolean isVert) {
		// for creating the while loop
		int i;
        int j;
        if(currPos < finalPos){
            i = currPos;
            j = finalPos;
        } else {
            i = finalPos;
            j = currPos;
        }

        // the finalPos and the currPos must have a difference larger then 1
        // remember j is the larger value
        if(j - i < 2){
            return false;
        }

        // goes over each space between the rabbit and the chosen destination
        while(i <= j){
            // check the space the rabbit is landing on
            if(finalPos == i){
                if(getObjectAt(x, y) instanceof ContainerPiece){
                    if(!((ContainerPiece)getObjectAt(x, y)).canEnter()){
                        return false; // if the hole/hill is full
                    }

                } else if (getObjectAt(x, y) == null){
                    // this means the space is empty
                } else {
                    return false; // this means the space is occupied
                }
            } else if(currPos != i){ // it doesn't need to check its own space
                if(isVert){
                    if(!(getObjectAt(x, i).canBeJumped()) || getObjectAt(x, i) == null){
                        return false;
                    }
                } else {
                    if(!(getObjectAt(i, y).canBeJumped()) || getObjectAt(i, y) == null){
                        return false;
                    }
                }
            }
            i ++;
        }

        return true;
	}

	private boolean canFoxMove(GamePiece piece, int x, int y, int currPos, int finalPos, boolean isVert) {
		// need to find out if it is moving head first or tail first
        if(currPos < finalPos){
            //headfirst = true
            for (int i = currPos + 1; i <= finalPos; i++){ // for each tile between the fox head and the finalPos, not including the head itself

                if(isVert){
                    if(!(getObjectAt(x, i) == null)){
                        return false;
                    }
                } else {
                    if(!(getObjectAt(i, y) == null)){
                        return false;
                    }
                }
            }

            return true;
        } else {
            //headfirst = false
            // this means we are moving BACKWARDS
            //First need to check that the move won't move the tail out of bounds
            if(finalPos - (((Fox)piece).getLength() - 1) < 0 && !(isVert)){
                //checking if the final position of the head + (the length of the fox minus the head) is
                // less then the edge of the board
                return false;
            }
            // for each tile between the fox tail and the finalPos of the tail, not including the tail itself
            for (int i = (currPos - 1) -(((Fox)piece).getLength() - 1); i >= finalPos - (((Fox)piece).getLength() - 1); i--){ 

                if(isVert){
                    if(!(getObjectAt(x, i) == null)){
                        return false;
                    }
                } else {
                    if(!(getObjectAt(i, y) == null)){
                        return false;
                    }
                }
            }

            return true;
        }
	}
    
	private boolean canMove (GamePiece piece, int x, int y){

        int currPos; //can only move in one direction at a time,
        int finalPos; // so these values are only either the x or y posistion.
        boolean isVert;

        //checks if desired location is out of bounds
        if(x < 0 || y < 0 || x >= boardWidth || y >= boardHeight){
            return false;
        }
        // this if is to see if the place they want to move the piece to is inline with where
        //they are either vertically or horizonally and isn't the tile the piece is already on.
        if((piece.getX() != x && piece.getY() != y)||
                (piece.getX() == x && piece.getY() == y)){
            return false;
        }
        // checking if the movement is vert or hori
        if(piece.getX() != x){
            currPos = piece.getX();
            finalPos = x;
            isVert = false;
        } else {
           currPos = piece.getY();
            finalPos = y; 
            isVert = true;
        }
        // is it a rabbit being moved?
        if(piece instanceof Rabbit){
        	return canRabbitMove(piece, x, y, currPos, finalPos, isVert);
        } else if(piece instanceof Fox) {// is fox being moved?
        	return canFoxMove(piece, x, y, currPos, finalPos, isVert);
        } else { // only enters here if piece is not a movable object (fox/rabbit)
        	return false;
        }
    }
		
	
	/**
	 * Generates a new ID for a given Piece that hasn't been taken.
	 * @param piece the type of piece the ID is being made for.
	 * @return something like "FOX 1" or whatever
	 */
	public String genNewID(PieceType piece) {
		String out = "";
		switch(piece) {
		case FOX_EW: case FOX_NS:
			out += "Fox ";
			break;
		case RABBIT:
			out += "Rabbit ";
			break;
		case HILL:
			out += "Hill ";
			break;
		case HOLE:
			out += "Hole ";
			break;
		case MUSHROOM:
			out += "Mushroom ";
			break;
		}
		boolean foundID = false;
		int IDCounter = 1;
		String outBuffer = out + IDCounter;
		while(!foundID) {
			if(!idList.contains(outBuffer)) {
				out = outBuffer;
				idList.add(out);
				foundID = true;
			}else {
				IDCounter++;
				outBuffer = out + IDCounter;
			}
		}
		return out;
	}
	
	/**
	 * basic Game constructor
	 * @param width
	 * @param height
	 */
	public Game(int width, int height) {
	}
}