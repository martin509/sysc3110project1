package GameRunners;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameInternal.ContainerPiece;
import GameInternal.FoxBit;
import GameInternal.Game;
import GameInternal.Hill;
import GameInternal.Hole;
import GameInternal.Mushroom;
import GameInternal.Rabbit;

public class GUIView extends JFrame {
	private int height, width;
	private JButton[][] buttons;
	private JButton btnCancel, btnUndo, btnRedo;
	private JPanel grid, gameStatus, options;
	private JLabel txtStatus;

	// non jframe varibles
	private boolean gameWon = false;

	public final static int FRAME_WIDTH = 400, FRAME_HEIGHT = 400; // The frame sizes.

	public GUIView() {
		this.height = 0;
		this.width = 0;
		setResizable(false);

		// Set the top menu bar with game text
		gameStatus = new JPanel();
		txtStatus = new JLabel("This will hold move instructions and game status");
		gameStatus.add(txtStatus);
		add(gameStatus, BorderLayout.NORTH);

		// Set the bottom menu bar, which has game options like "cancel move".
		options = new JPanel();
		btnCancel = new JButton("Cancel Move");
		btnCancel.setEnabled(false);
		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		options.add(btnCancel);
		options.add(btnUndo);
		options.add(btnRedo);
		add(options, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		updateFrame();
	}

	/**
	 * @author Michael Create a new board (MUST be called at the start of the game).
	 * @param boardWidth
	 * @parem boardHeight
	 * @parem listener
	 */
	public void newBoard(int boardWidth, int boardHeight, ActionListener listenGridButton) {
		// Holds the board width and height
		width = boardWidth;
		height = boardHeight;

		// Set the board grid
		grid = new JPanel(new GridLayout(width, height));
		add(grid, BorderLayout.CENTER);

		// Add the buttons to the board grid.
		buttons = new JButton[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				buttons[x][y] = new JButton();
				buttons[x][y].addActionListener(listenGridButton);
				buttons[x][y].setBackground(new Color(0x009900));
				grid.add(buttons[x][y]);
			}
		}

		updateFrame();
	}

	/**
	 * @author Michael, Andrew Update the display of the board this happens when a
	 *         piece is selected or moved.
	 * @param board,      the game board
	 * @param isPieceSel, is their currently a gamepiece selected
	 */
	public void updateBoard(Game board, boolean isPieceSel) {
		if (board == null)
			throw new NullPointerException();

		if (board.isGameWon()) {
			gameWon = true;
		} else {
			gameWon = false;
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				// this sets the buttons for the 3 game states, Game won, piece selected, piece
				// not selected
				if (gameWon) {
					buttons[x][y].setEnabled(false);

					txtStatus.setText("You win, you beat the level.");
				} else if (isPieceSel) {
					buttons[x][y].setEnabled(true);
					txtStatus.setText("Now select a position for the piece to move.");

				} else {
					buttons[x][y].setEnabled(board.getPieceAt(new Point(x, y)) != null);
					txtStatus.setText("Select a Fox or a Rabbit.");
				}

				if (board.getPieceAt(new Point(x, y)) != null) { // Update the display of each grid for what piece it should be.

					if (board.getPieceAt(new Point(x, y)) instanceof FoxBit) {
						buttons[x][y].setText("FBit");
					} else if (board.getPieceAt(new Point(x, y)) instanceof Mushroom) {
						buttons[x][y].setText("M");
					} else if (board.getPieceAt(new Point(x, y)) instanceof Rabbit) {
						buttons[x][y].setText("R");
					} else if (board.getPieceAt(new Point(x, y)) instanceof ContainerPiece) {
						// For holes and Hills, have a separate display for empty and full holes, if
						// full what is inside.
						if ((board.getPieceAt(new Point(x, y)) instanceof Hole)) {
							if (((ContainerPiece) board.getPieceAt(new Point(x, y))).isEmpty()) {
								buttons[x][y].setText("HO( )");
							} else {
								if (((ContainerPiece) board.getPieceAt(new Point(x, y))).check() instanceof Rabbit) {
									buttons[x][y].setText("HO(R)");
								} else if (((ContainerPiece) board.getPieceAt(new Point(x, y))).check() instanceof Mushroom) {
									buttons[x][y].setText("HO(M)");
								}
							}
						} else if ((board.getPieceAt(new Point(x, y)) instanceof Hill)) {
							if (((ContainerPiece) board.getPieceAt(new Point(x, y))).isEmpty()) {
								buttons[x][y].setText("HI( )");
							} else {
								if (((ContainerPiece) board.getPieceAt(new Point(x, y))).check() instanceof Rabbit) {
									buttons[x][y].setText("HI(R)");
								} else if (((ContainerPiece) board.getPieceAt(new Point(x, y))).check() instanceof Mushroom) {
									buttons[x][y].setText("HI(M)");
								}
							}
						}

					}
				} else {
					buttons[x][y].setText("");
				}
			}
		}
	}

	/**
	 * @author Michael Update the main JFrame
	 */
	private void updateFrame() {
		setTitle("Jump In");
		setVisible(true);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}

	/**
	 * @author Michael This method is used for the actionlistener in the controller.
	 * @param btn
	 * @return The board coordinates of the given button. NULL if the button is not
	 *         found in the board.
	 */
	public Point getCoordinates(JButton btn) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (buttons[x][y].equals(btn)) {
					return new Point(x, y);
				}
			}
		}

		return null;
	}

	/**
	 * Gives the undo button an action listener.
	 * 
	 * @author Martin
	 * @param undoListener
	 */
	public void addUndoListener(ActionListener undoListener) {
		btnUndo.addActionListener(undoListener);
	}

	/**
	 * Gives the redo button an action listener.
	 * 
	 * @author Martin
	 * @param undoListener
	 */
	public void addRedoListener(ActionListener redoListener) {
		btnRedo.addActionListener(redoListener);
	}

	/**
	 * this is how the controller disables and enables the undo button
	 * 
	 * @author Martin
	 * @param set
	 */
	public void setUndoButton(boolean set) {
		btnUndo.setEnabled(set);
	}

	/**
	 * this is how the controller disables and enables the redo button
	 * 
	 * @author Martin
	 * @param set
	 */
	public void setRedoButton(boolean set) {
		btnRedo.setEnabled(set);
	}

	/**
	 * ]
	 * 
	 * @author Andrew this method is for giving the cancel button an action listener
	 * @param listenForCancelButton
	 */
	public void addCancelListener(ActionListener listenForCancelButton) {

		btnCancel.addActionListener(listenForCancelButton);
	}

	/**
	 * @author Andrew this is how the controller disables and enables the cancel
	 *         button
	 * @param set
	 */
	public void setCancelButton(boolean set) {
		btnCancel.setEnabled(set);
	}

	/**
	 * @author Andrew this changes the text that guides the player
	 * @param set
	 */
	public void setText(String set) {
		txtStatus.setText(set);
	}
}