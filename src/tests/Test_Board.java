package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GameInternal.Board;
import GameInternal.DIRECTION;
import GameInternal.Fox;
import GameInternal.GamePiece;
import GameInternal.Hill;
import GameInternal.Hole;
import GameInternal.Mushroom;
import GameInternal.Rabbit;

class Test_Board {
	Board board1 = new Board(5,5);
	
	@Test
	void testAddAndRemove() {
		assertEquals(true, board1.addPiece(0, 0, new Rabbit()), "First addition of a piece failed to return true.");
		assertEquals(false, board1.addPiece(0, 0, new Rabbit()), "2nd addition of a piece failed to return false.");
		assertEquals(true, board1.addPiece(1, 1, new Rabbit()));
		board1.removePieceAt(0, 0);
		board1.removePieceAt(1, 1);
		assertEquals(0, board1.getPiecesOfType(new Rabbit()).size());
	}
	
	@Test
	void testGetPiecesOfType() {
		board1.addPiece(0, 0, new Rabbit());
		board1.addPiece(1, 0, new Hole());
		board1.addPiece(3, 3, new Rabbit());
		assertEquals(2, board1.getPiecesOfType(new Rabbit()).size());
		Rabbit rabb1 = new Rabbit();
		board1.addPiece(1, 0, rabb1);
		assertEquals(true, rabb1.isInHole());
		board1.addPiece(4, 4, new Fox(1, DIRECTION.NORTH));
		assertEquals(1, board1.getPiecesOfType(new Fox(0, DIRECTION.SOUTH)).size());
	}
	
	@Test
	void testMoveRabbit() {
		board1.addPiece(0, 0, new Hole());
		board1.addPiece(0, 1, new Mushroom());
		board1.addPiece(1, 2, new Mushroom());
		board1.addPiece(2, 2, new Hill());
		Rabbit rabb2 = new Rabbit();
		Rabbit rabb1 = new Rabbit();
		board1.addPiece(0, 2, rabb1);
		board1.addPiece(0, 3, rabb2);
		assertEquals(false, board1.move(rabb2, DIRECTION.WEST, 0)); //test that you can't move in directions with nothing there
		assertEquals(false, board1.move(rabb2, DIRECTION.NORTH, 0));
		assertEquals(false, board1.move(rabb2, DIRECTION.EAST, 0));
		
		assertEquals(true, board1.move(rabb2, DIRECTION.SOUTH, 0));
		assertEquals(false, board1.move(rabb1, DIRECTION.SOUTH, 0)); //test that you can't put a rabbit in an occupied hole
	}
	
	@Test
	void testMoveFox() {
		Fox fox1 = new Fox(1, DIRECTION.WEST);
		board1.addPiece(0, 4, fox1);
		board1.addPiece(0, 0, new Hill());
		assertEquals(true, board1.move(fox1, DIRECTION.EAST, 1));
	}

}
