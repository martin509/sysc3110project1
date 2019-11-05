package GameRunners;

import GameInternal.Game;
/**
 * 
 * @author Andrew
 * 
 * This is where the game will run.
 *
 */
public class GUIGame {

	public static void main(String[] args) {

		GUIView theView = new GUIView();
		Game theModel = new Game();
		new GUIController(theModel, theView);
		
		theView.setVisible(true);
	}

}
