package GameInternal;

import java.util.ArrayList;

public class Game {

	private Board board;
			
	/**
	 * Checks for a win state in the game.
	 * @return whether or not all rabbits are in holes. Defaults to true if there are no rabbits.
	 */
	public boolean isGameWon() {
		ArrayList<GamePiece> rabbits = board.getPiecesOfType(new Rabbit());
		for(GamePiece r: rabbits) {
			if(!((Rabbit)r).isInHole()) {
				return false;
			}
		}
		return true;
	}
		
	/**
	 * 
	 * @return
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * basic Game constructor
	 * @param width
	 * @param height
	 */
	public Game() {
		board = new Board(5,5);
		initialiseGame();
	}
	
	private void initialiseGame() {
		int[] hillX = {0,2,2,4};
		int[] hillY = {2,0,4,2};
		for(int i=0 ; i < 4; i++) {
			board.addPiece(hillX[i], hillY[i], new Hill());
		}
		int[] holeX = {0,4,2,0,4};
		int[] holeY = {0,0,2,4,4};
		for(int i=0 ; i < 5; i++) {
			board.addPiece(holeX[i], holeY[i], new Hole());
		}
		int[] rabbitX = {0,2,2};
		int[] rabbitY = {2,2,4};
		for(int i=0 ; i < 3; i++) {
			board.addPiece(rabbitX[i], rabbitY[i], new Rabbit());
		}
		int[] mushroomX = {0,2};
		int[] mushroomY = {3,0};
		for(int i=0 ; i < 2; i++) {
			board.addPiece(mushroomX[i], mushroomY[i], new Mushroom());
		}
		int[] foxX = {1,3};
		int[] foxY = {2,4};
		DIRECTION[] foxD = {DIRECTION.SOUTH,DIRECTION.NORTH};
		for(int i=0 ; i < 2; i++) {
			board.addPiece(foxX[i], foxY[i], new Fox(2,foxD[i]));
		}
	}
}