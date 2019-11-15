package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import GameInternal.Board;
import GameInternal.DIRECTION;
import GameInternal.Fox;
import GameInternal.FoxBit;
import GameInternal.GamePiece;
import GameInternal.Hill;
import GameInternal.Hole;
import GameInternal.Mushroom;
import GameInternal.Rabbit;

class Test_Board {
	Board board1 = new Board(5, 5);

	@Test
	void testAdd() {
		assertTrue(board1.addPiece(new Point(0, 0), new Rabbit()));
		assertFalse(board1.addPiece(new Point(0, 0), new Rabbit()));
		assertTrue(board1.addPiece(new Point(1, 1), new Rabbit()));
		assertTrue(board1.addPiece(new Point(3, 1), new Fox(2, DIRECTION.SOUTH)));
		assertFalse(board1.addPiece(new Point(3,1), new Fox(3, DIRECTION.NORTH)));
		assertFalse(board1.addPiece(new Point(3,2), new Fox(3, DIRECTION.SOUTH)));
		assertTrue(board1.addPiece(new Point(3,2), new Fox(3, DIRECTION.NORTH)));
		assertEquals(board1.getPieceAt(new Point(3,0)).getClass(),FoxBit.class);
		assertEquals(board1.getPieceAt(new Point(3,1)).getClass(),FoxBit.class);
		assertEquals(board1.getPieceAt(new Point(3,2)).getClass(),FoxBit.class);
		assertEquals(board1.getPieceAt(new Point(3,3)).getClass(),FoxBit.class);
		assertEquals(board1.getPieceAt(new Point(3,4)).getClass(),FoxBit.class);
	}

	@Test
	void testRemove() {
		assertTrue(board1.addPiece(new Point(0, 0), new Rabbit()));
		assertFalse(board1.addPiece(new Point(0, 0), new Rabbit()));
		assertTrue(board1.addPiece(new Point(1, 1), new Rabbit()));
		board1.removePieceAt(new Point(0, 0));
		assertNull(board1.getPieceAt(new Point(0, 0)));
		board1.removePieceAt(new Point(1, 1));
		assertNull(board1.getPieceAt(new Point(1, 1)));
	}
	/*
	 * @Test void testGetPiecesOfType() { board1.addPiece(0, new Rabbit());
	 * board1.addPiece(1, new Hole()); board1.addPiece(3, new Rabbit());
	 * assertEquals(2, board1.getPiecesOfType(new Rabbit()).size()); Rabbit rabb1 =
	 * new Rabbit(); board1.addPiece(1, rabb1); assertTrue( rabb1.isInHole());
	 * board1.addPiece(4, new Fox(1, DIRECTION.NORTH)); assertEquals(1,
	 * board1.getPiecesOfType(new Fox(0, DIRECTION.SOUTH)).size()); }
	 */

	@Test
	void testMoveRabbit() {
		board1.addPiece(new Point(2, 4), new Hole());
		board1.addPiece(new Point(1, 4), new Mushroom());
		board1.addPiece(new Point(2, 3), new Mushroom());
		board1.addPiece(new Point(4, 4), new Hill());
		Rabbit rabb2 = new Rabbit();
		Rabbit rabb1 = new Rabbit();
		board1.addPiece(new Point(0, 4), rabb1);
		board1.addPiece(new Point(2, 2), rabb2);
		assertFalse(board1.move(rabb2, DIRECTION.WEST, 0)); // test that you can't move in directions with nothing there
		assertFalse(board1.move(rabb2, DIRECTION.NORTH, 0));
		assertFalse(board1.move(rabb2, DIRECTION.EAST, 0));

		assertTrue(board1.move(rabb2, DIRECTION.SOUTH, 0));
		assertTrue(board1.move(rabb1, DIRECTION.EAST, 0)); // test that you can't put a rabbit in an occupied hole
		assertEquals(board1.getPieceAt(new Point(3, 4)).getClass(), Rabbit.class);
	}

	@Test
	void testMoveFox() {
		Fox fox1 = new Fox(1, DIRECTION.WEST);
		board1.addPiece(new Point(0, 0), fox1);
		board1.addPiece(new Point(3, 0), new Hill());
		assertTrue(board1.move(fox1, DIRECTION.EAST, 1));
		assertFalse(board1.move(fox1, DIRECTION.EAST, 3));
	}

}
