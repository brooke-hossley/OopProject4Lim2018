import java.awt.Point;
/**
 * Class used to represent all 25 possible game piece locations 
 * in the board of a Laser Maze game 
 *
 * @author Alissa Ronca, Patrick Barber, Brooke Hossley,
 *         Chris Adams, Hieu Le
 * @version Spring 2018
 */
public class BoardLocations
{
    /* Points represent pixel location of top left corner for 
    each of the 25 board positions*/
    protected Point[][] locationPoints;

    // The x and y starting pixels
    private int[] xPoints = {65, 161, 256, 352, 448};
    private int[] yPoints = {69, 165, 260, 356, 452};

    /**
     * Constructor for objects of class BoardLocations
     */
    public BoardLocations()
    {
        //Initialise instance variables
        locationPoints = new Point[5][5];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++){
                locationPoints[row][col] = 
                new Point(xPoints[row], yPoints[col]);
            }
        }
    }

    /**
     * Finds closest corresponding board drop point given x and y 
     * of the mouse release location, or returns null if mouse is outside 
     * the bounds of the game board
     *
     * @param mouseX The x-coordinate of the mouse
     * @param mouseY The y-coordinate of the mouse
     * @return The corresponding board drop point
     */
    protected Point getDropPoint(int mouseX, int mouseY)
    {
        for (int row = 0; row < 5; row++) {
            //If the mouse is within 8 pixels of this row
            if (xPoints[row] - 8 <= mouseX && mouseX <= xPoints[row] + 88) {
                //Loop through the 5 columns of the row
                for (int col = 0; col < 5; col++){
                    //If the mouse is within 8 pixels of this column
                    if (yPoints[col] - 8 <= 
                    mouseY && mouseY <= yPoints[col] + 88) {
                        //Return that location
                        return locationPoints[row][col];
                    }
                }
            }
        }
        //Location null if the mouse is outside of the board
        return null;
    }
}
