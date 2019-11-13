package GameInternal;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Class Board serves as a container for all the GamePieces in the game and
 * provides methods for manipulating them.
 * 
 * @author James Horner
 */
public class Board {

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
	private Point getLocation(GamePiece piece) {
		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < board[i].length; j++) {// iterate through the board.
				if (board[i][j] != null) {
					if (board[i][j].equals(piece)) {// until a piece matching the piece provided is found.
						return new Point(j, i);
					}
				}
			}
		}
		return null;// if not found return -1.
	}

	/**
	 * @author Andrew MacKinnon
	 *
	 */
	public GamePiece[][] getBoard() {
		return board;
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
			for (int i = fox.getLength(); i > 0; i--) {// iterate over the FoxBits to find the location of the tail.
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
	 * @param piece     MovablePiece to be moved.
	 * @param direction Direction in which the piece is to be moved.
	 * @return boolean for if the movement is successful.
	 */
	public boolean move(MovablePiece piece, DIRECTION direction, int numSpaces) {
		if (piece instanceof Rabbit) {// if the piece is a rabbit
			Point oldLoc = getLocation(piece);
			if (!checkOnBoard(oldLoc)) {// check if the location is valid
				return false;
			} else {
				GamePiece inFront = getAdjacentPiece(oldLoc, direction);// get the piece beside the rabbit in the
																		// specified direction.
				if (inFront == null || inFront instanceof ContainerPiece) {// cannot jump to an immediately adjacent
																			// empty space or hole.
					return false;
				} else {// if not beside an empty space or hole.
					Point newLoc = getAdjacentCoordinate(oldLoc, direction);// keep track of the current coordinates of
																			// the adjacent piece.
					// iterate through the adjacent pieces until a hole or empty space is reached or
					// the piece being checked is off the board.
					while (!(inFront instanceof ContainerPiece) && checkOnBoard(newLoc) && inFront != null) {
						inFront = getAdjacentPiece(newLoc, direction);
						newLoc = getAdjacentCoordinate(newLoc, direction);
					}

					if (!checkOnBoard(newLoc)) {// if the piece being check is off the board
						return false;
					} else if (inFront instanceof ContainerPiece) {// if the piece being checked is a hole
						ContainerPiece destination = (ContainerPiece) inFront;
						if (destination.putIn(piece)) {// try adding the rabbit to the hole.
							((Rabbit) piece).jumpInHole();
							removePieceAt(oldLoc);
							return true;
						} else {
							return false;
						}
					} else if (inFront == null && checkOnBoard(newLoc)) {// if the space is empty
						board[newLoc.y][newLoc.x] = piece;// add the rabbit to the empty space.
						removePieceAt(oldLoc);
						return true;
					}
				}
			}
		} else if (piece instanceof Fox) {// if the piece being moved is a fox.
			Fox fox = (Fox) piece;// cast the piece to a fox for less code repetition.
			if (fox.getHead() != null) {// check the fox has FoxBits.
				Point oldLoc;
				int count = numSpaces;
				GamePiece inFront;
				if (direction == fox.getAxisForward() || direction == fox.getAxisBackward()) {// if the direction of
																								// movement is valid.
					if (direction == fox.getAxisForward()) {// if the direction is forward
						oldLoc = getLocation(fox.getHead());// get the location of the head
						inFront = getAdjacentPiece(oldLoc, direction);// get the location in front of the head.
					} else {// if the direction is backwards
						oldLoc = getLocation(fox.getHead().getTail());// get the location of the tail
						inFront = getAdjacentPiece(oldLoc, direction);// get the piece behind the tail
					}
					Point newLoc = oldLoc;// get the location in front of the head or
																			// tail
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
					} else if (inFront == null && count == 0 && checkOnBoard(newLoc)) {// if the number of spaces is
																						// reached and the space is
						// empty.
						FoxBit tempBehind;
						if (direction == fox.getAxisForward()) {// if the fox is moving forward
							board[newLoc.x][newLoc.x] = fox.getHead();// set the final destination cell to the head
							tempBehind = fox.getHead().getBehind();// set the temp behind to be the neck
						} else {// if the fox is moving backwards
							board[newLoc.y][newLoc.x] = fox.getHead().getTail();// set the final destination cell to the
																				// tail
							tempBehind = fox.getHead().getTail().getAhead();// set the temp behind to be the butt.
						}
						removePieceAt(oldLoc);// remove the tail or the head from the old position
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
						return true;
					}

				} else {// if the direction is not in the axis of the foxes movement return false.
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
	 * @param p         int for x or y coordinate based on the isX parameter.
	 * @param direction DIRECTION for the direction of the adjacent location.
	 * @param isX       boolean for whether n is an x coordinate (true) or a y
	 *                  coordinate (false).
	 * @return
	 */
	private Point getAdjacentCoordinate(Point p, DIRECTION direction) {
		switch (direction) {
		case NORTH:
			return new Point(p.x, p.y + 1);
		case SOUTH:
			return new Point(p.x, p.y - 1);
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
	private GamePiece getAdjacentPiece(Point p, DIRECTION direction) {
		switch (direction) {
		case NORTH:
			return getPieceAt(new Point(p.x, p.y + 1));
		case SOUTH:
			return getPieceAt(new Point(p.x, p.y - 1));
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
	private boolean checkOnBoard(Point p) {
		if (p.x >= boardWidth || p.x < 0 || p.y >= boardHeight || p.y < 0) {
			return false;
		} else {
			return true;
		}
	}
}
