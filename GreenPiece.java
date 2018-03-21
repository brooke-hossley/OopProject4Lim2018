package OopProject4Lim2018;


/**
 * Class to represent a green two way mirror piece, aka a beam splitter. 
 * 
 * For Orientation, UP and DOWN should be the same and will look like |/|
 *                  LEFT and RIGHT should be the same and will look like |\|
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GreenPiece extends Piece
{
    
    /**
     * Constructs a GreenPiece with orientation unknown (Question mark), able to be rotated and moved, and not hit yet
     */
    public GreenPiece()
    {
        super(Orientation.UNKNOWN, true, true, false);
    }
    
    
}
