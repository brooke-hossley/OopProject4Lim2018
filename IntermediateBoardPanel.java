import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.geom.Line2D;
////////////////////////////////////////////////////////////////////////////////
/**
 * Creates the board with draggable images and rotable mirrors.
 *
 * @author (Alissa, Patrick, Chris, Hieu, Chris)
 * @version (3/26/18)
 */
public class IntermediateBoardPanel extends JPanel implements MouseListener, MouseMotionListener , ActionListener
{
    //used to drag to correct spot
    private BoardLocations locations;
    private Point[] mirrorPoints;
    private Point[] sidePanel = {new Point(602, 75), new Point(602, 170), new Point(602, 265)};

    // position of the movable mirrors
    private int x1, y1, x2, y2, x3, y3;

    // am I currently dragging a movable mirror? 
    private boolean dragging1, dragging2, dragging3;

    // remember mouse "grab point" in the movable mirror 
    private int xOffset, yOffset;

    // icon size 
    public static final int MIRROR_SIZE = 80;

    //all the images we're going to use
    private static Image board;
    private Image[] redLasers,  purpleMirrors, purpleMirrorsWithTarget, greenMirrors;
    private Image redQuestionMark, purpleMirrorTarget; 
    private Image purpleQuestionMark, purpleQuestionMark2, greenQuestionMark, purpleWithRedQuestionMark;

    //what the current image being used by game is for laser and moveable mirror
    private Image ourLaser, ourMoveablePurple1, ourMoveablePurple2, ourPurpleWithTarget, ourMoveableGreen;

    //array index ourLaser and ourMovablePurple currently are
    private int redIndex, purpleIndex, purpleIndex2 ,greenIndex, purpleWithTargetIndex;

    private CircleButton fireButton;
    private boolean correctSolution, fire;

    public IntermediateBoardPanel() {
        locations = new BoardLocations();
        mirrorPoints = new Point[6];
        mirrorPoints[0] = locations.locationPoints[3][0];
        mirrorPoints[1] = locations.locationPoints[1][1];
        mirrorPoints[2] = locations.locationPoints[3][3];
        mirrorPoints[3] = sidePanel[0];
        mirrorPoints[4] = sidePanel[1];
        mirrorPoints[5] = sidePanel[2];
        
        //set images we start with on board
        String dir = "Images\\";
        board = new ImageIcon(dir + "Board.PNG").getImage();
        redQuestionMark = new ImageIcon(dir + "RedLaserQuestion.JPG").getImage();
        purpleMirrorTarget = new ImageIcon(dir + "PurpleMirrorWithTargetRight.JPG").getImage();
        purpleQuestionMark = new ImageIcon(dir + "PurpleQuestionMark.JPG").getImage();
        greenQuestionMark = new ImageIcon(dir + "greenQuestionMark.JPG").getImage();
        purpleWithRedQuestionMark = new ImageIcon(dir + "purpleWithRedQuestion.JPG").getImage();

        ourLaser = redQuestionMark;
        ourMoveablePurple1 = purpleQuestionMark;
        ourMoveablePurple2 = purpleQuestionMark;
        ourPurpleWithTarget = purpleWithRedQuestionMark;
        ourMoveableGreen = greenQuestionMark;
        
        // initial position of our movable mirrors 
        //first purple mirror
        x1 = sidePanel[0].x;
        y1 = sidePanel[0].y;

        //second purple mirror
        x2 = sidePanel[1].x;
        y2 = sidePanel[1].y;

        //green mirror
        x3 = sidePanel[2].x;
        y3 = sidePanel[2].y;
        
        //load in purple and red piece images for rotation later
        purpleMirrors = new Image[4];
        purpleMirrors[0] = new ImageIcon(dir + "PurpleMirrorUp.JPG").getImage();
        purpleMirrors[1] = new ImageIcon(dir + "PurpleMirrorRight.JPG").getImage();
        purpleMirrors[2] = new ImageIcon(dir + "PurpleMirrorDown.JPG").getImage();
        purpleMirrors[3] = new ImageIcon(dir + "PurpleMirrorLeft.JPG").getImage();

        purpleMirrorsWithTarget = new Image[4];
        purpleMirrorsWithTarget[0] = new ImageIcon(dir + "PurpleMirrorWithTargetUp.JPG").getImage();
        purpleMirrorsWithTarget[1] = new ImageIcon(dir + "PurpleMirrorWithTargetRight.JPG").getImage();
        purpleMirrorsWithTarget[2] = new ImageIcon(dir + "PurpleMirrorWithTargetDown.JPG").getImage();
        purpleMirrorsWithTarget[3] = new ImageIcon(dir + "PurpleMirrorWithTargetLeft.JPG").getImage();

        redLasers = new Image[4];
        redLasers[0] = new ImageIcon(dir + "LaserUp.JPG").getImage();
        redLasers[1] = new ImageIcon(dir + "LaserRight.JPG").getImage();
        redLasers[2] = new ImageIcon(dir + "LaserDown.JPG").getImage();
        redLasers[3] = new ImageIcon(dir + "LaserLeft.JPG").getImage();

        greenMirrors = new Image[2];
        greenMirrors[0] = new ImageIcon(dir + "GreenMirrorA.JPG").getImage();
        greenMirrors[1] = new ImageIcon(dir + "GreenMirrorB.JPG").getImage();
        

        //set window to correct size
        Dimension size = new Dimension(board.getWidth(null), board.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);

        addMouseListener(this);
        addMouseMotionListener(this);

        fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        add(fireButton);
        fireButton.addActionListener(this);
    }

