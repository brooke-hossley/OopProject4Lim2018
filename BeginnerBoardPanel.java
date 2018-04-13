import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.Line2D;
import javax.sound.sampled.*;
/**
 * Class to create Laser Maze board game based on the beginner card
 *
 * @author Alissa Ronca, Patrick Barber, Brooke Hossley,
 *         Chris Adams, Hieu Le
 * @version Spring 2018
 */
public class BeginnerBoardPanel extends JPanel implements 
MouseListener, MouseMotionListener, ActionListener
{
    //Used to drag mirrors to correct spot
    private BoardLocations locations;
    private Point mirrorPoints[], sidePanel;

    //Position of the movable purple mirror
    private int x, y;

    //Dragging the movable mirror? 
    private boolean dragging;

    //Remember mouses "grab point" for the movable mirror 
    private int xOffset, yOffset;

    //Icon size 
    public static final int MIRROR_SIZE = 80;

    //All images used
    private static Image board;
    private Image[] redLasers,  purpleMirrors;
    private Image redQuestionMark, purpleMirrorTarget, purpleQuestionMark;

    //Current image being used
    private Image ourLaser, ourMovablePurple;

    //Array index where ourLaser and ourMovablePurple currently are
    private int redIndex, purpleIndex;

    //Used to fire laser using button if solution is correct
    private CircleButton fireButton;
    private boolean correctSolution, fire;

    //Piece placement sound 
    File soundFilePlacement;

    //The JFrame to display the game
    private static JFrame frame;

    /**
     * Constructor for the Beginner Board
     */
    public BeginnerBoardPanel() 
    {
        locations = new BoardLocations();
        sidePanel = new Point(602, 75);
        mirrorPoints = new Point[4];
        mirrorPoints[0] = locations.locationPoints[4][0];
        mirrorPoints[1] = locations.locationPoints[0][3];
        mirrorPoints[2] = locations.locationPoints[0][4];
        mirrorPoints[3] = sidePanel;

        //Starting images 
        String dir = "Images\\";
        board = new ImageIcon(dir + "Board.PNG").getImage();
        redQuestionMark = new ImageIcon(dir + 
            "RedLaserQuestion.JPG").getImage();
        purpleMirrorTarget = new ImageIcon(dir + 
            "PurpleMirrorWithTargetDown.JPG").getImage();
        purpleQuestionMark = new ImageIcon(dir + 
            "PurpleQuestionMark.JPG").getImage();
        ourLaser = redQuestionMark;
        ourMovablePurple = purpleQuestionMark;

        //Initial position of movable mirror
        x = 602;
        y = 75;

        //Load in purple and red piece images for rotation later
        purpleMirrors = new Image[4];
        purpleMirrors[0] = new ImageIcon(dir + 
            "PurpleMirrorUp.JPG").getImage();
        purpleMirrors[1] = new ImageIcon(dir + 
            "PurpleMirrorRight.JPG").getImage();
        purpleMirrors[2] = new ImageIcon(dir + 
            "PurpleMirrorDown.JPG").getImage();
        purpleMirrors[3] = new ImageIcon(dir + 
            "PurpleMirrorLeft.JPG").getImage();

        redLasers = new Image[4];
        redLasers[0] = new ImageIcon(dir + 
            "LaserUp.JPG").getImage();
        redLasers[1] = new ImageIcon(dir + 
            "LaserRight.JPG").getImage();
        redLasers[2] = new ImageIcon(dir + 
            "LaserDown.JPG").getImage();
        redLasers[3] = new ImageIcon(dir + 
            "LaserLeft.JPG").getImage();

        //Set window to correct size
        Dimension size = new Dimension(board.getWidth(null), 
                board.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);

        //Add needed listener objects
        addMouseListener(this);
        addMouseMotionListener(this);

        //Set up fire button
        fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        add(fireButton);
        fireButton.addActionListener(this);

        try
        {
            soundFilePlacement = new File("ButtonClick.wav");
        }
        catch(Exception e) {}
    }

    /**
     * Panel's paint method to manage the graphics
     * 
     * @param g The Graphics reference
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        //Static images 
        g.drawImage(board, 0, 0, null);
        g.drawImage(purpleMirrorTarget, 448, 69, null);
        g.drawImage(purpleMirrors[3], 65, 356, null);

        //Rotatable and draggable images
        g.drawImage(ourLaser, 65, 452, null);
        g.drawImage(ourMovablePurple, x, y, null);

        if (fire) 
        { 
            if (correctSolution) 
            { 
                /*Draw the laser and "You Win" message
                and the option to return to the menu*/

                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(10));
                g.drawLine(105,452,105,400);
                g.drawLine(105,400,480,400);
                g.drawLine(486,400,486,138);

                g.setColor(Color.GREEN);
                g.fillRect(190, 275, 205, 50);
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
                g.drawString("YOU WIN!", 200, 315);
                g.drawString("Back to Menu?", 160, 365);
                JButton y = new JButton("YES");
                y.setFont(new Font("Arial", Font.BOLD, 20));
                y.setBackground(Color.GREEN);
                y.setBounds(160,380,100,50);
                add(y);
                y.addActionListener(this);
                y.setActionCommand("YES");
                add(y);
                JButton n = new JButton("NO");
                n.setFont(new Font("Arial", Font.BOLD, 20));
                n.setBackground(Color.RED);
                n.setBounds(325,380,100,50);
                add(n);
                n.addActionListener(this);
                n.setActionCommand("NO");
                add(n);
            }

            else 
            {
                //Draw "Try Again" message
                g.setColor(Color.RED);
                g.fillRect(175, 275, 235, 50);
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
                g.drawString("TRY AGAIN!", 180, 315);
            }
        }
    }

    /**
     * Called when actionListeners are triggered
     * 
     * @param e The action calling the event
     * @see java.awt.event
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        fire = true;
        correctSolution = (mirrorPoints[3] == locations.locationPoints[4][3] 
            && ourLaser == redLasers[0] && ourMovablePurple == 
            purpleMirrors[1]);
        repaint();
        String action = e.getActionCommand();
        if(action.equals("YES")){
            Driver.createAndShowGUI();
        }
        else if(action.equals("NO")){
            frame.dispose();
        }
    }

    /**
     * Determines what each mouse click does
     * 
     * @param e The action calling the event
     */
    public void mouseClicked(MouseEvent e)
    {
        //If clicked on laser, rotate it
        if (e.getX() >= 65 && e.getX() <= 65 + MIRROR_SIZE &&
        e.getY() >= 452 && e.getY() <= 452 + MIRROR_SIZE) 
        {
            if (ourLaser == redQuestionMark || ourLaser == redLasers[3]) 
            {
                ourLaser = redLasers[0];
                redIndex = 0;
                repaint();
            }
            else 
            {
                redIndex++; 
                ourLaser = redLasers[redIndex];
                repaint();
            }
        }
        //If clicked on movable mirror, rotate it
        else if (e.getX() >= x && e.getX() <= x + MIRROR_SIZE &&
        e.getY() >= y && e.getY() <= y + MIRROR_SIZE) 
        {
            if (ourMovablePurple == purpleQuestionMark || 
            ourMovablePurple == purpleMirrors[3]) 
            {
                ourMovablePurple = purpleMirrors[0];
                purpleIndex = 0;
                repaint();
            }
            else 
            {
                purpleIndex++; 
                ourMovablePurple = purpleMirrors[purpleIndex];
                repaint();
            }
        }
        else return;

        try
        {
            mirrorSound();
        }
        catch(Exception ex){}
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    /**
     * Determines what each mouse release does
     * 
     * @param e The action calling the event
     */
    public void mouseReleased(MouseEvent e) 
    {
        if (dragging) 
        {
            //Drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());
            //If not within board, return the mirror to the side panel
            if (drop == null) 
            {
                x = 602;
                y = 75;
                mirrorPoints[3] = sidePanel;
            }

            //If within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2]) 
            {
                x = drop.x;
                y = drop.y;
                mirrorPoints[3] = drop;
            }

            //If over another piece, leave it where it came from
            else 
            {
                x = mirrorPoints[3].x;
                y = mirrorPoints[3].y;
            }

            try
            {
                mirrorSound();
            }
            catch(Exception ex){}
            repaint();
        }
    }

    /** 
     * Determines if the mouse was pressed in the bounds of
     * the movable image.
     * 
     * @param e The action causing the event
     */
    public void mousePressed(MouseEvent e) 
    {
        fire = correctSolution = false;
        dragging = (e.getX() >= x && e.getX() <= x + MIRROR_SIZE &&
            e.getY() >= y && e.getY() <= y + MIRROR_SIZE);
        xOffset = e.getX() - x;
        yOffset = e.getY() - y;
    }

    /**
     * Determins if the mouse is dragging
     * 
     * @param e The action causing the event
     */
    public void mouseDragged(MouseEvent e) 
    {
        if (dragging) 
        {
            x = e.getX() - xOffset;
            y = e.getY() - yOffset;
            repaint();
        }
    }

    /**
     * Method for the placing and rotating mirrors/laser sound 
     * 
     * @throws Exception The possiblility of the file not being found
     */
    private void mirrorSound() throws Exception
    {
        //Allocate a AudioInputStream piped from a file
        AudioInputStream audioIn = 
            AudioSystem.getAudioInputStream(soundFilePlacement);
        //Allocate a sound Clip resource
        Clip clip = AudioSystem.getClip();
        //Open the clip to load sound samples from the audio input stream
        clip.open(audioIn);
        //Play once
        clip.start();
        if (clip.isRunning()) clip.stop();//Stop playing 
    }

    /**
     * Creates the JFrame for the game
     */
    protected static void createAndShowGUI() 
    {
        //Create and set up the window.
        frame = new JFrame("BeginnerBoardPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BeginnerBoardPanel panel = new BeginnerBoardPanel();
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
