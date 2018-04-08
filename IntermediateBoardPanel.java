import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.Line2D;
import javax.sound.sampled.*;
/**
 * Creates the board with draggable images and rotable mirrors.
 *
 * @author Alissa Ronca, Patrick Barber, Brooke Hossley, 
 *         Chris Adams, Hieu Le
 * @version Spring 2018
 */
public class IntermediateBoardPanel extends JPanel implements 
MouseListener, MouseMotionListener, ActionListener
{
    //To drag mirrors to correct spot
    private BoardLocations locations;
    private Point[] mirrorPoints;
    private Point[] sidePanel = {new Point(602, 75), 
            new Point(602, 170), new Point(602, 265)};

    //Position of the movable mirrors, in the order they start on side panel
    private int x1, y1, x2, y2, x3, y3;

    //Dragging a movable mirror? 
    private boolean dragging1, dragging2, dragging3;

    //Remember mouse "grab point" for the movable mirrors 
    private int xOffset, yOffset;

    //Icon size 
    public static final int MIRROR_SIZE = 80;

    //Starting images
    protected static Image board;
    private Image[] redLasers, purpleMirrors; 
    private Image[] purpleMirrorsWithTarget, greenMirrors;
    private Image redQuestionMark, purpleMirrorTarget; 
    private Image purpleQuestionMark, purpleQuestionMark2; 
    private Image greenQuestionMark, purpleWithRedQuestionMark;

    //Current images being used by the game for laser and moveable mirrors
    private Image ourLaser, ourMoveablePurple1, ourMoveablePurple2, 
    ourPurpleWithTarget, ourMoveableGreen;

    //Array index where ourLaser and ourMovablePurple currently are
    private int redIndex, purpleIndex, purpleIndex2, 
    greenIndex, purpleWithTargetIndex;

    //Used to fire laser if the solution is correct
    private CircleButton fireButton;
    private boolean correctSolution, fire;

    //Piece placement sound
    File soundFile;

    /**
     * Constructor for the Intermediate Board
     */
    public IntermediateBoardPanel() 
    {
        locations = new BoardLocations();
        mirrorPoints = new Point[6];
        mirrorPoints[0] = locations.locationPoints[3][0];
        mirrorPoints[1] = locations.locationPoints[1][1];
        mirrorPoints[2] = locations.locationPoints[3][3];
        mirrorPoints[3] = sidePanel[0];
        mirrorPoints[4] = sidePanel[1];
        mirrorPoints[5] = sidePanel[2];

        //Set starting images
        String dir = "Images\\";
        board = new ImageIcon(dir + "Board.PNG").getImage();
        redQuestionMark = new ImageIcon(dir + 
            "RedLaserQuestion.JPG").getImage();
        purpleMirrorTarget = new ImageIcon(dir + 
            "PurpleMirrorWithTargetRight.JPG").getImage();
        purpleQuestionMark = new ImageIcon(dir + 
            "PurpleQuestionMark.JPG").getImage();
        greenQuestionMark = new ImageIcon(dir + 
            "greenQuestionMark.JPG").getImage();
        purpleWithRedQuestionMark = new ImageIcon(dir + 
            "purpleWithRedQuestion.JPG").getImage();

        ourLaser = redQuestionMark;
        ourMoveablePurple1 = purpleQuestionMark;
        ourMoveablePurple2 = purpleQuestionMark;
        ourPurpleWithTarget = purpleWithRedQuestionMark;
        ourMoveableGreen = greenQuestionMark;

        //Initial position of the movable mirrors 
        //First purple mirror
        x1 = sidePanel[0].x;
        y1 = sidePanel[0].y;
        //Second purple mirror
        x2 = sidePanel[1].x;
        y2 = sidePanel[1].y;
        //Green mirror
        x3 = sidePanel[2].x;
        y3 = sidePanel[2].y;

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

        purpleMirrorsWithTarget = new Image[4];
        purpleMirrorsWithTarget[0] = new ImageIcon(dir + 
            "PurpleMirrorWithTargetUp.JPG").getImage();
        purpleMirrorsWithTarget[1] = new ImageIcon(dir + 
            "PurpleMirrorWithTargetRight.JPG").getImage();
        purpleMirrorsWithTarget[2] = new ImageIcon(dir + 
            "PurpleMirrorWithTargetDown.JPG").getImage();
        purpleMirrorsWithTarget[3] = new ImageIcon(dir + 
            "PurpleMirrorWithTargetLeft.JPG").getImage();

        redLasers = new Image[4];
        redLasers[0] = new ImageIcon(dir + 
            "LaserUp.JPG").getImage();
        redLasers[1] = new ImageIcon(dir + 
            "LaserRight.JPG").getImage();
        redLasers[2] = new ImageIcon(dir + 
            "LaserDown.JPG").getImage();
        redLasers[3] = new ImageIcon(dir + 
            "LaserLeft.JPG").getImage();

        greenMirrors = new Image[2];
        greenMirrors[0] = new ImageIcon(dir + 
            "GreenMirrorA.JPG").getImage();
        greenMirrors[1] = new ImageIcon(dir + 
            "GreenMirrorB.JPG").getImage();

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
            soundFile = new File("ButtonClick.wav");
        }
        catch(Exception e) {}

    }

    /**
     * Panel's paint method to manage the graphics
     * 
     * @param g the Graphics reference
     */
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        //Static images 
        g.drawImage(board, 0, 0, null);
        g.drawImage(purpleMirrorsWithTarget[1], 352, 356, null);

        //Rotatable and draggable images
        g.drawImage(ourLaser, 161, 165, null);
        g.drawImage(ourMoveablePurple1, x1, y1, null);
        g.drawImage(ourMoveablePurple2, x2, y2, null);
        g.drawImage(ourMoveableGreen, x3, y3, null);
        g.drawImage(ourPurpleWithTarget, 352, 69, null);
        if (fire) 
        {
            if (correctSolution) 
            {
                //Draw the laser and "You Win" message
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(10));
                g.drawLine(245,205,490,205);
                g.drawLine(390,205,390,141);
                g.drawLine(490,205,490,397);
                g.drawLine(490,397,420,397);

                g.setColor(Color.GREEN);
                g.fillRect(190, 275, 205, 50);
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.DIALOG, 0, 40));
                g.drawString("YOU WIN!", 200, 315);
            }

            else 
            {
                //Draw "Try Again" message
                g.setColor(Color.RED);
                g.fillRect(175, 275, 235, 50);
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.DIALOG, 0, 40));
                g.drawString("TRY AGAIN!", 180, 315);
            }
        }
    }

    /**
     * Called when the fire button is pressed
     * 
     * @param e The action calling the event
     * @see java.awt.event
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        fire = true;
        //Determine if board pieces are in the correct positions
        correctSolution = (ourPurpleWithTarget == 
            purpleMirrorsWithTarget[2] 
            && ourLaser == redLasers[1] && ourMoveableGreen == 
            greenMirrors[0] &&
            mirrorPoints[5] == locations.locationPoints[3][1] 
            && ((ourMoveablePurple1 == purpleMirrors[0] && mirrorPoints[3] == 
                    locations.locationPoints[4][1] && ourMoveablePurple2 == 
                    purpleMirrors[1] && mirrorPoints[4] == 
                    locations.locationPoints[4][3])
                || (ourMoveablePurple2 == purpleMirrors[0] && 
                    mirrorPoints[4] == 
                    locations.locationPoints[4][1] && ourMoveablePurple1 == 
                    purpleMirrors[1] && mirrorPoints[3] == 
                    locations.locationPoints[4][3])));
        repaint();
    }

    /**
     * Determins what each mouse click does
     * 
     * @param e The action calling the event
     */
    public void mouseClicked(MouseEvent e) 
    {
        //If clicked on laser, rotate it
        if (e.getX() >= 160 && e.getX() <= 160 + MIRROR_SIZE &&
        e.getY() >= 165 && e.getY() <= 165 + MIRROR_SIZE) 
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
        //If clicked on movable mirror1, rotate it
        else if (e.getX() >= x1 && e.getX() <= x1 + MIRROR_SIZE &&
        e.getY() >= y1 && e.getY() <= y1 + MIRROR_SIZE) 
        {
            if (ourMoveablePurple1 == purpleQuestionMark || 
            ourMoveablePurple1 == purpleMirrors[3]) 
            {
                ourMoveablePurple1 = purpleMirrors[0];
                purpleIndex = 0;
                repaint();
            }
            else 
            {
                purpleIndex++; 
                ourMoveablePurple1 = purpleMirrors[purpleIndex];
                repaint();
            }
        }
        //If clicked on movable mirror2, rotate it
        else if (e.getX() >= x2 && e.getX() <= x2 + MIRROR_SIZE &&
        e.getY() >= y2 && e.getY() <= y2 + MIRROR_SIZE) 
        {
            if (ourMoveablePurple2 == purpleQuestionMark || 
            purpleIndex2 == 3) 
            {
                ourMoveablePurple2 = purpleMirrors[0];
                purpleIndex2 = 0;
                repaint();
            }
            else 
            {
                purpleIndex2++; 
                ourMoveablePurple2 = purpleMirrors[purpleIndex2];
                repaint();
            }
        }
        //If clicked on movable green mirror, rotate it
        else if (e.getX() >= x3 && e.getX() <= x3 + MIRROR_SIZE &&
        e.getY() >= y3 && e.getY() <= y3 + MIRROR_SIZE) 
        {
            if (ourMoveableGreen == greenQuestionMark || 
            ourMoveableGreen == greenMirrors[0]) 
            {
                ourMoveableGreen = greenMirrors[1];
                repaint();
            }
            else 
            {
                ourMoveableGreen = greenMirrors[0];
                repaint();
            }
        }
        //If clicked on rotatable target mirror, rotate it
        else if (e.getX() >= 352 && e.getX() <= 352 + MIRROR_SIZE &&
        e.getY() >= 69 && e.getY() <= 69 + MIRROR_SIZE) 
        {
            if (ourPurpleWithTarget == purpleWithRedQuestionMark || 
            ourPurpleWithTarget == purpleMirrorsWithTarget[3]) 
            {
                ourPurpleWithTarget = purpleMirrorsWithTarget[0];
                purpleWithTargetIndex = 0;
                repaint();
            }
            else 
            {
                purpleWithTargetIndex++; 
                ourPurpleWithTarget = 
                purpleMirrorsWithTarget[purpleWithTargetIndex];
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
     * Determins what each mouse release does
     * 
     * @param e Th action calling the event
     */
    public void mouseReleased(MouseEvent e) 
    {
        if (dragging1) 
        {
            //Drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());
            //If not within board, return the mirror to the side panel
            if (drop == null) 
            {
                x1 = 602;
                y1 = 75;
                mirrorPoints[3] = sidePanel[0];
            }
            //If within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2] && drop != mirrorPoints[4] && 
            drop != mirrorPoints[5]) 
            {
                x1 = drop.x;
                y1 = drop.y;
                mirrorPoints[3] = drop;
            }
            //If over another piece leave, it where it came from
            else 
            {
                x1 = mirrorPoints[3].x;
                y1 = mirrorPoints[3].y;
            }

            try
            {
                mirrorSound();
            }
            catch(Exception ex){}
            repaint();
        }
        else if (dragging2) 
        {
            //Drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());
            //If not within board, return the mirror to the side panel
            if (drop == null) 
            {
                x2 =602;
                y2 = 170;
                mirrorPoints[4] = sidePanel[1];
            }
            //If within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2] && drop != mirrorPoints[3] && 
            drop != mirrorPoints[5]) 
            {
                x2 = drop.x;
                y2 = drop.y;
                mirrorPoints[4] = drop;
            }
            //If over another piece, leave it where it came from
            else 
            {
                x2 = mirrorPoints[4].x;
                y2 = mirrorPoints[4].y;
            }

            try
            {
                mirrorSound();
            }
            catch(Exception ex){}
            repaint();
        }
        else if (dragging3) 
        {
            //Drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());
            //If not within board, return the mirror to the side panel
            if (drop == null) 
            {
                x3 =602;
                y3 = 265;
                mirrorPoints[5] = sidePanel[2];
            }
            //If within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2] && drop != mirrorPoints[3] && 
            drop != mirrorPoints[4]) 
            {
                x3 = drop.x;
                y3 = drop.y;
                mirrorPoints[5] = drop;
            }
            //If over another piece, leave it where it came from
            else 
            {
                x3 = mirrorPoints[5].x;
                y3 = mirrorPoints[5].y;
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
     * a movable image.
     * 
     * @param e The action leading to the event
     */
    public void mousePressed(MouseEvent e) 
    {
        correctSolution = fire = false;
        //Determine if mouse click is on first movable purple mirror
        dragging1 = (e.getX() >= x1 && e.getX() <= x1 + MIRROR_SIZE &&
            e.getY() >= y1 && e.getY() <= y1 + MIRROR_SIZE);
        if (dragging1) 
        {
            xOffset = e.getX() - x1;
            yOffset = e.getY() - y1;
        }
        //Determine if mouse click is on second movable purple mirror
        dragging2 = (e.getX() >= x2 && e.getX() <= x2 + MIRROR_SIZE &&
            e.getY() >= y2 && e.getY() <= y2 + MIRROR_SIZE);
        if (dragging2) 
        {
            xOffset = e.getX() - x2;
            yOffset = e.getY() - y2;
        }
        //Determine if mouse click is on movable green mirror
        dragging3 = (e.getX() >= x3 && e.getX() <= x3 + MIRROR_SIZE &&
            e.getY() >= y3 && e.getY() <= y3 + MIRROR_SIZE);
        if (dragging3) 
        {
            xOffset = e.getX() - x3;
            yOffset = e.getY() - y3;
        }
    }

    /**
     * Determins if the mouse is dragging
     * 
     * @param e The action causing the event
     */
    public void mouseDragged(MouseEvent e) 
    {
        if (dragging1) 
        {
            x1 = e.getX() - xOffset;
            y1 = e.getY() - yOffset;
            repaint();
        }
        if (dragging2) 
        {
            x2 = e.getX() - xOffset;
            y2 = e.getY() - yOffset;
            repaint();
        }
        if (dragging3) 
        {
            x3 = e.getX() - xOffset;
            y3 = e.getY() - yOffset;
            repaint();
        }
    }

    /**
     * Method for the placing and rotating mirrors/laser sound 
     * 
     * @throws The possiblility of the file not being found
     */
    private void mirrorSound() throws Exception
    {
        //Allocate a AudioInputStream piped from a file
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
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
    private static void createAndShowGUI() 
    {
        //Create and set up the window.
        JFrame frame = new JFrame("Intermediate Board Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IntermediateBoardPanel panel = new IntermediateBoardPanel();
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
                public void run() {
                    createAndShowGUI();
                }
            });
    }
}