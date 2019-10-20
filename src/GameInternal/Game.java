package GameInternal;

import java.util.ArrayList;

public class Game {
	
	//hoo boy
	//hoooooooooooooooooooooooooooooooooooooooooooooooo boy
	//this is gonna take a while isn't it
	private GamePiece[][] board;
	private int boardWidth, boardHeight;
	private ArrayList<String> idList; //Gonna want a list of IDs that have already been made, to avoid duplicate keys.
	
	private GamePiece getObjectAt(int x, int y) {
		return board[x][y];
	}
	
	/**
	 * This is private because why trust anything but Game to make GamePieces?
	 * @param x
	 * @param y
	 * @param piece
	 * @return whether or not it succeeded.
	 */
	private boolean addPiece(int x, int y, GamePiece piece) {
		if(x >= boardWidth || y >= boardHeight) {
			return false;
		}
		if(board[x][y] == null) {
			board[x][y] = piece;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Not sure how to go about this, but this one is the public-facing addPiece
	 * @param x
	 * @param y
	 * @param piece
	 * @return whether or not it succeeded
	 */
	public boolean addPiece(int x, int y, PieceType piece) {
		switch(piece) {
		case FOX_EW:
			return addPiece(x, y, new Fox(genNewID(piece), 1, DIRECTION.EAST_WEST));
		case FOX_NS:
			return addPiece(x, y, new Fox(genNewID(piece), 1, DIRECTION.NORTH_SOUTH));
		case RABBIT:
			return addPiece(x, y, new Rabbit(genNewID(piece)));
		case HILL:
			return addPiece(x, y, new Hill(genNewID(piece)));
		case HOLE:
			return addPiece(x, y, new Hole(genNewID(piece)));
		default:
			return addPiece(x, y, new Mushroom(genNewID(piece)));
			
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
			out += "FOX";
			break;
		case RABBIT:
			out += "RABBIT";
			break;
		case HILL:
			out += "HILL";
			break;
		case HOLE:
			out += "HOLE";
			break;
		case MUSHROOM:
			out += "MUSH";
			break;
		}
		boolean foundID = false;
		int IDCounter = 1;
		String outBuffer = out + IDCounter;
		while(!foundID) {
			if(idList.contains(outBuffer)) {
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
		board = new GamePiece[width][height];
		boardWidth = width;
		boardHeight = height;
		idList = new ArrayList<String>();
	}
	
	
}
