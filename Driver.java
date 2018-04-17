import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
/**
 * The class that runs the game with a level 
 * selector and a help button for the rules
 * 
 * @author Brooke Hossley
 * @version Spring 2018
 */
public class Driver extends JPanel implements ActionListener
{
    //Laser Maze logo
    private static Image logo, welcome;
    private static Image background;
    private Image catGif;
    //JButtons for level selection
    private JButton beginnerLevel;
    private JButton intermediateLevel;
    private JButton helpButton;
    //The frame to display the menu on
    protected static JFrame frame;
    /**
     * Constructor for the Driver class
     */
    public Driver()
    {
        //Images with paths
        String dir = "Images\\";
        String dir2 = "Rules\\";
        logo = new ImageIcon(dir + "Logo.jpg").getImage();
        background = new ImageIcon(dir + "background.jpg").getImage();
        welcome = new ImageIcon(dir + "Welcome.png").getImage();
        catGif = new ImageIcon(dir2 + "lasercat.gif").getImage();

        //Set window to correct size
        Dimension size = new Dimension(background.getWidth(null), 
                background.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        //Creating the JButtons with actionListeners
        beginnerLevel = new JButton("Easy");
        beginnerLevel.setFont(new Font("Arial", Font.BOLD, 40));
        beginnerLevel.setBackground(Color.GREEN);
        beginnerLevel.setBounds(300,300,200,100);
        add(beginnerLevel);
        beginnerLevel.addActionListener(this);
        beginnerLevel.setActionCommand("Easy");
        add(beginnerLevel);

        intermediateLevel = new JButton("Hard");
        intermediateLevel.setFont(new Font("Arial", Font.BOLD, 40));
        intermediateLevel.setBackground(Color.RED);
        intermediateLevel.setBounds(750,300,200,100);
        add(intermediateLevel);
        intermediateLevel.addActionListener(this);
        intermediateLevel.setActionCommand("Hard");
        add(intermediateLevel);

        helpButton = new JButton("Help");
        helpButton.setFont(new Font("Arial", Font.BOLD, 20));
        helpButton.setBackground(new Color(51,153,255));
        helpButton.setBounds(100,600,100,50);
        add(helpButton);
        helpButton.addActionListener(this);
        helpButton.setActionCommand("Help");
        add(helpButton);
    }

    /**
     * Panel's paint method to manage the graphics
     * 
     * @param g The Graphics reference
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        String text = "Welcome to Laser Maze!";
        String text2 = "Select the difficulty or Help for rules";
        g.drawImage(background, 0, 0, null);
        g.drawImage(logo, 10, 10, null);
        g.setColor(Color.GREEN);
        g.fillRect(700, 50, 550, 100);
        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 25));
        FontMetrics metrics = g.getFontMetrics(getFont());
        g.drawString(text, 820, 95); 
        g.drawString(text2, 765, 130);
        g.drawImage(catGif, 900, 350, this);
    }

    /**
     * Called when actionListeners are triggered
     * 
     * @param e The action calling the event
     */
    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();
        if(action.equals("Help")) 
        {
            Help.createAndShowGUI();
        }
        else if(action.equals("Easy"))
        {
            BeginnerBoardPanel.createAndShowGUI();
            frame.dispose();
        }
        else
        {
            IntermediateBoardPanel.createAndShowGUI();
            frame.dispose();
        }
    }

    /**
     * Creates the JFrame for the menu
     */
    protected static void createAndShowGUI() 
    {
        //Create and set up the window.
        frame = new JFrame("Level Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Driver panel = new Driver();
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Main method to run the Menu
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
