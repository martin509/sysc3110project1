package GameInternal;

import java.util.ArrayList;

/**
 * Class Board serves as a container for all the GamePieces in the game and
 * provides methods for manipulating them.
 * 
 * @author James Horner
 */
public class Board {

	private GamePiece[][] board;
	private int boardWidth, boardHeight;

	public Board(int width, int height) {
		board = new GamePiece[width][height];
		boardWidth = width;
		boardHeight = height;
		for (int i = 0; i < boardHeight; i++) {// initialize the board to be all null;
			for (int j = 0; j < boardWidth; j++) {
				board[i][j] = null;
			}
		}
	}

	/**
	 * Method getXLocation provides a way to find the x coordinate of a piece on the
	 * board.
	 * 
	 * @param piece GamePiece that is being searched for.
	 * @param x     boolean true if the x coordinate is to be returned, false for y.
	 * @return int for the coordinate of the piece or -1 if no piece is found.
	 */
	private int getLocation(GamePiece piece, boolean x) {
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < board[i].length; j++) {// iterate through the board.
				if (board[i][j].equals(piece)) {// until a piece matching the piece provided is found.
					if (x) {
						return j;
					} // return the x coordinate of the piece.
					else {
						return i;
					}
				}
			}
		}
		return -1;// if not found return -1.
	}

	/**
	 * Method getPieceAt returns the piece at the specific grid coordinate.
	 * 
	 * @param x int for x coordinate on the grid.
	 * @param y int for y coordinate on the grid.
	 * @return GamePiece at the specified grid coordinate null if no piece exists
	 *         there.
	 */
	public GamePiece getPieceAt(int x, int y) {
		if (!checkOnBoard(x,y)) {// check if coordinates are on the board.
			return null;
		} else {
			return board[y][x];
		}
	}

	/**
	 * Method addPiece adds a piece to a specific location on the board.
	 * 
	 * @param x     int for x coordinate of the piece.
	 * @param y     into for y coordinate of the piece.
	 * @param piece GamePiece to be placed on the board.
	 * @return boolean for whether the piece could be successfully added.
	 */
	public boolean addPiece(int x, int y, GamePiece piece) {
		if (!checkOnBoard(x,y)) {// check that the coordinates are valid.
			return false;
		} else if (board[x][y] == null) {// if there is a piece that is at the coordinates already.
			board[x][y] = piece;// if not add the piece.
			return true;
		} else if (board[x][y] instanceof Hole && (piece instanceof Rabbit ||piece instanceof Mushroom)) {// if the piece at the coordinates is a hole and the piece being added is a rabbit.
			Hole container = (Hole) board[x][y];
			if(piece instanceof Rabbit) {
				Rabbit pieceR = (Rabbit) piece; //evil casting to Rabbit so the Rabbit can be properly be putInHole()
				pieceR.jumpInHole();
			}
			return container.putIn(piece);// try adding the rabbit to the hole and return the result.
		} else if (piece instanceof Fox){//if piece being added is a fox.
			Fox fox = (Fox) piece;//cast piece to a new fox object to avoid repetition.
			int tailX = x;//reset the location to the head.
			int tailY = y;
			for(int i = fox.getLength(); i > 0; i--) {//iterate over the FoxBits to find the location of the tail.
				tailX = getAdjacentCoordinate(tailX,fox.getAxisBackward(),true);
				tailY = getAdjacentCoordinate(tailY,fox.getAxisBackward(),false);
				if(board[tailY][tailX]!=null) {//if any of the body pieces would overwrite another piece
					return false;//return unsuccessful.
				}
			}
			if(checkOnBoard(tailX,tailY)) {//if the tail would be on the board
				tailX = x;//reset the location of the tail to the head.
				tailY = y;
				FoxBit body = fox.getHead().getBehind();//make a body tracker FoxBit.
				
				board[x][y] = fox.getHead();//add the head to the board.
				
				while(body!=null) {//while not at the tail of the fox
					tailX = getAdjacentCoordinate(tailX,fox.getAxisBackward(),true);//find the coordinate behind the current coordinate.
					tailY = getAdjacentCoordinate(tailY,fox.getAxisBackward(),false);
					board[tailX][tailY] = body;//add the current body piece to the cell.
					body = body.getBehind();//get the next body piece to add.
				}
				return true;//return successful.
			} else {//if the tail would be off the board return false.
				return false;
			}
			
		} else {
			return false;// default to false.
		}
	}

	/**
	 * Method removePieceAt removes a piece at a certain location.
	 * 
	 * @param x int for x coordinate of the piece.
	 * @param y int for y coordinate of the piece.
	 */
	public void removePieceAt(int x, int y) {
		board[x][y] = null;
	}

	/**
	 * Method getPiecesOfType is used to find objects of a certain type and return
	 * them.
	 * 
	 * @param piece GamePiece used to identify the type of piece being requested.
	 * @return ArrayList of all the pieces found on the board of the specified type.
	 */
	public ArrayList<GamePiece> getPiecesOfType(GamePiece piece) {
		if (piece == null) {
			return null;
		}
		ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < boardHeight; j++) {// iterate through board
				if (board[i][j] != null) {
					if (board[i][j].getClass().equals(piece.getClass())) {// check if piece at location has the same
																			// class.
						pieces.add(board[i][j]);// if it does add it to the array
					} else if (board[i][j] instanceof ContainerPiece) {// if the piece is a ContainerPiece
						ContainerPiece cont = (ContainerPiece) board[i][j];
						if(cont.check() != null) {
							if (cont.check().getClass().equals(piece.getClass())) {// check the contents
								pieces.add(cont.check());// add to the array if it matches the class
							}
						}
					}
				}
			}
		}
		return pieces;
	}

	/**
	 * Method move enables the movement of pieces around the board following the
	 * rules of the game.
	 * 
	 * @param piece     MovablePiece to be moved.
	 * @param direction Direction in which the piece is to be moved.
	 * @return boolean for if the movement is successful.
	 */
	public boolean move(MovablePiece piece, DIRECTION direction, int numSpaces) {
		if (piece instanceof Rabbit) {// if the piece is a rabbit
			int oldX = getLocation(piece, true);
			int oldY = getLocation(piece, false);
			if (!checkOnBoard(oldX, oldY)) {// check if the location is valid
				return false;
			} else {
				GamePiece inFront = getAdjacentPiece(oldX, oldY, direction);// get the piece beside the rabbit in the
																			// specified direction.
				if (inFront == null || inFront instanceof ContainerPiece) {// cannot jump to an immediately adjacent
																			// empty space or hole.
					return false;
				} else {// if not beside an empty space or hole.
					int newX = getAdjacentCoordinate(oldX, direction, true);// keep track of the current coordinates of
																			// the adjacent piece.
					int newY = getAdjacentCoordinate(oldY, direction, false);

					// iterate through the adjacent pieces until a hole or empty space is reached or
					// the piece being checked is off the board.
					while (!(inFront instanceof ContainerPiece) && checkOnBoard(newX, newY)) {
						inFront = getAdjacentPiece(newX, newY, direction);
						newX = getAdjacentCoordinate(newX, direction, true);
						newY = getAdjacentCoordinate(newY, direction, false);
					}

					if (!checkOnBoard(newX, newY)) {// if the piece being check is off the board
						return false;
					} else if (inFront instanceof ContainerPiece) {// if the piece being checked is a hole
						ContainerPiece destination = (ContainerPiece) inFront;
						return destination.putIn(piece);// try adding the rabbit to the hole.
					} else if (inFront == null) {// if the space is empty
						board[newY][newX] = piece;// add the rabbit to the empty space.
						return true;
					}
				}
			}
		} else if (piece instanceof Fox) {// if the piece being moved is a fox.
			Fox fox = (Fox) piece;// cast the piece to a fox for less code repetition.
			if (fox.getHead() != null) {// check the fox has FoxBits.
				int oldX;
				int oldY;
				int count = numSpaces;
				GamePiece inFront;
				if (direction == fox.getAxisForward() || direction == fox.getAxisBackward()) {//if the direction of movement is valid.
					if (direction == fox.getAxisForward()) {//if the direction is forward
						oldX = getLocation(fox.getHead(), true);//get the location of the head
						oldY = getLocation(fox.getHead(), false);
						inFront = getAdjacentPiece(oldX,oldY,direction);//get the location in front of the head.
					} else {//if the direction is backwards
						oldX = getLocation(fox.getHead().getTail(), true);//get the location of the tail
						oldY = getLocation(fox.getHead().getTail(), false);
						inFront = getAdjacentPiece(oldX,oldY,direction);//get the piece behind the tail
					}
					int newX = getAdjacentCoordinate(oldX, direction, true);//get the location in front of the head or tail
					int newY = getAdjacentCoordinate(oldY, direction, false);
					
					//while the space in front is empty and the count is not exceeded and the location of the piece in front is on the board
					while (inFront == null && count > 0 && checkOnBoard(newX, newY)) {
						inFront = getAdjacentPiece(newX, newY, direction);//get the new piece in front
						newX = getAdjacentCoordinate(newX, direction, true);//increment the location in that direction
						newY = getAdjacentCoordinate(newY, direction, false);
						count--;//decrement the count.
					}
					if (!checkOnBoard(newX, newY)) {//if the final destination is off the board return false.
						return false;
					} else if (inFront != null) {//if the final destination already has a piece return false.
						return false;
					} else if (inFront == null && count == 0) {//if the number of spaces is reached and the space is empty.
						FoxBit tempBehind;
						if (direction == fox.getAxisForward()) {//if the fox is moving forward
							board[newY][newX] = fox.getHead();//set the final destination cell to the head
							tempBehind = fox.getHead().getBehind();//set the temp behind to be the neck
						} else {//if the fox is moving backwards
							board[newY][newX] = fox.getHead().getTail();//set the final destination cell to the tail
							tempBehind = fox.getHead().getTail().getAhead();//set the temp behind to be the butt. 
						}
						removePieceAt(oldX, oldY);//remove the tail or the head from the old position
						int newXBehind = getAdjacentCoordinate(newX, direction.getOppositeDirection(), true);//get the location of the new neck or butt
						int newYBehind = getAdjacentCoordinate(newY, direction.getOppositeDirection(), false);
						int oldXBehind = getAdjacentCoordinate(oldX, direction.getOppositeDirection(), true);//get the location of the old neck or butt
						int oldYBehind = getAdjacentCoordinate(oldY, direction.getOppositeDirection(), false);

						while (tempBehind != null) {//while not at the tail or head

							board[newYBehind][newXBehind] = tempBehind;//add the temp behind to the new location.
							removePieceAt(oldXBehind, oldYBehind);//remove it from the old location.

							newX = getAdjacentCoordinate(newX, direction.getOppositeDirection(), true);//store the location of the piece just moved.
							newY = getAdjacentCoordinate(newY, direction.getOppositeDirection(), false);
							newXBehind = getAdjacentCoordinate(newX, direction.getOppositeDirection(), true);//get the destination of the next piece to be moved.
							newYBehind = getAdjacentCoordinate(newY, direction.getOppositeDirection(), false);
							oldXBehind = getAdjacentCoordinate(oldXBehind, direction.getOppositeDirection(), true);//get the location of the next piece to be moved.
							oldYBehind = getAdjacentCoordinate(oldYBehind, direction.getOppositeDirection(), false);

							if (direction == fox.getAxisForward()) {//if going forwards increment towards the tail.
								tempBehind = tempBehind.getBehind();
							} else {
								tempBehind = tempBehind.getAhead();//if going backwards increment towards the head.
							}
						}
					}

				} else {//if the direction is not in the axis of the foxes movement return false.
					return false;
				}
			}
		}
		return false;// default to false.
	}

	/**
	 * Method getAdjacentCoordinate is used to increment a grid location based on a
	 * direction.
	 * 
	 * @param n         int for x or y coordinate based on the isX parameter.
	 * @param direction DIRECTION for the direction of the adjacent location.
	 * @param isX       boolean for whether n is an x coordinate (true) or a y
	 *                  coordinate (false).
	 * @return
	 */
	private int getAdjacentCoordinate(int n, DIRECTION direction, boolean isX) {
		switch (direction) {
		case NORTH:
			if (isX) {
				return n;
			} else {
				return n + 1;
			}
		case SOUTH:
			if (isX) {
				return n;
			} else {
				return n - 1;
			}
		case EAST:
			if (isX) {
				return n + 1;
			} else {
				return n;
			}
		case WEST:
			if (isX) {
				return n - 1;
			} else {
				return n;
			}
		}
		return n;
	}

	/**
	 * Method getAdjacentPiece is used to get an adjacent GamePiece to a grid
	 * coordinate
	 * 
	 * @param x         int for the x coordinate of the reference piece.
	 * @param y         int for the y coordinate of the reference piece.
	 * @param direction DIRECTION of the adjacent piece to the reference piece.
	 * @return GamePiece for adjacent piece, null if there is no piece or if piece
	 *         is outside of the board.
	 */
	private GamePiece getAdjacentPiece(int x, int y, DIRECTION direction) {
		switch (direction) {
		case NORTH:
			return getPieceAt(x, y + 1);
		case SOUTH:
			return getPieceAt(x, y - 1);
		case EAST:
			return getPieceAt(x + 1, y);
		case WEST:
			return getPieceAt(x - 1, y);
		}
		return null;
	}

	/**
	 * Method checkOnBoard is used to check if a certain coordinate lies on the game
	 * board.
	 * 
	 * @param x int for x coordinate of the location being checked.
	 * @param y int for y coordinate of the location being checked.
	 * @return boolean true if x and y lie on the board, false otherwise.
	 */
	private boolean checkOnBoard(int x, int y) {
		if (x >= boardWidth || x < 0 || y >= boardHeight || y < 0) {
			return false;
		} else {
			return true;
		}
	}
}
