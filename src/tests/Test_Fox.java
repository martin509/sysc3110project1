package tests;

import static org.junit.Assert.*;

import org.junit.*;

import GameInternal.DIRECTION;
import GameInternal.Fox;
import GameInternal.FoxBit;
import GameInternal.MovablePiece;

public class Test_Fox {
	Fox f1;
	Fox f2;
	Fox f3;

	@Before
	public void setUp() {
		f1 = new Fox(2, DIRECTION.NORTH);
	}

	@Test
	public void testFox() {
		assertEquals(f1.getLength(),2);
		assertEquals(f1.getAxisForward(),DIRECTION.NORTH);
		assertEquals(f1.getAxisBackward(),DIRECTION.SOUTH);
		assertNotNull(f1.getHead());
		assertEquals(f1.getHead().getFox(),f1);
		assertNull(f1.getHead().getAhead());
		assertNotNull(f1.getHead().getBehind());
		assertNull(f1.getHead().getBehind().getBehind());
		assertEquals(f1.getHead().getBehind().getFox(),f1);
		assertEquals(f1.getHead().getBehind(),f1.getHead().getTail());
		assertEquals(f1.getHead(),f1.getHead().getBehind().getHead());
	}
	@Test
	public void testGetAxisForward() {
		assertEquals(f1.getAxisForward(),DIRECTION.NORTH);
	}
	@Test
	public void testGetAxisBackward() {
		assertEquals(f1.getAxisBackward(),DIRECTION.SOUTH);
	}
	@Test
	public void testGetHead() {
		assertNotNull(f1.getHead());
		assertEquals(f1.getHead().getFox(),f1);
		assertNull(f1.getHead().getAhead());
		assertNotNull(f1.getHead().getBehind());
		assertNull(f1.getHead().getBehind().getBehind());
		assertEquals(f1.getHead().getBehind().getFox(),f1);
	}
	@Test
	public void testGetLength() {
		assertEquals(f1.getLength(),2);
	}

}