    /**
     * THE REALLY IMPORTANT METHOD which decides where to correctSolution stuff
     * Applet's paint method to manage our graphics
     * 
     * @param g the Graphics reference
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //these will stay same because hardcoded
        g.drawImage(board, 0, 0, null);
        g.drawImage(purpleMirrorsWithTarget[1], 352, 356, null);

        //these can  rotate and moveables can be dragged
        g.drawImage(ourLaser, 161, 165, null);
        g.drawImage(ourMoveablePurple1, x1, y1, null);
        g.drawImage(ourMoveablePurple2, x2, y2, null);
        g.drawImage(ourMoveableGreen, x3, y3, null);
        g.drawImage(ourPurpleWithTarget, 352, 69, null);

        if (fire) {
            if (correctSolution) {
                g.setColor(Color.RED);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(10));
                g.drawLine(245,205,490,205);
                g.drawLine(390,205,390,141);
                g.drawLine(490,205,490,396);
                g.drawLine(490,396,420,396);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //this is what gets called when the fire button is pressed
        fire = true;
        
        //determine if board is correct positioning
        correctSolution = (ourPurpleWithTarget == purpleMirrorsWithTarget[2] 
        && ourLaser == redLasers[1] && ourMoveableGreen == greenMirrors[0] &&
        mirrorPoints[5] == locations.locationPoints[3][1] 
        && ((ourMoveablePurple1 == purpleMirrors[0] && mirrorPoints[3] == 
        locations.locationPoints[4][1] && ourMoveablePurple2 == 
        purpleMirrors[1] && mirrorPoints[4] == locations.locationPoints[4][3])
        || (ourMoveablePurple2 == purpleMirrors[0] && mirrorPoints[4] == 
        locations.locationPoints[4][1] && ourMoveablePurple1 == 
        purpleMirrors[1] && mirrorPoints[3] == 
        locations.locationPoints[4][3])));
        
        repaint();
    }

    public void mouseClicked(MouseEvent e) {

        //if clicked on laser rotate it
        if (e.getX() >= 160 && e.getX() <= 160 + MIRROR_SIZE &&
        e.getY() >= 165 && e.getY() <= 165 + MIRROR_SIZE) {

            if (ourLaser == redQuestionMark || ourLaser == redLasers[3]) {
                ourLaser = redLasers[0];
                redIndex = 0;
                repaint();
            }
            else {
                redIndex++; 
                ourLaser = redLasers[redIndex];
                repaint();
            }
        }

        //if clicked on movable mirror1 rotate it
        else if (e.getX() >= x1 && e.getX() <= x1 + MIRROR_SIZE &&
        e.getY() >= y1 && e.getY() <= y1 + MIRROR_SIZE) {

            if (ourMoveablePurple1 == purpleQuestionMark || 
            ourMoveablePurple1 == purpleMirrors[3]) {
                ourMoveablePurple1 = purpleMirrors[0];
                purpleIndex = 0;
                repaint();
            }
            else {
                purpleIndex++; 
                ourMoveablePurple1 = purpleMirrors[purpleIndex];
                repaint();
            }
        }

        //if clicked on movable mirror2 rotate it
        else if (e.getX() >= x2 && e.getX() <= x2 + MIRROR_SIZE &&
        e.getY() >= y2 && e.getY() <= y2 + MIRROR_SIZE) {

            if (ourMoveablePurple2 == purpleQuestionMark || 
            purpleIndex2 == 3) {
                ourMoveablePurple2 = purpleMirrors[0];
                purpleIndex2 = 0;
                repaint();
            }
            else {
                purpleIndex2++; 
                ourMoveablePurple2 = purpleMirrors[purpleIndex2];
                repaint();
            }
        }

        //if clicked on movable green mirror rotate it
        else if (e.getX() >= x3 && e.getX() <= x3 + MIRROR_SIZE &&
        e.getY() >= y3 && e.getY() <= y3 + MIRROR_SIZE) {

            if (ourMoveableGreen == greenQuestionMark || 
            ourMoveableGreen == greenMirrors[0]) {
                ourMoveableGreen = greenMirrors[1];
                repaint();
            }
            else {
                ourMoveableGreen = greenMirrors[0];
                repaint();
            }
        }
        
        //if clicked on rotatable target mirror rotate it
        else if (e.getX() >= 352 && e.getX() <= 352 + MIRROR_SIZE &&
        e.getY() >= 69 && e.getY() <= 69 + MIRROR_SIZE) {

            if (ourPurpleWithTarget == purpleWithRedQuestionMark || 
            ourPurpleWithTarget == purpleMirrorsWithTarget[3]) {
                ourPurpleWithTarget = purpleMirrorsWithTarget[0];
                purpleWithTargetIndex = 0;
                repaint();
            }
            else {
                purpleWithTargetIndex++; 
                ourPurpleWithTarget = purpleMirrorsWithTarget[purpleWithTargetIndex];
                repaint();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {
        if (dragging1) {
            //find drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());

            //if not within board put on side panel
            if (drop == null) {
                x1 = 602;
                y1 = 75;
                mirrorPoints[3] = sidePanel[0];
            }
            //if within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2] && drop != mirrorPoints[4] && drop != mirrorPoints[5]) {
                x1 = drop.x;
                y1 = drop.y;
                mirrorPoints[3] = drop;
            }
            //if over another piece leave it where it came from
            else {
                x1 = mirrorPoints[3].x;
                y1 = mirrorPoints[3].y;
            }

            repaint();
        }
        else if (dragging2) {
            //find drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());

            //if not within board put on side panel
            if (drop == null) {
                x2 =602;
                y2 = 170;
                mirrorPoints[4] = sidePanel[1];
            }
            //if within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2] && drop != mirrorPoints[3] && drop != mirrorPoints[5]) {
                x2 = drop.x;
                y2 = drop.y;
                mirrorPoints[4] = drop;
            }
            //if over another piece leave it where it came from
            else {
                x2 = mirrorPoints[4].x;
                y2 = mirrorPoints[4].y;
            }

            repaint();
        }
        else if (dragging3) {
            //find drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());

            //if not within board put on side panel
            if (drop == null) {
                x3 =602;
                y3 = 265;
                mirrorPoints[5] = sidePanel[2];
            }
            //if within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2] && drop != mirrorPoints[3] && drop != mirrorPoints[4]) {
                x3 = drop.x;
                y3 = drop.y;
                mirrorPoints[5] = drop;
            }
            //if over another piece leave it where it came from
            else {
                x3 = mirrorPoints[5].x;
                y3 = mirrorPoints[5].y;
            }

            repaint();
        }
    }

    /** 
     * determine if the mouse was pressed in the bounds of
     * our movable image.
     * 
     * @param e mouse event information
     */
    public void mousePressed(MouseEvent e) {
        correctSolution = fire = false;
        dragging1 = (e.getX() >= x1 && e.getX() <= x1 + MIRROR_SIZE &&
                    e.getY() >= y1 && e.getY() <= y1 + MIRROR_SIZE);
        if (dragging1) {
            xOffset = e.getX() - x1;
            yOffset = e.getY() - y1;
        }
        dragging2 = (e.getX() >= x2 && e.getX() <= x2 + MIRROR_SIZE &&
                    e.getY() >= y2 && e.getY() <= y2 + MIRROR_SIZE);
        if (dragging2) {
            xOffset = e.getX() - x2;
            yOffset = e.getY() - y2;
        }
        dragging3 = (e.getX() >= x3 && e.getX() <= x3 + MIRROR_SIZE &&
                    e.getY() >= y3 && e.getY() <= y3 + MIRROR_SIZE);
        if (dragging3) {
            xOffset = e.getX() - x3;
            yOffset = e.getY() - y3;
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (dragging1) {
            x1 = e.getX() - xOffset;
            y1 = e.getY() - yOffset;
            repaint();
        }
        if (dragging2) {
            x2 = e.getX() - xOffset;
            y2 = e.getY() - yOffset;
            repaint();
        }
        if (dragging3) {
            x3 = e.getX() - xOffset;
            y3 = e.getY() - yOffset;
            repaint();
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Intermediate Board Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        IntermediateBoardPanel panel = new IntermediateBoardPanel();
        frame.getContentPane().add(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }
}