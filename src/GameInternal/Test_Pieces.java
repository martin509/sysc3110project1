package GameInternal;

import static api.Assertions.*;

import org.junit.jupiter.api.Test;

class Test_Pieces {
	Fox fox1 = new Fox("Fox1", 4, 4, 1, DIRECTION.NORTH_SOUTH);
	Hill hill1 = new Hill("Hill1", 0, 0);
	Hole hole1 = new Hole("Hole1", 2, 2);
	Mushroom mush1 = new Mushroom("Mush1", 1, 1);
	Rabbit rabb1 = new Rabbit("Bun1", 3, 3); 
	
	@Test
	void testMovable() {
		boolean[] results = {false, false, false};
		GamePiece[] pieces = {hill1, hole1, mush1};
		String errorMsg;
		for(int n = 0; n < pieces.length; n++) {
			errorMsg = "Piece " + pieces[n].getID() + "'s movable value should be " + results[n];
			assertEquals(results[n], pieces[n].canBeMoved());
		}
		assertEquals(true, fox1.canBeMoved(), "Fox1's head should be movable.");
		assertEquals(true, fox1.getHead().canBeMoved(), "Fox1's head should be movable.");
		assertEquals(true, rabb1.canBeMoved(), "Rabb1 should be movable at first.");
		rabb1.jumpInHole();
		assertEquals(false, rabb1.canBeMoved(), "Rabb1 should not be able to be moved after being put in a hole.");
		
	}
}
