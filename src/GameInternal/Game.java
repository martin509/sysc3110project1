package GameInternal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class Game extends Board implements Observer {

	private Stack<MoveEvent> undoStack;
	private Stack<MoveEvent> redoStack;

	/**
	 * Checks for a win state in the game.
	 * 
	 * @return whether or not all rabbits are in holes. Defaults to true if there
	 *         are no rabbits.
	 */
	public boolean isGameWon() {
		ArrayList<GamePiece> rabbits = getPiecesOfType(new Rabbit());
		for (GamePiece r : rabbits) {
			if (!((Rabbit) r).isInHole()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * basic Game constructor
	 * 
	 * @param width
	 * @param height
	 */
	public Game() {
		super(5, 5);
		super.addObserver(this);
		undoStack = new Stack<MoveEvent>();
		redoStack = new Stack<MoveEvent>();
		initialiseGame();
	}

	private void initialiseGame() {
		GamePiece[] pieces = { new Hill(), new Hill(), new Hill(), new Hill(), new Hole(), new Hole(), new Hole(),
				new Hole(), new Hole(), new Rabbit(), new Rabbit(), new Rabbit(), new Mushroom(), new Mushroom(),
				new Fox(2, DIRECTION.SOUTH), new Fox(2, DIRECTION.NORTH) };
		Point[] locations = { new Point(0, 2), new Point(2, 0), new Point(2, 4), new Point(4, 2), new Point(0, 0),
				new Point(4, 0), new Point(2, 2), new Point(0, 4), new Point(4, 4), new Point(0, 2), new Point(2, 2),
				new Point(2, 4), new Point(0, 3), new Point(2, 0), new Point(1, 2), new Point(3, 3) };
		for (int i = 0; i < pieces.length; i++) {
			addPiece(locations[i], pieces[i]);
		}
//		int[] hillX = {0,2,2,4};
//		int[] hillY = {2,0,4,2};
//		for(int i=0 ; i < 4; i++) {
//			addPiece(new Point(hillX[i],hillY[i]), new Hill());
//		}
//		int[] holeX = {0,4,2,0,4};
//		int[] holeY = {0,0,2,4,4};
//		for(int i=0 ; i < 5; i++) {
//			addPiece(new Point(holeX[i],holeY[i]), new Hole());
//		}
//		int[] rabbitX = {0,2,2};
//		int[] rabbitY = {2,2,4};
//		for(int i=0 ; i < 3; i++) {
//			addPiece(new Point(rabbitX[i],rabbitY[i]), new Rabbit());
//		}
//		int[] mushroomX = {0,2};
//		int[] mushroomY = {3,0};
//		for(int i=0 ; i < 2; i++) {
//			addPiece(new Point(mushroomX[i],mushroomY[i]), new Mushroom());
//		}
//		int[] foxX = {1,3};
//		int[] foxY = {2,3};
//		DIRECTION[] foxD = {DIRECTION.SOUTH,DIRECTION.NORTH};
//		for(int i=0 ; i < 2; i++) {
//			addPiece(new Point(foxX[i],foxY[i]), new Fox(2,foxD[i]));
//		}
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
		Point p = new Point();
		for (int i = 0; i < boardWidth; i++) {
			p.y = i;
			for (int j = 0; j < boardHeight; j++) {// iterate through board
				p.x = j;
				if (getPieceAt(p) != null) {
					if (getPieceAt(p).getClass().equals(piece.getClass())) {// check if piece at location has the same
																			// class.
						pieces.add(getPieceAt(p));// if it does add it to the array
					} else if (getPieceAt(p) instanceof ContainerPiece) {// if the piece is a ContainerPiece
						ContainerPiece cont = (ContainerPiece) getPieceAt(p);
						if (cont.check() != null) {
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

	@Override
	public void update(Observable board, Object event) {
		if (event instanceof MoveEvent) {
			undoStack.push((MoveEvent) event);
		}
	}

	/**
	 * 
	 */
	public void undo() {
		if (!undoStack.isEmpty()) {
			MoveEvent e = undoStack.pop();
			move(e.getPiece(), e.getDirection().getOppositeDirection(), e.getNumSpaces());
			redoStack.push(e);
		}
	}

	/**
	 * 
	 */
	public void redo() {
		if (!redoStack.isEmpty()) {
			MoveEvent e = redoStack.pop();
			move(e.getPiece(), e.getDirection(), e.getNumSpaces());
			undoStack.push(e);
		}
	}

}