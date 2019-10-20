package GameInternal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PieceTest {
	Fox fox1 = new Fox("Fox1", 1, DIRECTION.NORTH_SOUTH);
	Hill hill1 = new Hill("Hill1");
	Hole hole1 = new Hole("Hole1");
	Mushroom mush1 = new Mushroom("Mush1");
	Rabbit rabb1 = new Rabbit("Bun1"); 
	
	@Test
	void testMovable() {
		boolean[] results = {false, false, false};
		GamePiece[] pieces = {hill1, hole1, mush1};
		String errorMsg;
		for(int n = 0; n < pieces.length; n++) {
			errorMsg = "Piece " + pieces[n].getID() + "'s movable value should be " + results[n];
			assertEquals(results[n], pieces[n].canBeMoved(), errorMsg);
		}
		assertEquals(true, fox1.canBeMoved(), "Fox1's head should be movable.");
		assertEquals(true, fox1.getHead().canBeMoved(), "Fox1's head should be movable.");
		assertEquals(true, rabb1.canBeMoved(), "Rabb1 should be movable at first.");
		rabb1.jumpInHole();
		assertEquals(false, rabb1.canBeMoved(), "Rabb1 should not be able to be moved after being put in a hole.");
		
	}
	@Test
	void testGameConstructor() {
		Game game1 = new Game(5, 5);
		assertEquals("FOX1", game1.genNewID(PieceType.FOX_EW),"First Fox should be FOX1");
		assertEquals("FOX2", game1.genNewID(PieceType.FOX_EW),"2nd Fox should be FOX2");
		assertEquals("FOX3", game1.genNewID(PieceType.FOX_NS),"3rd Fox should be FOX3");
		assertEquals("RABBIT1", game1.genNewID(PieceType.RABBIT),"1st Rabbit should be RABBIT1");
	}
	

}
