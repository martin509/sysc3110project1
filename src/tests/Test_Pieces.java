package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import GameInternal.DIRECTION;
import GameInternal.Fox;
import GameInternal.GamePiece;
import GameInternal.Hill;
import GameInternal.Hole;
import GameInternal.Mushroom;
import GameInternal.Rabbit;

/**
 * @author Martin
 * 
 */

//TODO modularize error messages.

class Test_Pieces {
	Fox fox1 = new Fox("Fox1", 1, DIRECTION.NORTH);
	Hill hill1 = new Hill("Hill1");
	Hole hole1 = new Hole("Hole1");
	Mushroom mush1 = new Mushroom("Mush1");
	Rabbit rabb1 = new Rabbit("Rabb1");

	@Test
	void testMovable() {
		GamePiece[] pieces = { hill1, hole1, mush1 };
		String errorMsg;
		for (int n = 0; n < pieces.length; n++) {
			assertEquals(false, pieces[n].canBeMoved(), pieces[n].getID() + " should not be movable.");
		}
		assertEquals(true, fox1.canBeMoved(), "Fox1 should be movable.");
		assertEquals(true, fox1.getHead().canBeMoved(), "Fox1's head should be movable.");
		assertEquals(true, rabb1.canBeMoved(), "Rabb1 should be movable.");
	}
	
	@Test
	void testCanBeJumped() {
		assertEquals(true, fox1.canBeJumped(), "Fox1 should be jumpable.");
		assertEquals(true, fox1.getHead().canBeJumped(), "Fox1's head should be jumpable.");
		assertEquals(true, rabb1.canBeJumped(), "Rabb1 should be jumpable.");
		assertEquals(true, mush1.canBeJumped(), "Mush1 should be jumpable.");
		assertEquals(false, hole1.canBeJumped(), "Hole1 should not be jumpable.");
		assertEquals(false, hill1.canBeJumped(), "Hill1 should not be jumpable.");
	}
	
	@Test
	void testJumpInHole() {
		rabb1.jumpInHole();
		assertEquals(false, rabb1.canBeMoved(), "Rabb1 should not be able to be moved after being put in a hole.");
	}
	
	@Test
	void testGetID() {
		assertEquals("Fox1", fox1.getID(), "Finding ID of Fox1 failed, result: " + fox1.getID());
		assertEquals("Hill1", hill1.getID(), "Finding ID of Hill1 failed, result: " + hill1.getID());
		assertEquals("Hole1", hole1.getID(), "Finding ID of Hole1 failed, result: " + hole1.getID());
		assertEquals("Mush1", mush1.getID(), "Finding ID of Mush1 failed, result: " + mush1.getID());
		assertEquals("Rabb1", rabb1.getID(), "Finding ID of Rabb1 failed, result: " + rabb1.getID());
	}
	
	@Test
	void testContainer() {
		assertEquals(true, hole1.canEnter(), "Container initial canEnter() didn't return true.");
		assertEquals(true, hole1.putIn(rabb1), "Container putIn() didn't return true.");
		assertEquals(false, hole1.canEnter(), "Container second canEnter() didn't return false.");
		assertEquals(rabb1, hole1.check(), "Container check() didn't return correct Piece.");
		assertEquals(rabb1, hole1.takeOut(), "Container takeOut() didn't return correct Piece.");
		assertEquals(true, hole1.canEnter(), "Container final canEnter didn't return true.");
	}
}
