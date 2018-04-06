 


/**
 * Write a description of class PurplePiece here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PurplePiece extends Piece
{
    protected boolean isTarget;
    /**
     * Constructs a PurplePiece as it would be in the unused panel:
     * with orientation unknown (Question mark), able to be rotated, movable, not hit yet, and not an end target
     */
    public PurplePiece()
    {
        super(Orientation.UNKNOWN, true, true, false);
        isTarget=false;
    }
    
    /**
     * Constructs a PurplePiece with orientation passed in, Hasn't been hit yet.
     * 
     * @param orientation the initial orientation of the piece
     * @param rotatable whether the piece can be rotated. Should be false if piece doesn't start as a "?"
     */
    public PurplePiece(Orientation orientation, boolean rotatable, boolean movable, boolean target)
    {
        //makes Piece with orientation and rotatable given, that is not hit
        super(orientation, rotatable, movable, false);
        //set isTarget
        isTarget = target;
    }
}
