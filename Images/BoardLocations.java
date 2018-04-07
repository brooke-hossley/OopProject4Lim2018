import java.awt.Point;
/**
 * Write a description of class BoardLocations here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BoardLocations
{
    /*points represent pixel location of top left corner for 
    each of the 25 board positions*/
    protected Point[][] locationPoints;

    int[] xPoints = {65, 161, 256, 352, 448};
    int[] yPoints = {69, 165, 260, 356, 452};

    /**
     * Constructor for objects of class BoardLocations
     */
    public BoardLocations()
    {
        // initialise instance variables
        locationPoints = new Point[5][5];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++){
                locationPoints[row][col] = 
                new Point(xPoints[row], yPoints[col]);
            }
        }
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public Point getDropPoint(int mouseX, int mouseY)
    {
        for (int row = 0; row < 5; row++) {
            //if its in this row (give or take 8 pixels)
            if (xPoints[row] - 8 <= mouseX && mouseX <= xPoints[row] + 88) {
                //loop through the 5 spots
                for (int col = 0; col < 5; col++){
                    //if its in this col (give or take 8 pixels)
                    if (yPoints[col] - 8 <= 
                    mouseY && mouseY <= yPoints[col] + 88) {
                        //return that location
                        return locationPoints[row][col];
                    }
                }
            }
        }

        return null;
    }
}
