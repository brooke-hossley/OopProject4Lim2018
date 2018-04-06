 


/**
 * Abstract class Pieces - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Piece
{
    protected Orientation orient;
    protected boolean isRotatable;
    protected boolean isMovable;
    protected boolean isHit;
    public Piece(Orientation o, boolean rotatable, boolean movable, boolean hit)
    {
        orient = o;
        isRotatable = rotatable;
        isHit = hit;
        isMovable = movable;
    }
    
    /**
     * Changes orientation to make the piece be rotated one clockwise. If orient is unknown it will 
     */
    public boolean rotate(){
        if (!this.isRotatable) {
            return false;
        }
        
        if (orient == Orientation.UP) {
            orient = Orientation.RIGHT;
        }
        else if (orient == Orientation.RIGHT) {
            orient = Orientation.DOWN;
        }
        else if (orient == Orientation.DOWN) {
            orient = Orientation.LEFT;
        }
        else if (orient == Orientation.UNKNOWN || orient == Orientation.LEFT) {
            orient = Orientation.UP;
        }
        return true;
    }
    
    public void hitPiece() {
        isHit = true;
    }
    
}
