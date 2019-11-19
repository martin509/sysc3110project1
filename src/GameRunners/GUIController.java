package GameRunners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import GameInternal.DIRECTION;
import GameInternal.Fox;
import GameInternal.FoxBit;
import GameInternal.Game;
import GameInternal.MovablePiece;
import GameInternal.Rabbit;

public class GUIController {

	private Game theModel;
	private GUIView theView;
	private DIRECTION direction;

	// ----------------
	private boolean isPieceSel;
	private Point pieceToMove;
	private Point endLocation;
	private int distance;
	private Fox tempFox;
	boolean canMove;

	/**
	 * @author Andrew
	 * 
	 *         The idea of this class is that you select a grid button, at which
	 *         point isPieceSel is set to true, then you have two choices. Either
	 *         press the cancel button to undo the selection made or select a
	 *         different grid button and if the move can be made, move the piece at
	 *         which point the system starts all over again
	 * @param theModel
	 * @param theView
	 * @param x        width of board
	 * @param y        length of board
	 */
	public GUIController(Game theModel, GUIView theView, int x, int y) {

		SetUp(theModel, theView, x, y);
	}

	// the default Constructor. The board size is 5x5
	public GUIController(Game theModel, GUIView theView) {

		SetUp(theModel, theView, 5, 5);
	}

	private void SetUp(Game theModel, GUIView theView, int x, int y) {
		this.theModel = theModel;
		this.theView = theView;
		this.theView.newBoard(x, y, new GridListener());
		this.theView.addCancelListener(new CancelListener());
		this.theView.addUndoListener(new UndoListener());
		this.theView.addRedoListener(new RedoListener());
		this.theModel = new Game(5, 5);

		// non model/view stuff
		isPieceSel = false;
		pieceToMove = new Point();
		endLocation = new Point();
		theView.setText("Select a Fox or a Rabbit");

		update();
	}

	/**
	 * Helper function for all the identical theView.updateBoard() calls.
	 */
	private void update() {
		theView.updateBoard(theModel, isPieceSel);
		theView.setUndoButton(theModel.canUndo());
		theView.setRedoButton(theModel.canRedo());
	}

	class CancelListener implements ActionListener {

		// when the cancel button is pressed
		@Override
		public void actionPerformed(ActionEvent arg0) {
			isPieceSel = false;
			theView.updateBoard(theModel, isPieceSel);
			theView.setCancelButton(false);
			theView.setText("Select a Fox or a Rabbit");

		}

	}

	/**
	 * @author Martin
	 */
	class UndoListener implements ActionListener {
		// when the undo button is pressed
		@Override
		public void actionPerformed(ActionEvent arg0) {
			theModel.undo();
			update();
			theView.setUndoButton(theModel.canUndo());
			theView.setText("Move undone!");

		}
	}

	/**
	 * @author Martin
	 */
	class RedoListener implements ActionListener {
		// when the undo button is pressed
		@Override
		public void actionPerformed(ActionEvent arg0) {
			theModel.redo();
			update();
			theView.setRedoButton(theModel.canRedo());
			theView.setText("Move redone!");

		}
	}

	class GridListener implements ActionListener {

		// when a button on the grid is pressed
		@Override
		public void actionPerformed(ActionEvent e) {
			if (isPieceSel) { // if there already is a selected piece

				// get end location and check if the piece is in line
				endLocation = theView.getCoordinates((JButton) e.getSource()); // these are the co-ords of the end position of the selected piece

				if (theModel.getPieceAt(pieceToMove) instanceof FoxBit) {

					if (theModel.move(tempFox, pieceToMove, endLocation)) {
						tempFox = null;
						canMove = true;
					} else {
						canMove = false;
					}
				} else {

					if (theModel.move((MovablePiece) theModel.getPieceAtAdvanced(pieceToMove), pieceToMove, endLocation)) {
						canMove = true;
					} else {
						canMove = false;
					}
				}

				if (canMove) {
					// if the move can be made, move the piece
					theView.setCancelButton(false);
					isPieceSel = false;
					update();
				} else {
					// if the move can't be made
					isPieceSel = false;
					update();
					theView.setText("Move couldn't be made, select new piece");
					theView.setCancelButton(false);
				}
			} else { // if the no piece has been selected yet

				pieceToMove = theView.getCoordinates((JButton) e.getSource()); // this gets the co-ords of the piece that we want to move

				if ((theModel.getPieceAt(pieceToMove) instanceof FoxBit) || (theModel.getPieceAtAdvanced(pieceToMove) instanceof Rabbit)) { // is this piece movable?

					if (theModel.getPieceAt(pieceToMove) instanceof FoxBit) {
						tempFox = ((FoxBit) theModel.getPieceAt(pieceToMove)).getFox();

					}

					isPieceSel = true;
					update();
					theView.setCancelButton(true);

				}

			}

		}

	}
}
