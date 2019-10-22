/**
 * @(#)Command.java
 *
 *
 * @Michael Fairbairn
 * Holds a command
 *  
 * @version 1.00 2019/10/20
 */

 package GameInternal;
 import java.util.Scanner;

public class Command {
	private Game g;
	private GamePiece targetPiece;
	private int goX, goY;
	
	public Command(Game g) { //This will add a new command based on a user input that will be prompted when run.
		this(processFromInput(g));
	}
	public Command(Command c) {
		this(c.getGame(), c.getTarget(), c.getNewX(), c.getNewY());
	}
	public Command(Game g, GamePiece targetPiece, int goX, int goY){
		this.targetPiece = targetPiece;
		this.goX = goX;
		this.goY = goY;
	}
	
	/**
	 * @return The x-position to move the piece.
	 */
	public int getNewX() {
		return this.goX;
	}
	
	/**
	 * @return The y-position to move the piece.
	 */
	public int getNewY() {
		return this.goY;
	}
	
	/**
	 * @returns The target itself.
	 */
	public GamePiece getTarget(){
		return this.targetPiece;
	}
	
	/**
	 * @returns the ID of the target.
	 */
	public String getTargetID(){
		return this.targetPiece.getID();
	}
	
	public Game getGame() {
		return this.g;
	}
	
	/**
	 * Execute the given command.
	 */
	public GamePiece[][] execute() {
		String type = getTargetID().substring(0, getTargetID().indexOf(' '));
		
		if (type.equals("Fox")) {
			g.moveFox((Fox)targetPiece, goX, goY);
			
		}
		else {
			g.moveRabbit((Rabbit)targetPiece, goX, goY);
		}
		return g.getBoard();
	}
	
	/**
	 * Create a new command prompt and convert said prompt into a command.
	 * All commands are in the form of "move <ID> x y"
	 * @param g The game to access 
	 * @return The command from the prompt
	 */
	private static Command processFromInput(Game g) {
		Scanner tempIn = new Scanner(System.in);
		Scanner cmd = new Scanner(tempIn.next()); //The scanner of a command that the user is prompted to type
		tempIn.close();
		
		if (!cmd.next().equals("move")) {
			System.out.println("Invalid command, try again: ");
			return processFromInput(g);
		}
		
		String tempID = cmd.next();
		
		if (!tempID.equals("Fox") && !tempID.equals("Rabbit")) {
			System.out.println("Invalid command, try again: ");
			return processFromInput(g);
		}
		
		tempID += " " + cmd.next();
		GamePiece tempPiece = g.getPiece(tempID);
		int tempX = 0, tempY = 0;
		try {
			tempX = Integer.parseInt(cmd.next());
			tempY = Integer.parseInt(cmd.next());
		}catch (Exception e) {
			System.out.println("Invalid command, try again: ");
			return processFromInput(g);
		}
		
		cmd.close();
		return new Command(g, tempPiece, tempX, tempY);
	}
}