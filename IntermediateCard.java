import java.util.ArrayList;
/**
 * Write a description of class IntermediateCard here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class IntermediateCard
{
    protected Piece theBoard[][];
    protected ArrayList<Piece> piecesToAdd;
    /**
     * Constructor for objects of class CardOne
     */
    public IntermediateCard()
    {
        theBoard = new Piece[5][5];
        theBoard[0][3] = new PurplePiece(Orientation.UNKNOWN, true, false, true);
        theBoard[3][3] = new PurplePiece(Orientation.RIGHT, false, false, true);
        theBoard[1][1] = new RedPiece(Orientation.UNKNOWN, true);
        piecesToAdd = new ArrayList<Piece>();
        piecesToAdd.add(new PurplePiece(Orientation.UNKNOWN, true, true, false));
        piecesToAdd.add(new PurplePiece(Orientation.UNKNOWN, true, true, false));
        piecesToAdd.add(new GreenPiece());
    }

    public void addPiece(int row, int col, int indexOfArrayList) {
        theBoard[row][col] = piecesToAdd.get(indexOfArrayList);
    }

    public boolean movePiece(int startRow, int startCol, int endRow, int endCol){
        if (theBoard[startRow][startCol] == null || theBoard[startRow][startCol].isMovable == false || theBoard[endRow][endCol] != null) {
            return false;
        }
        else {
            theBoard[endRow][endCol] = theBoard[startRow][startCol];
            theBoard[startRow][startCol] = null;
            return true;
        }
    }

    public boolean rotatePiece(int row, int col) {
        if (theBoard[row][col] == null || theBoard[row][col].isRotatable == false) {
            return false;
        }
        else {
            theBoard[row][col].rotate();
            return true;
        }
    }

    public boolean isValidSolution() {
        return (theBoard[1][1].orient == Orientation.RIGHT && theBoard[0][3].orient == Orientation.DOWN && (theBoard[1][3].orient ==
                Orientation.UP || theBoard[1][3].orient == Orientation.DOWN) && theBoard[1][4].orient == Orientation.UP && 
            theBoard[3][4].orient == Orientation.RIGHT && piecesToAdd.get(0) == theBoard[1][4] && piecesToAdd.get(1) == theBoard[3][4]
            && piecesToAdd.get(2) == theBoard[1][3]);
        
    } 

    public static void main (String args[]) {
        IntermediateCard b1 = new IntermediateCard();
        //Is it a solution with no pieces added
        System.out.println("Starter board is valid solution: " + b1.isValidSolution());
        //Adding the first purple piece to [0][0]
        b1.addPiece(0, 0, 0);
        //Move the purple piece to correct spot
        b1.movePiece(0,0,1,4);
        //Adding the second purple piece to [3][4]
        b1.addPiece(3, 4, 1);
        //Adding the green piece to [1][3]
        b1.addPiece(1, 3, 2);
        System.out.println("Added all pieces to correct spot, but wrong orientation: " + b1.isValidSolution());
        //Rotating the Laser
        b1.rotatePiece(1, 1);
        b1.rotatePiece(1, 1);
        //Rotating the rotatable purple piece
        b1.rotatePiece(0, 3);
        b1.rotatePiece(0, 3);
        b1.rotatePiece(0, 3);
        //Rotating purple piece at [1][4]
        b1.rotatePiece(1, 4);
        //Rotating purple piece at [3][4]
        b1.rotatePiece(3, 4);
        b1.rotatePiece(3, 4);
        //Rotating green piece at [1][3]
        b1.rotatePiece(1, 3);
        System.out.println("Added all pieces to correct spot, with correct orientation: " + b1.isValidSolution());
    }
}
