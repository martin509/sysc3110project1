/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameInternal;

import java.util.ArrayList;

/**
 *
 * @author jweho
 */
public class Board {

    private GamePiece[][] board;
    private int boardWidth, boardHeight;

    public void Board(int width, int height) {
        board = new GamePiece[width][height];
        boardWidth = width;
        boardHeight = height;
        for (GamePiece[] x : board) {
            for (GamePiece y : x) {
                y = null;
            }
        }
    }

    private int getXLocation(GamePiece piece) {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(piece)) {
                    return j;
                }
            }
        }
        return -1;
    }

    private int getYLocation(GamePiece piece) {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(piece)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private GamePiece getPieceAt(int x, int y) {
        if (x >= boardWidth || x < 0 || y >= boardHeight || y < 0) {
            return null;
        } else {
            return board[x][y];
        }
    }

    public GamePiece getPieceWithID(String ID) {
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getID().equals(ID)) {
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public boolean addPiece(int x, int y, GamePiece piece) {
        if (x >= boardWidth || y >= boardHeight || x < 0 || y < 0) {
            return false;
        }
        if (board[x][y] == null) {
            if (getPieceWithID(piece.getID()) == null) {
                board[x][y] = piece;
                return true;
            } else {
                return false;
            }
        } else if (board[x][y] instanceof Hole && piece instanceof Rabbit) {
            Hole container = (Hole) board[x][y];
            container.putIn(piece);
            return true;
        } else {
            return false;
        }
    }

    public void removePieceAt(int x, int y) {
        board[x][y] = null;
    }

    public ArrayList<GamePiece> getPiecesOfType(GamePiece piece) {
        if (piece == null) {
            return null;
        }
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getClass().equals(piece.getClass())) {
                        pieces.add(board[i][j]);
                    } else if (board[i][j] instanceof ContainerPiece) {
                        ContainerPiece cont = (ContainerPiece) board[i][j];
                        if (cont.check().getClass().equals(piece.getClass())) {
                            pieces.add(cont.check());
                        }
                    }
                }
            }
        }
        return pieces;
    }

    public boolean move(GamePiece piece, Direction direction) {
        if (piece instanceof Rabbit) {
            int oldX = getXLocation(piece);
            int oldY = getYLocation(piece);
            if (oldX < 0 || oldX > boardWidth - 1 || oldY < 0 || oldY > boardWidth - 1) {
                return false;
            } else {
                GamePiece inFront = getAdjacentPiece(oldX, oldY, direction);
                if (inFront == null || inFront instanceof ContainerPiece) {
                    return false;
                } else {
                    int newX = getAdjacentXCoordinate(oldX, direction);
                    int newY = getAdjacentYCoordinate(oldY, direction);
                    while (!(inFront instanceof ContainerPiece) && inFront != null && newX >= 0 && newX < boardWidth && newY >= 0 && newY < boardHeight) {
                        inFront = getAdjacentPiece(newX, newY, direction);
                        newX = getAdjacentXCoordinate(newX, direction);
                        newY = getAdjacentYCoordinate(newY, direction);
                    }
                    if(newX < 0 || newX >= boardWidth || newY < 0 || newY >= boardHeight){
                        return false;
                    } else if (inFront instanceof ContainerPiece) {
                        ContainerPiece destination = (ContainerPiece) inFront;
                        destination.putIn(piece);
                        return true;
                    } else if (inFront == null) {
                        board[newY][newX]=piece;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getAdjacentXCoordinate(int x, Direction direction) {
        switch (direction) {
            case NORTH:
                return x;
            case SOUTH:
                return x;
            case EAST:
                return x + 1;
            case WEST:
                return x - 1;
        }
        return x;
    }

    private int getAdjacentYCoordinate(int y, Direction direction) {
        switch (direction) {
            case NORTH:
                return y + 1;
            case SOUTH:
                return y - 1;
            case EAST:
                return y;
            case WEST:
                return y;
        }
        return y;
    }

    private GamePiece getAdjacentPiece(int x, int y, Direction direction) {
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
