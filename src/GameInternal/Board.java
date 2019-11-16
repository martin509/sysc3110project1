package GameInternal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Class Board serves as a container for all the GamePieces in the game and
 * provides methods for manipulating them.
 * 
 * @author James Horner
 */
public class Board extends Observable {

	private GamePiece[][] board;
	protected int boardWidth;
	protected int boardHeight;

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
	protected Point getLocation(GamePiece piece) {
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < board[i].length; j++) {// iterate through the board.
				if (board[i][j] != null) {
					if (board[i][j].equals(piece)) {// until a piece matching the piece provided is found.
						return new Point(j, i);
					} else if (board[i][j] instanceof ContainerPiece) {
						ContainerPiece temp = (ContainerPiece) board[i][j];
						if (!(temp.isEmpty())) {
							if (temp.check().equals(piece)) {
								return new Point(j, i);
							}
						}
					} else if (board[i][j] instanceof FoxBit) {
						FoxBit temp = (FoxBit) board[i][j];
						if (temp.getFox().equals(piece)) {
							return getLocation(temp.getHead());
						}
					}
				}
			}
		}
		return null;// if not found return -1.
	}

	/**
	 * Method getPieceAt returns the piece at the specific grid coordinate.
	 * 
	 * @param x int for x coordinate on the grid.
	 * @param y int for y coordinate on the grid.
	 * @return GamePiece at the specified grid coordinate null if no piece exists
	 *         there.
	 */
	public GamePiece getPieceAt(Point p) {
		if (!checkOnBoard(p)) {// check if coordinates are on the board.
			return null;
		} else {
			return board[p.y][p.x];
		}
	}

	/**
	 * Method addPiece adds a piece to a specific location on the board.
	 * 
	 * @param p     int for x coordinate of the piece.
	 * @param piece GamePiece to be placed on the board.
	 * @return boolean for whether the piece could be successfully added.
	 */
	public boolean addPiece(Point p, GamePiece piece) {
		if (!checkOnBoard(p)) {// check that the coordinates are valid.
			return false;
		}
		// if the piece at the coordinates is a hole and the piece being added is a
		// rabbit.
		else if (getPieceAt(p) instanceof ContainerPiece && (piece instanceof Rabbit || piece instanceof Mushroom)) {
			if (getPieceAt(p) instanceof Hole) {
				Hole container = (Hole) getPieceAt(p);
				if (piece instanceof Rabbit) {
					if (container.putIn(piece)) {// try adding the rabbit to the hole and return the result.
						Rabbit pieceR = (Rabbit) piece; // evil casting to Rabbit so the Rabbit can be properly be
														// putInHole()
						pieceR.jumpInHole();
						return true;
					} else {
						return false;
					}
				} else {// if the piece is a mushroom try to add it to the hole and return the result.
					return container.putIn(piece);
				}
			} else {
				ContainerPiece container = (ContainerPiece) getPieceAt(p);
				return container.putIn(piece);
			}
		} else if (piece instanceof Fox) {// if piece being added is a fox.
			Fox fox = (Fox) piece;// cast piece to a new fox object to avoid repetition.
			Point tailPos = p;// set the location to the head.
			for (int i = fox.getLength() - 1; i > 0; i--) {// iterate over the FoxBits to find the location of the tail.
				if (getPieceAt(tailPos) != null) {// if any of the body pieces would overwrite another piece
					return false;// return unsuccessful.
				}
				tailPos = getAdjacentCoordinate(tailPos, fox.getAxisBackward());
			}
			if (checkOnBoard(tailPos)) {// if the tail would be on the board
				tailPos = p;// reset the location of the tail to the head.
				FoxBit body = fox.getHead().getBehind();// make a body tracker FoxBit.

				board[p.y][p.x] = fox.getHead();// add the head to the board.

				while (body != null) {// while not at the tail of the fox
					tailPos = getAdjacentCoordinate(tailPos, fox.getAxisBackward());// find the coordinate behind the
																					// current coordinate.
					board[tailPos.y][tailPos.x] = body;// add the current body piece to the cell.
					body = body.getBehind();// get the next body piece to add.
				}
				return true;// return successful.
			} else {// if the tail would be off the board return false.
				return false;
			}

		} else if (getPieceAt(p) == null) {// if there is not a piece that is at the coordinates already.
			board[p.y][p.x] = piece;// if not add the piece.
			return true;
		} else {
			return false;// default to false.
		}
	}

	/**
	 * Method removePieceAt removes a piece at a certain location.
	 * 
	 * @param p Point for coordinates of the piece.
	 */
	public void removePieceAt(Point p) {
		board[p.y][p.x] = null;
	}

	/**
	 * Method move enables the movement of pieces around the board following the
	 * rules of the game.
	 * 
	 * @param piece       MovablePiece to be moved.
	 * @param destination Direction in which the piece is to be moved.
	 * @return boolean for if the movement is successful.
	 */
	public boolean move(MovablePiece piece, Point destination) {
		if (piece == null) {// Check that piece is not null.
			return false;// If it is null it cannot be moved.
		}
		Point source = getLocation(piece);// Get the location of the piece to be moved.
		DIRECTION direction = getDirectionFromPoint(source, destination);// Get the direction of the movement to happen.

		if (!checkOnBoard(source) || direction == null) {// Check if the location and direction is valid.
			return false;// If not return false.
		}
		if (piece instanceof Rabbit) {// if the piece is a rabbit
			Rabbit rabbit = (Rabbit) piece;
			if (isMoveValid(rabbit, source, destination)) {
				if (getPieceAt(source) instanceof ContainerPiece) {
					ContainerPiece temp = (ContainerPiece) getPieceAt(source);
					temp.takeOut();
				} else {
					removePieceAt(source);
				}
				if (getPieceAt(destination) instanceof ContainerPiece) {
					ContainerPiece container = (ContainerPiece) getPieceAt(destination);
					if (container.putIn(rabbit)) {// try adding the rabbit to the hole.
						rabbit.jumpInHole();
					}
				} else {
					board[destination.y][destination.x] = rabbit;// add the rabbit to the empty space.
				}
				setChanged();
				notifyObservers(new MoveEvent(rabbit, source, destination));
				return true;

			} else {
				return false;
			}
		
		} else if (piece instanceof Fox) {// if the piece being moved is a fox.
			Fox fox = (Fox) piece;// cast the piece to a fox for less code repetition.
			if (fox.getHead() != null) {// check the fox has FoxBits.
				Point oldLoc;
				if(direction == fox.getAxisForward()) {
					oldLoc = getLocation(fox.getHead());
				} else if (direction == fox.getAxisBackward()){
					oldLoc = getLocation(fox.getHead().getTail());
				} else {
					return false;
				}
				int count = getNumSpacesFromPoint(oldLoc, destination);
				if(isMoveValid(fox,oldLoc,destination)) {
					FoxBit tempBehind;
					if (direction == fox.getAxisForward()) {// if the fox is moving forward
						board[destination.y][destination.x] = fox.getHead();// set the final destination cell to the head
						tempBehind = fox.getHead().getBehind();// set the temp behind to be the neck
					} else {// if the fox is moving backwards
						board[destination.y][destination.x] = fox.getHead().getTail();// set the destination cell to the tail.
						tempBehind = fox.getHead().getTail().getAhead();// set the temp behind to be the butt.
					}
					removePieceAt(oldLoc);// remove the tail or the head from the old position
					Point newLoc = destination;
					// get the location of the new neck or butt.
					Point newLocBehind = getAdjacentCoordinate(newLoc, direction.getOppositeDirection());
					// get the location of the old neck or butt.
					Point oldLocBehind = getAdjacentCoordinate(oldLoc, direction.getOppositeDirection());
					while (tempBehind != null) {// while not at the tail or head
						board[newLocBehind.y][newLocBehind.x] = tempBehind;// add the temp behind to the new
																			// location.
						removePieceAt(oldLocBehind);// remove it from the old location.
						// store the location of the piece just moved.
						newLoc = getAdjacentCoordinate(newLoc, direction.getOppositeDirection());
						// get the destination of the next piece to be moved.
						newLocBehind = getAdjacentCoordinate(newLoc, direction.getOppositeDirection());
						// get the location of the next piece to be moved.
						oldLocBehind = getAdjacentCoordinate(oldLocBehind, direction.getOppositeDirection());
						if (direction == fox.getAxisForward()) {// if going forwards increment towards the tail.
							tempBehind = tempBehind.getBehind();
						} else {
							tempBehind = tempBehind.getAhead();// if going backwards increment towards the head.
						}
					}
					setChanged();
					notifyObservers(new MoveEvent(fox, oldLoc, destination));
					return true;

				}
			}
			return false;
		}
		return false;// default to false.
	}

	/**
	 * Method getAdjacentCoordinate is used to increment a grid location based on a
	 * direction.
	 * 
	 * @param p         int for x or y coordinate based on the isX parameter.
	 * @param direction DIRECTION for the direction of the adjacent location.
	 * @param isX       boolean for whether n is an x coordinate (true) or a y
	 *                  coordinate (false).
	 * @return
	 */
	protected Point getAdjacentCoordinate(Point p, DIRECTION direction) {
		switch (direction) {
		case NORTH:
			return new Point(p.x, p.y - 1);
		case SOUTH:
			return new Point(p.x, p.y + 1);
		case EAST:
			return new Point(p.x + 1, p.y);
		case WEST:
			return new Point(p.x - 1, p.y);
		}
		return p;
	}

	/**
	 * Method getAdjacentPiece is used to get an adjacent GamePiece to a grid
	 * coordinate
	 * 
	 * @param p         Point for the coordinates of the reference piece.
	 * @param direction DIRECTION of the adjacent piece to the reference piece.
	 * @return GamePiece for adjacent piece, null if there is no piece or if piece
	 *         is outside of the board.
	 */
	protected GamePiece getAdjacentPiece(Point p, DIRECTION direction) {
		switch (direction) {
		case NORTH:
			return getPieceAt(new Point(p.x, p.y - 1));
		case SOUTH:
			return getPieceAt(new Point(p.x, p.y + 1));
		case EAST:
			return getPieceAt(new Point(p.x + 1, p.y));
		case WEST:
			return getPieceAt(new Point(p.x - 1, p.y));
		}
		return null;
	}

	/**
	 * Method checkOnBoard is used to check if a certain coordinate lies on the game
	 * board.
	 * 
	 * @param p Point for coordinates of the location being checked.
	 * @return boolean true if x and y lie on the board, false otherwise.
	 */
	protected boolean checkOnBoard(Point p) {
		if (p.x >= boardWidth || p.x < 0 || p.y >= boardHeight || p.y < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	protected DIRECTION getDirectionFromPoint(Point source, Point destination) {
		if (checkOnBoard(source) && checkOnBoard(destination)) {
			if (source.y == destination.y) {
				if (source.x > destination.x) {
					return DIRECTION.WEST;
				} else {
					return DIRECTION.EAST;
				}
			} else if (source.x == destination.x) {
				if (source.y > destination.y) {
					return DIRECTION.NORTH;
				} else {
					return DIRECTION.SOUTH;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	protected int getNumSpacesFromPoint(Point source, Point destination) {
		if (checkOnBoard(source) && checkOnBoard(destination)) {
			if (source.y == destination.y) {
				return Math.abs(destination.x - source.x);
			} else if (source.x == destination.x) {
				return Math.abs(destination.y - source.y);
			}
		}
		return -1;

	}

	protected boolean isMoveValid(MovablePiece piece, Point source, Point destination) {
		DIRECTION direction = getDirectionFromPoint(source, destination);// Get the direction of the movement to happen.
		GamePiece inFront = getAdjacentPiece(source, direction);
		if (!checkOnBoard(source) || direction == null) {// Check if the location and direction is valid.
			return false;// If not return false.
		}
		if (piece.getClass() == Rabbit.class) {
			// cannot jump to an immediately adjacent empty space or out of a hole.
			if (inFront == null || getPieceAt(source) instanceof Hole) {
				return false;
			} else {// if not beside an empty space or in a hole.
				if (inFront instanceof ContainerPiece) {// Check if inFront is a ContainerPiece
					if (((ContainerPiece) inFront).isEmpty()) {
						return false;// If it is and it is empty return false.
					}
				}
				// keep track of the current coordinates of the adjacent rabbit.
				Point newLoc = getAdjacentCoordinate(source, direction);
				// iterate through the adjacent pieces until an empty space is reached, or
				// the rabbit being checked is off the board, or the rabbit cannot be jumped.
				while (checkOnBoard(newLoc) && inFront != null && inFront.canBeJumped() && !newLoc.equals(destination)) {
					newLoc = getAdjacentCoordinate(newLoc, direction);
					inFront = getPieceAt(newLoc);
				}
				if (!checkOnBoard(newLoc)) {// if the rabbit being check is off the board
					return false;
				} else if (inFront == null && checkOnBoard(newLoc)) {// if the space is empty
					return true;
				} else if (!inFront.canBeJumped()) {// if the rabbit being checked cannot be jumped.
					if (inFront instanceof ContainerPiece) {// check if the rabbit is a container
						ContainerPiece container = (ContainerPiece) inFront;
						if (container.isEmpty()) {// try adding the rabbit to the hole.
							return true;
						}
					}
				}
				return false;
			}
		} else if (piece.getClass() == Fox.class) {
			Fox fox = (Fox) piece;
			Point oldLoc;
			int count = getNumSpacesFromPoint(source, destination);
			// If the direction of movement is valid.
			if (direction == fox.getAxisForward() || direction == fox.getAxisBackward()) {
				if (direction == fox.getAxisForward()) {// if the direction is forward
					oldLoc = getLocation(fox.getHead());// get the location of the head
				} else {// if the direction is backwards
					oldLoc = getLocation(fox.getHead().getTail());// get the location of the tail
				}
				inFront = getAdjacentPiece(oldLoc, direction);// get the location in front of the head.
				Point newLoc = oldLoc;// get the location in front of the head or tail
				// while the space in front is empty and the count is not exceeded and the
				// location of the piece in front is on the board
				while (inFront == null && count > 0 && checkOnBoard(newLoc)) {
					inFront = getAdjacentPiece(newLoc, direction);// get the new piece in front
					newLoc = getAdjacentCoordinate(newLoc, direction);// increment the location in that direction
					count--;// decrement the count.
				}
				if (!checkOnBoard(newLoc)) {// if the final destination is off the board return false.
					return false;
				} else if (inFront != null) {// if the final destination already has a piece return false.
					return false;
				}
				// if the number of spaces is reached and the space is empty.
				else if (inFront == null && count == 0 && checkOnBoard(newLoc)) {
					return true;
				}
			}
		}
		return false;
	}
}
