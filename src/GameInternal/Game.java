package GameInternal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.*;

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
		ArrayList<GamePiece> rabbits = getPiecesOfType(Rabbit.class);
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
	public Game(int boardWidth, int boardHeight) {
		super(boardWidth, boardHeight);
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
	}

	/**
	 * Method getPiecesOfType is used to find objects of a certain type and return
	 * them.
	 * 
	 * @param piece GamePiece used to identify the type of piece being requested.
	 * @return ArrayList of all the pieces found on the board of the specified type.
	 */
	public ArrayList<GamePiece> getPiecesOfType(Class<?> c) {
		if (c == null) {
			return null;
		}
		ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
		Point p = new Point(0,0);
		for (int i = 0; i < boardHeight; i++) {
			for (int j = 0; j < boardWidth; j++) {// iterate through board
				p.y = i;
				p.x = j;
				if (getPieceAt(p) != null) {
					// check if piece at location has the same class.
					if (c.isInstance(getPieceAt(p))) {
						pieces.add(getPieceAt(p));// if it does add it to the array
					} else if (getPieceAt(p) instanceof ContainerPiece) {// if the piece is a ContainerPiece
						ContainerPiece cont = (ContainerPiece) getPieceAt(p);
						if (cont.check() != null) {
							if (c.isInstance(cont.check())) {// check the contents
								pieces.add(cont.check());// add to the array if it matches the class
							}
						}
					} else if((c.equals(MovablePiece.class) || c.equals(GamePiece.class)) && getPieceAt(p) instanceof FoxBit) {
						Fox fox = ((FoxBit)getPieceAt(p)).getFox();
						if(!pieces.contains(fox)) {
							pieces.add(fox);
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
			GamePiece dest = getPieceAt(e.getDestinationLocation());
			if (dest instanceof ContainerPiece) {
				((ContainerPiece) dest).takeOut();
				addPiece(e.getSourceLocation(), e.getPiece());
			} else if (e.getPiece() instanceof Fox) {
				FoxBit head = ((Fox) e.getPiece()).getHead();
				Point headLoc = getLocation(head);
				for (int i = 0; i < head.getFox().getLength(); i++) {
					removePieceAt(headLoc);
					headLoc = getAdjacentCoordinate(headLoc, head.getFox().getAxisBackward());
					head = head.getBehind();
				}
				addPiece(e.getSourceLocation(), e.getPiece());
			} else {
				removePieceAt(e.getDestinationLocation());
				addPiece(e.getSourceLocation(), e.getPiece());
			}
			redoStack.push(e);
		}
	}

	/**
	 * 
	 */
	public void redo() {
		if (!redoStack.isEmpty()) {
			MoveEvent e = redoStack.pop();
			move(e.getPiece(), e.getSourceLocation(), e.getDestinationLocation());
			undoStack.push(e);
		}
	}

	private ArrayList<MoveEvent> getAllValidPieceMoves() {
		ArrayList<GamePiece> pieces = getPiecesOfType(MovablePiece.class);
		ArrayList<MoveEvent> pieceMoves = new ArrayList<MoveEvent>();
		if (!pieces.isEmpty()) {
			for (GamePiece p : pieces) {
				pieceMoves.addAll(getValidMoves((MovablePiece) p));
			}
		}
		return pieceMoves;
	}

	private ArrayList<MoveEvent> getValidMoves(MovablePiece piece) {
		if (piece == null) {
			return null;
		} else {
			Point sourceLoc = getLocation(piece);
			Point testLoc = sourceLoc;
			ArrayList<MoveEvent> moves = new ArrayList<MoveEvent>();
			if (checkOnBoard(sourceLoc)) {
				for (DIRECTION d : DIRECTION.values()) {
					testLoc = sourceLoc;
					while (checkOnBoard(testLoc)) {
						if (isMoveValid(piece, sourceLoc, testLoc)) {
							moves.add(new MoveEvent(piece, sourceLoc, testLoc));
						}
						testLoc = getAdjacentCoordinate(testLoc, d);
					}
				}
			}
			return moves;
		}
	}
	
	public MoveEvent getHint() {
		if(isGameWon()) {
			return null;
		}
		ArrayList<MoveEvent> currentPossibleMoves = getAllValidPieceMoves();
		ArrayList<Game> gameStatesChecked = new ArrayList<Game>();
		gameStatesChecked.add(this);
		for(MoveEvent e:currentPossibleMoves) {
			Game futureGame = copyGame(this);
			futureGame.move(e.getPiece(), e.getSourceLocation(), e.getDestinationLocation());
			if(containsWinningLeaf(gameStatesChecked,futureGame)) {
				return e;
			}
		}
		return null;
	}
	public boolean containsWinningLeaf(ArrayList<Game> gameStatesChecked, Game g) {
		if(g.isGameWon()) {
			return true;
		} else if(gameStatesChecked.contains(g)) {
			return false;
		} else {
			ArrayList<MoveEvent> currentPossibleMoves = getAllValidPieceMoves();
			for(MoveEvent e:currentPossibleMoves) {
				Game futureGame = copyGame(g);
				futureGame.move(e.getPiece(), e.getSourceLocation(), e.getDestinationLocation());
				gameStatesChecked.add(futureGame);
				if(containsWinningLeaf(gameStatesChecked, futureGame)) {
					return true;
				}
			}
			return false;
		}
	}
	public static Game copyGame(Game game) {
		Game ret = new Game(game.getBoardWidth(),game.getBoardHeight());
		Point p = new Point(0,0);
		for (int i = 0; i < game.getBoardHeight(); i++) {
			for (int j = 0; j < game.getBoardWidth(); j++) {// iterate through board
				p.y = i;
				p.x = j;
				if (game.getPieceAt(p) != null) {
					ret.addPiece(p, game.getPieceAt(p));
				} else {
					ret.addPiece(p, null);
				}
			}
		}
		return ret;
	}
	
	public boolean equals(Game game) {
		if(game.getBoardHeight() != this.getBoardHeight() || game.getBoardWidth() != this.getBoardWidth()) {
			return false;
		}
		Point point = new Point(0,0);
		for(int y = 0; y < boardHeight; y++ ) {
			for(int x = 0; x < boardWidth; x++) {
				point.x=x;
				point.y=y;
				if(!this.getPieceAt(point).equals(game.getPieceAt(point))) {
					return false;
				}
			}
		}
		return true;
	}
}