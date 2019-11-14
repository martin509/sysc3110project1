package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Before;
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

/**
 * 
 * @author Martin, James Horner
 *
 */
public class Test_Game {
	Game g1;
	Game g2;
		
	@Test
	void testIsGameWon() {
		g1 = new Game();
		assertFalse(g1.isGameWon());
		Rabbit r1 = (Rabbit)((Hill) g1.getPieceAt(new Point(0, 2))).check();
		Rabbit r2 = (Rabbit)((Hill)g1.getPieceAt(new Point(2, 4))).check();
		g1.move(r1, DIRECTION.SOUTH, 2);
		g1.move(r2, DIRECTION.EAST, 2);
		assertTrue(g1.isGameWon());
	}
	@Test
	void testGame() {
		g2 = new Game();
		assertNotNull(g2);
		GamePiece[][] shouldContain = {{new Hole(), new FoxBit(null), new Hill(), null, new Hole()},
				{null,new Fox(2,DIRECTION.SOUTH),null,null,null},
				{new Hill(),null,new Hole(),null, new Hill()},
				{new Mushroom(),null,null,new Fox(2,DIRECTION.NORTH),null},
				{new Hole(), null, new Hill(), null, new Hole()}
		};
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(g2.getPieceAt(new Point(j, i)) != null)
					assertEquals(g2.getPieceAt(new Point(j, i)).getClass(),shouldContain[i][j].getClass());
			}
		}
	}
}
