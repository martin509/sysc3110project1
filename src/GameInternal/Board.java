package GameInternal;

import java.util.ArrayList;

/**
 * Class Board serves as a container for all the GamePieces in the game and provides methods for manipulating them.
 * @author James Horner
 */
public class Board {

    private GamePiece[][] board;
    private int boardWidth, boardHeight;

    public void Board(int width, int height) {
        board = new GamePiece[width][height];
        boardWidth = width;
        boardHeight = height;
        for (GamePiece[] x : board) {//initialize the board to be all null;
            for (GamePiece y : x) {
                y = null;
            }
        }
    }
    /**
     * Method getXLocation provides a way to find the x coordinate of a piece on the board.
     * @param piece GamePiece that is being searched for.
     * @param x boolean true if the x coordinate is to be returned, false for y.
     * @return int for the coordinate of the piece or -1 if no piece is found.
     */
    private int getLocation(GamePiece piece, boolean x) {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < board[i].length; j++) {//iterate through the board.
                if (board[i][j].equals(piece)) {//until a piece matching the piece provided is found.
                	if(x) {return j;}//return the x coordinate of the piece.
                	else {return i;}
                }
            }
        }
        return -1;//if not found return -1.
    }
    
    /**
     * Method getPieceAt returns the piece at the specific grid coordinate.
     * @param x int for x coordinate on the grid.
     * @param y int for y coordinate on the grid.
     * @return GamePiece at the specified grid coordinate null if no piece exists there.
     */
    private GamePiece getPieceAt(int x, int y) {
        if (x >= boardWidth || x < 0 || y >= boardHeight || y < 0) {//check if coordinates are on the board.
            return null;
        } else {
            return board[x][y];
        }
    }
    
    /**
     * Method getPieceWithID is used to find a piece on the board with a specific ID.
     * @param ID String for the ID of the piece being looked for.
     * @return GamePiece on the board with matching ID.
     */
    public GamePiece getPieceWithID(String ID) {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < board[i].length; j++) {//iterate through board.
                if (board[i][j].getID().equals(ID)) {//until a matching piece is found.
                    return board[i][j];
                }
            }
        }
        return null;//return null if board does not contain a matching piece.
    }
    
    /**
     * Method addPiece adds a piece to a specific location on the board.
     * @param x int for x coordinate of the piece.
     * @param y into for y coordinate of the piece.
     * @param piece GamePiece to be placed on the board. 
     * @return boolean for whether the piece could be successfully added.
     */
    public boolean addPiece(int x, int y, GamePiece piece) {
        if (x >= boardWidth || y >= boardHeight || x < 0 || y < 0) {//check that the coordinates are valid.
            return false;
        }
        if (board[x][y] == null) {//if there is not piece that is at the coordinates already. 
            if (getPieceWithID(piece.getID()) == null) {//check if there are any pieces on the board with same ID.
                board[x][y] = piece;//if not add the piece.
                return true;
            } else {
                return false;
            }
        } else if (board[x][y] instanceof Hole && piece instanceof Rabbit) {//if the piece at the coordinates is a hole and the piece being added is a rabbit.
            Hole container = (Hole) board[x][y];
            return container.putIn(piece);//try adding the rabbit to the hole and return the result.
        } else {
            return false;//default to false.
        }
    }
    
    /**
     * Method removePieceAt removes a piece at a certain location.
     * @param x int for x coordinate of the piece.
     * @param y int for y coordinate of the piece.
     */
    public void removePieceAt(int x, int y) {
        board[x][y] = null;
    }

    /**
     * Method getPiecesOfType is used to find objects of a certain type and return them.
     * @param piece GamePiece used to identify the type of piece being requested.
     * @return ArrayList of all the pieces found on the board of the specified type.
     */
    public ArrayList<GamePiece> getPiecesOfType(GamePiece piece) {
        if (piece == null) {
            return null;
        }
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {//iterate through board
                if (board[i][j] != null) {
                    if (board[i][j].getClass().equals(piece.getClass())) {//check if piece at location has the same class.
                        pieces.add(board[i][j]);//if it does add it to the array
                    } else if (board[i][j] instanceof ContainerPiece) {//if the piece is a ContainerPiece
                        ContainerPiece cont = (ContainerPiece) board[i][j];
                        if (cont.check().getClass().equals(piece.getClass())) {//check the contents 
                            pieces.add(cont.check());//add to the array if it matches the class
                        }
                    }
                }
            }
        }
        return pieces;
    }
    
    /**
     * Method move enables the movement of pieces around the board following the rules of the game.
     * @param piece MovablePiece to be moved.
     * @param direction Direction in which the piece is to be moved.
     * @return boolean for if the movement is successful.
     */
    public boolean move(MovablePiece piece, DIRECTION direction) {
        if (piece instanceof Rabbit) {//if the piece is a rabbit
            int oldX = getLocation(piece,true);
            int oldY = getLocation(piece,false);
            if (oldX < 0 || oldX >= boardWidth || oldY < 0 || oldY >= boardWidth) {//check if the location is valid
                return false;
            } else {
                GamePiece inFront = getAdjacentPiece(oldX, oldY, direction);//get the piece beside the rabbit in the specified direction.
                if (inFront == null || inFront instanceof ContainerPiece) {//cannot jump to an immediately adjacent empty space or hole.
                    return false;
                } else {//if not beside an empty space or hole.
                    int newX = getAdjacentCoordinate(oldX, direction,true);//keep track of the current coordinates of the adjacent piece.
                    int newY = getAdjacentCoordinate(oldY, direction,false);
                    
                    //iterate through the adjacent pieces until a hole or empty space is reached or the piece being checked is off the board. 
                    while (!(inFront instanceof ContainerPiece) && inFront != null && newX >= 0 && newX < boardWidth && newY >= 0 && newY < boardHeight) {
                        inFront = getAdjacentPiece(newX, newY, direction);
                        newX = getAdjacentCoordinate(newX, direction,true);
                        newY = getAdjacentCoordinate(newY, direction,false);
                    }
                    
                    if(newX < 0 || newX >= boardWidth || newY < 0 || newY >= boardHeight){//if the piece being check is off the board
                        return false;
                    } else if (inFront instanceof ContainerPiece) {//if the piece being checked is a hole
                        ContainerPiece destination = (ContainerPiece) inFront;
                        return destination.putIn(piece);//try adding the rabbit to the hole.
                    } else if (inFront == null) {//if the space is empty
                        board[newY][newX]=piece;//add the rabbit to the empty space.
                        return true;
                    }
                }
            }
        }
        return false;//default to false.
    }

    /**
     * Method getAdjacentCoordinate is used to increment a grid location based on a direction.
     * @param n int for x or y coordinate based on the isX parameter. 
     * @param direction DIRECTION for the direction of the adjacent location.
     * @param isX boolean for whether n is an x coordinate (true) or a y coordinate (false).
     * @return
     */
    private int getAdjacentCoordinate(int n, DIRECTION direction, boolean isX) {
        switch (direction) {
            case NORTH:
            	if(isX) {return n;}
            	else {return n+1;}
            case SOUTH:
            	if(isX) {return n;}
            	else {return n-1;}
            case EAST:
            	if(isX) {return n+1;}
            	else {return n;}
            case WEST:
            	if(isX) {return n-1;}
            	else {return n;}
        }
        return n;
    }
    /**
     * Method getAdjacentPiece is used to get an adjacent GamePiece to a grid coordinate
     * @param x int for the x coordinate of the reference piece.
     * @param y int for the y coordinate of the reference piece.
     * @param direction DIRECTION of the adjacent piece to the reference piece.
     * @return GamePiece for adjacent piece, null if there is no piece or if piece is outside of the board.
     */
    private GamePiece getAdjacentPiece(int x, int y, DIRECTION direction) {
        switch (direction) {
            case NORTH:
                return getPieceAt(x, y + 1);
            case SOUTH:
                return getPieceAt(x, y - 1);
            case EAST:
                return getPieceAt(x + 1, y);
            case WEST:
                return getPieceAt(x - 1, y);
        }
        return null;
    }
}
//code that was removed from a previous iteration please ignore
/*
    public boolean moveRabbit (Rabbit piece, int x, int y){
    //this works the same way as moveFox, but without all the extra bits
        if(canMove(piece, x, y)){
            piece.setX(x);
            piece.setY(y);
            board[x][y] = piece;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean moveFox(Fox piece, int x, int y){
        if(canMove(piece, x, y)){ // can the fox move to where I want to place it
            boolean isVert;
            // is the movement vertical or horizonal
            if(piece.getAxis() == DIRECTION.EAST_WEST){
                isVert = false;
            } else {
                isVert = true;
            }

            // used the hold the current bit being moved
            FoxBit currBit = piece.getHead();

            // go through a move each bit
            for(int i = 0; i < piece.getLength(); i++){
                if(i == 0){ // moving the head and the fox location
                    piece.setX(x);
                    piece.setY(y);
                    currBit.setX(x);
                    currBit.setY(y);
                    board[x][y] = currBit;
                } else { // moving the rest of the body
                    if(isVert){
                        currBit.setX(x);
                        currBit.setY(y - i);
                        board[x][y - i] = currBit;
                    } else {
                        currBit.setX(x - i);
                        currBit.setY(y);
                        board[x - i][y] = currBit;
                    }
                }
                currBit = currBit.getBehind();
            }
            return true;
        }else{ // the move is not possible
            return false;
        }
    }

 */
