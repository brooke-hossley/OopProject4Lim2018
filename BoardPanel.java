import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
/**
 * Write a description of class BoardPanel here.
 *
 * @author Patrick Alissa Brooke Chris and Hieu
 * @version (a version number or a date)
 */
public class BoardPanel extends JFrame
{
    public BoardPanel(int card) //game 1
    {
        super("BoardPanel");
        JFrame frame = new JFrame("Laser Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = (JPanel)frame.getContentPane();

        ImageIcon pic1 = new ImageIcon("purpleMirror.PNG");// your image here
        ImageIcon pic2 = new ImageIcon("laser.PNG");// your image here

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon("Board.PNG"));// your image here
        panel.add(background);

        CircleButton fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        background.add(fireButton);

        frame.setSize(730,645);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        if(card==2)
        {

        }

    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new BoardPanel(1);
                }
            });
    }

}
