package GameRunners;

import java.util.ArrayList;

import GameInternal.*;
public class TextGame {
	private Game game;
	public static void main(String[] args) {
		/*Game game1 = new Game(5, 5);
		game1.addPiece(1, 1, PieceType.RABBIT);
		game1.addPiece(1, 2, PieceType.MUSHROOM);
		game1.addPiece(1, 3, PieceType.HOLE);
		TextGame theGame = new TextGame(game1);
		while(true) {
			theGame.execute();
		}*/
		Game g1 = new Game(5,5);
		System.out.println(g1.toString() + "\n\n");
		ArrayList<Game> game = new ArrayList<Game>();
		game.add(g1);
		System.out.println(g1.containsWinningLeaf(game,g1) + "\n\n");
		System.out.println(g1.getHint().toString() + "\n\n");

	}
	
	private TextGame(Game game) {
		this.game = game;
	}
	
	/**
	 * The basic execution loop for the game. Takes a command, prints resulting board.
	 */
	private void execute() {
		//Command cmd = new Command(game);
		//System.out.println(boardToText(cmd.execute()));
	}
	/**
	 * Returns the board in text form.
	 * @param board
	 * @return
	 */
	private String boardToText(GamePiece[][] board) {
		String out = "";
		for(GamePiece[] x: board) {
			for(GamePiece y: x) {
				
			}
		}
		
		out += "Rabbits are:\n";
		out += readPieceType(PieceType.RABBIT);
		out += "Foxes are:\n";
		out += readPieceType(PieceType.FOX_NS);
		out += readPieceType(PieceType.FOX_EW);
		
		return out;
	}
	
	private String readPieceType(PieceType type) {
		String out = "";
		//ArrayList<GamePiece> pieces = game.getPiecesOfType(type);
		//for(GamePiece p: pieces) {
			//out += p.getID() + " (" + p.getX() + ", " + p.getY() + ")\n";
		//}
		return out;
	}
}
