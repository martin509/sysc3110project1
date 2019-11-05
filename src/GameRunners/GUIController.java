package GameRunners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import GameInternal.DIRECTION;
import GameInternal.Game;
import GameInternal.MovablePiece;
import GameInternal.Rabbit;
import GameInternal.Fox;

public class GUIController {

	private Game theModel;
	private GUIView theView;
	private DIRECTION direction;
	
	//----------------
	private boolean isPieceSel;
	private Point pieceToMove;
	private Point endLocation;
	private int distance;
	
	/**
	 * @author Andrew 
	 * 
	 * The idea of this class is that you select a grid button, at which point isPieceSel is set to true, then you have two choices.
	 * Either press the cancel button to undo the selection made or select a different grid button and if the move can be made, move the piece
	 * at which point the system starts all over again
	 * @param theModel
	 * @param theView
	 * @param x width of board
	 * @param y length of board
	 */
	public GUIController (Game theModel, GUIView theView, int x, int y) {
		
		SetUp(theModel, theView, x, y);
	}
	
	// the default Constructor. The board size is 5x5
	public GUIController (Game theModel, GUIView theView) {

		SetUp(theModel, theView, 5, 5);	
	}
	
	private void SetUp(Game theModel, GUIView theView, int x, int y) {
		this.theModel = theModel;
		this.theView = theView;
		this.theView.newBoard(x, y, new GridListener());
		this.theView.addCancelListener(new CancelListener());
		this.theModel = new Game ();
		this.theView.updateBoard(theModel.getBoard().getBoard());
		
		// non model/view stuff
		isPieceSel = false;
		pieceToMove = new Point();
		endLocation = new Point();
	}
	
	class CancelListener implements ActionListener{

		//when the cancel button is pressed
		@Override
		public void actionPerformed(ActionEvent arg0) {
			isPieceSel = false;
			theView.SetCancelButton(false);
			
		}
		
	}
	
	class GridListener implements ActionListener{

		//when a button on the grid is pressed
		@Override
		public void actionPerformed(ActionEvent e) {
			if(isPieceSel) { // if there already is a selected piece
				
				//get end location and check if the piece is in line
				endLocation = theView.getCoordinates((JButton)e.getSource()); // these are the co-ords of the end position of the selected piece
				// find direction
				if((endLocation.x != pieceToMove.x) && (endLocation.y == pieceToMove.y)) { // if the movement is left or right
					
					distance = Math.abs(endLocation.x - pieceToMove.x);
					if(pieceToMove.x < endLocation.x) {
						direction = DIRECTION.EAST;
					} else {
						direction = DIRECTION.WEST;
					}
					
				} else if((endLocation.x == pieceToMove.x) && (endLocation.y != pieceToMove.y)) { // if movement is up or down
					
					distance = Math.abs(endLocation.y - pieceToMove.y);
					if(pieceToMove.y < endLocation.y) {
						direction = DIRECTION.SOUTH;
					} else {
						direction = DIRECTION.NORTH;
					}
				} else {
					distance = 0; // if the endLocation is not a valid choice
				}
				//------------------------------------------------------------------------------------------
				
				if(distance !=0 ) { // if the endLocation is valid
					if(theModel.getBoard().
							move((MovablePiece)theModel.getBoard().getPieceAt(pieceToMove.x, pieceToMove.y), direction, distance)) {
						// if the move can be made, move the piece
						theView.SetCancelButton(false);
						isPieceSel = false;
						theView.updateBoard(theModel.getBoard().getBoard());
					}
				}	
			} else { // if the no piece has been selected yet
				
				pieceToMove = theView.getCoordinates((JButton)e.getSource()); //this gets the co-ords of the piece that we want to move
				
				if((theModel.getBoard().getPieceAt(pieceToMove.x, pieceToMove.y) instanceof Fox) || 
						(theModel.getBoard().getPieceAt(pieceToMove.x, pieceToMove.y) instanceof Rabbit)) { //is this piece movable?
					isPieceSel = true;
					theView.SetCancelButton(true);
				}
				
			}
			
		}
		
	}
}
