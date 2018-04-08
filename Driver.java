import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
/**
 * Write a description of class Driver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Driver extends JPanel implements MouseListener
{
    //Laser Maze logo
    private static Image logo;
    private static Image background;

    public Driver()
    {
        //Starting Image
        String dir = "Images\\";
        logo = new ImageIcon(dir + "Logo.JPG").getImage();
        background = new ImageIcon(dir + "background.PNG").getImage();

        //Set window to correct size
        Dimension size = new Dimension(logo.getWidth(null), 
                logo.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    /**
     * Creates the JFrame for the game
     */
    private static void createAndShowGUI() 
    {
        //Create and set up the window.
        JFrame frame = new JFrame("Level Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Driver panel = new Driver();
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Main method to run the Beginner Board
     * 
     * @param args Unused
     */
    public static void main(String[] args) 
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
            {
                public void run() 
                {
                    createAndShowGUI();
                }
            });
    }
}
