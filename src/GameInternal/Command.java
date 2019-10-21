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

public class Command {
	public enum Actions{};
	
	private GamePiece targetPiece;
	private Actions action;
	
	/**
	 * Creates a new command by performing an inline command input.
	 * @parem targetPiece
	 * @parem action
	 */
	public Command(GamePiece targetPiece, Actions action){
		this.targetPiece = targetPiece;
		this.action = action;
	}
	
	/**
	 * @returns the action of the command.
	 */
	public Actions getAction(){
		return this.action;
	}
	
	/**
	 * @returns the ID of the target.
	 */
	public String getTarget(){
		return this.targetPiece.getID();
	}
	
	/**
	 * @returns The target itself
	 */
	public String getTargetClass(){
		return this.targetPiece;
	}
}