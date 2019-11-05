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
		Board board = g1.getBoard();
		Rabbit r1 = (Rabbit)((Hill)board.getPieceAt(0, 2)).check();
		Rabbit r2 = (Rabbit)((Hill)board.getPieceAt(2, 4)).check();
		board.move(r1, DIRECTION.SOUTH, 2);
		board.move(r2, DIRECTION.EAST, 2);
		assertTrue(g1.isGameWon());
	}
	@Test
	public void testGetBoard() {
		g1 = new Game();
		assertNotNull(g1.getBoard());
	}
	@Test
	void testGame() {
		g2 = new Game();
		assertNotNull(g2);
		assertNotNull(g2.getBoard());
		Board board = g2.getBoard();
		GamePiece[][] shouldContain = {{new Hole(), new FoxBit(null), new Hill(), null, new Hole()},
				{null,new Fox(2,DIRECTION.SOUTH),null,null,null},
				{new Hill(),null,new Hole(),null, new Hill()},
				{new Mushroom(),null,null,new Fox(2,DIRECTION.NORTH),null},
				{new Hole(), null, new Hill(), null, new Hole()}
		};
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(board.getPieceAt(i, j)!=null)
					assertEquals(board.getPieceAt(i, j).getClass(),shouldContain[j][i].getClass());
			}
		}
	}
}

