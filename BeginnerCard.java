 
/**
 * Write a description of class CardOne here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BeginnerCard
{

    protected Piece theBoard[][];
    protected Piece pieceToAdd;
    /**
     * Constructor for objects of class CardOne
     */
    public BeginnerCard()
    {
        theBoard = new Piece[5][5];
        theBoard[0][4] = new PurplePiece(Orientation.DOWN, false, false, true);
        theBoard[3][0] = new PurplePiece(Orientation.LEFT, false, false, false);
        theBoard[4][0] = new RedPiece(Orientation.UNKNOWN, true);
        pieceToAdd = new PurplePiece();
    }

    public void addPiece(int row, int col) {
        theBoard[row][col] = pieceToAdd;
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
            Piece p = theBoard[row][col];
            p.rotate();
            return true;
        }
    }

    public boolean isValidSolution() {
        //hardcoded solution
        return (pieceToAdd == theBoard[3][4] && theBoard[4][0].orient == Orientation.UP && theBoard[3][4].orient == Orientation.RIGHT);
        
        
        
        // // not finished if unplaced pieces exist
        
        // //not finished if any pieces unhit
        // for (int i = 0; i <= 4; i++) {
            // for (int j = 0; j <= 4; j++) {
                // if (theBoard[i][j] != null && !theBoard[i][j].isHit) {
                    // return false;
                // }
            // }
        // }
        // return true;
    }
    
    public static void main (String args[]) {
        BeginnerCard b1 = new BeginnerCard();
        System.out.println("Starter board is valid solution: " + b1.isValidSolution());
        b1.addPiece(0, 0);
        System.out.println("added to (0,0) is valid solution: " + b1.isValidSolution());
        b1.movePiece(0, 0, 3, 4);
        System.out.println("moved without rotating is valid solution: " + b1.isValidSolution());
        b1.rotatePiece(3, 4);
        b1.rotatePiece(3, 4);
        b1.rotatePiece(4, 0);
        System.out.println("Correct and Rotated is valid solution: " + b1.isValidSolution());
    }
 
}
