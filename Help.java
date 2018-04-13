import java.awt.image.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
/**
 * Class for contructing the rules JFrame that is 
 * triggered by the menu screen
 * 
 * @author Brooke Hossley
 * @version Spring 2018
 */
public class Help extends JPanel
{
    //Images used 
    private Image frontPage, gMirror, laser, pMirror, objective, 
    rules1, rules2, gif, background;
    /**
     * Constructor for the Help class
     */
    public Help()
    {
        //Images with paths
        String dir = "Rules\\";
        frontPage = new ImageIcon(dir + "front page.PNG").getImage();
        gMirror = new ImageIcon(dir + "Green mirror.PNG").getImage();
        laser = new ImageIcon(dir + "Laser.PNG").getImage();
        pMirror = new ImageIcon(dir + "Purple mirror.PNG").getImage();
        objective = new ImageIcon(dir + "Objective.PNG").getImage();
        rules1 = new ImageIcon(dir + "Steps to play.PNG").getImage();
        rules2 = new ImageIcon(dir + "Steps to play 2.PNG").getImage();
        gif = new ImageIcon(dir + "gif.GIF").getImage();
        gif = gif.getScaledInstance(200, 150, Image.SCALE_DEFAULT);
        background = new ImageIcon(dir + "galaxy.JPG").getImage();
        background = 
        background.getScaledInstance(800, 900, Image.SCALE_DEFAULT);
        //Setting up the starting window
        Dimension size = new Dimension(800, 900);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    /**
     * Panel's paint method to manage the graphics
     * 
     * @param g The Graphics reference
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(frontPage, 0, 0, null);
        g.drawImage(objective, 400, 0, null);
        g.drawImage(rules1, 400, 230, null);
        g.drawImage(rules2, 400, 450, null);
        g.drawImage(laser, 0, 570, null);
        g.drawImage(pMirror, 0, 660, null);
        g.drawImage(gMirror, 0, 770, null);
        g.drawImage(gif, 480, 730, this);
    }

    /**
     * Creates the JFrame for the Help window
     */
    protected static void createAndShowGUI() 
    {
        //Create and set up the window.
        JFrame frame = new JFrame("Instructions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Help panel = new Help();
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
