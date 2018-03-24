 
/**
 * Abstract class RedPiece - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public class RedPiece extends Piece
{

    /**
     * Constructs a RedPiece with orientation passed in
     * 
     * @param orientation the initial orientation of the piece
     * @param rotatable whether the piece can be rotated. Should be false if piece doesn't start as a "?"
     */
    public RedPiece(Orientation orientation, boolean rotatable)
    {
        super(orientation, rotatable, false, true);
    }
    
    
}
