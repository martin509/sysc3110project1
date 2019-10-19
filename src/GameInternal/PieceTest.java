package GameInternal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PieceTest {
	
	GamePiece[] createPieces() {
		GamePiece[] pieces = new GamePiece[5];
		pieces[0] = new Fox("Fox1", 1, DIRECTION.NORTH_SOUTH);
		pieces[1] = new Hill("Hill1");
		pieces[2] = new Hole("Hole1");
		pieces[3] = new Mushroom("Mush1");
		pieces[4] = new Rabbit("Bun1"); 
		return pieces;
	}
	@Test
	void testMovable() {
		boolean[] results = {true, false, false, false, true};
		GamePiece[] pieces = createPieces();
		String errorMsg;
		for(int n = 0; n < pieces.length; n++) {
			errorMsg = "Piece " + pieces[n].getID() + "'s movable value should be " + results[n];
			assertEquals(results[n], pieces[n].canBeMoved() ,errorMsg);
		}
		//pass();
		//fail("Not yet implemented");
	}

}
