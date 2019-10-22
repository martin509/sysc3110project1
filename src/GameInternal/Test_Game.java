package GameInternal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class Test_Game {
	@Test
	void testGenIDs() {
		Game game1 = new Game(5, 5);
		assertEquals("Fox 1", game1.genNewID(PieceType.FOX_EW),"First Fox should be FOX1");
		assertEquals("Fox 2", game1.genNewID(PieceType.FOX_EW),"2nd Fox should be FOX2");
		assertEquals("Fox 3", game1.genNewID(PieceType.FOX_NS),"3rd Fox should be FOX3");
		assertEquals("Rabbit 1", game1.genNewID(PieceType.RABBIT),"1st Rabbit should be RABBIT1");
	}
	
	@Test
	void testgetPiecesOfType() {
		Game game1 = new Game(5, 5);
		assertEquals(true, game1.addPiece(1, 0, PieceType.RABBIT));
		assertEquals(true, game1.addPiece(1, 1, PieceType.RABBIT));
		assertEquals(true, game1.addPiece(2, 2, PieceType.RABBIT));
		assertEquals(true, game1.addPiece(2, 3, PieceType.MUSHROOM));
		assertEquals(3, game1.getPiecesOfType(PieceType.RABBIT).size(), "Should get 3 pieces.");
	}
	/*void testAddPieces() {
		Game game1 = new Game(5, 5);
	}*/
}
