package GameRunners;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GameInternal.Fox;
import GameInternal.FoxBit;
import GameInternal.GamePiece;
import GameInternal.Hill;
import GameInternal.Hole;
import GameInternal.Mushroom;
import GameInternal.Rabbit;

public class GUIView extends JFrame {
	private int height, width;
	private JButton[][] buttons;
	private JButton btnCancel;
	private JPanel grid, gameStatus, options;
	private JLabel txtStatus;

	public final static int FRAME_WIDTH = 400, FRAME_HEIGHT = 400; // The frame sizes.

	public GUIView() {
		this.height = 0;
		this.width = 0;
		setResizable(false);

		// Set the top menu bar with game text.
		gameStatus = new JPanel();
		txtStatus = new JLabel("This will hold move instructions and game status");
		gameStatus.add(txtStatus);
		add(gameStatus, BorderLayout.NORTH);

		// Set the bottom menu bar, which has game options like "cancel turn".
		options = new JPanel();
		btnCancel = new JButton("Cancel Move");
		btnCancel.setEnabled(false);
		options.add(btnCancel);
		add(options, BorderLayout.SOUTH);

		updateFrame();
	}

	/**
	 * @author Michael Create a new board (MUST be called at the start of the game).
	 * @param boardWidth
	 * @parem boardHeight
	 * @parem listener
	 */
	public void newBoard(int boardWidth, int boardHeight, ActionListener listener) {
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
				buttons[x][y].addActionListener(listener);
				grid.add(buttons[x][y]);
			}
		}

		updateFrame();
	}

	/**
	 * @author Michael Update the display of the board.
	 * @param board
	 */
	public void updateBoard(GamePiece[][] board) {
		if (board == null)
			throw new NullPointerException();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				buttons[x][y].setEnabled(board[x][y] != null);

				if (board[x][y] != null) { // Update the display of each grid for what piece it should be.
					if (board[x][y] instanceof Fox) {
						buttons[x][y].setText("FBit");
					} else if (board[x][y] instanceof FoxBit) {
						buttons[x][y].setText("F");
					} else if (board[x][y] instanceof Hill) {
						buttons[x][y].setText("H");
					} else if (board[x][y] instanceof Hole) {
						// For holes, have a separate display for empty and full holes.
						if (((Hole) board[x][y]).canEnter()) {
							buttons[x][y].setText("( )");
						} else {
							buttons[x][y].setText("(R)");
						}
					} else if (board[x][y] instanceof Mushroom) {
						buttons[x][y].setText("M");
					} else if (board[x][y] instanceof Rabbit) {
						buttons[x][y].setText("R");
					}
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
}