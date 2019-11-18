package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameInternal.Board;
import GameInternal.DIRECTION;
import GameInternal.Fox;
import GameInternal.FoxBit;
import GameInternal.Game;
import GameInternal.GamePiece;
import GameInternal.Hill;
import GameInternal.Hole;
import GameInternal.Mushroom;
import GameInternal.Rabbit;
import GameInternal.ContainerPiece;

/**
 * 
 * @author Martin, James Horner
 *
 */
public class Test_Game {
	Game g1;
	Game g2;

	@BeforeEach
	void setUp() {
		g1 = new Game(5,5);
		g2 = new Game(5,5);
	}
	@Test
	void testIsGameWon() {
		//g1 = new Game();
		assertFalse(g1.isGameWon());
		Rabbit r1 = (Rabbit) ((Hill) g1.getPieceAt(new Point(0, 2))).check();
		Rabbit r2 = (Rabbit) ((Hill) g1.getPieceAt(new Point(2, 4))).check();
		g1.move(r1, new Point(0,2), new Point(0, 4));
		g1.move(r2, new Point(2,4), new Point(4, 4));
		assertTrue(g1.isGameWon());
	}

	@Test
	void testGame() {
		//g2 = new Game();
		assertNotNull(g2);
		Class[][] shouldContain = { { Hole.class, null, Hill.class, null, Hole.class },
				{ null, FoxBit.class, null, null, null },
				{ Hill.class, FoxBit.class, Hole.class, null, Hill.class },
				{ Mushroom.class, null, null, FoxBit.class, null },
				{ Hole.class, null, Hill.class, FoxBit.class, Hole.class } };
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (g2.getPieceAt(new Point(j, i)) != null)
					assertEquals(g2.getPieceAt(new Point(j, i)).getClass(), shouldContain[i][j]);
			}
		}
	}
	
	@Test
	void testUndo() {
		Rabbit r1 = (Rabbit) ((Hill) g1.getPieceAt(new Point(0, 2))).check();
		g1.move(r1, new Point(0,2), new Point(0, 4));
		g1.undo();
		assertEquals(((Hill) g1.getPieceAt(new Point(0, 2))).check(),r1);
		assertTrue((((ContainerPiece) g1.getPieceAt(new Point(0,4))).isEmpty()));
	}
	@Test
	void testRedo() {
		Rabbit r1 = (Rabbit) ((Hill) g1.getPieceAt(new Point(0, 2))).check();
		g1.move(r1, new Point(0,2), new Point(0, 4));
		g1.undo();
		assertEquals(((Hill) g1.getPieceAt(new Point(0, 2))).check(),r1);
		assertTrue((((ContainerPiece) g1.getPieceAt(new Point(0,4))).isEmpty()));
		g1.redo();
		assertFalse((((ContainerPiece) g1.getPieceAt(new Point(0,4))).isEmpty()));
		assertNull(((Hill) g1.getPieceAt(new Point(0, 2))).check());
	}
}
