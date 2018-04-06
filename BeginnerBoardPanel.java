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
public class BeginnerBoardPanel extends JPanel implements MouseListener, MouseMotionListener , ActionListener
{
    //used to drag to correct spot
    private BoardLocations locations;
    private Point mirrorPoints[], sidePanel;
    
    // position of the movable purple mirror
    private int x, y;

    // am I currently dragging the movable mirror? 
    private boolean dragging;

    // remember mouse "grab point" in the movable mirror 
    private int xOffset, yOffset;

    // icon size 
    public static final int MIRROR_SIZE = 80;

    //all the images we're going to use
    private static Image board;
    private Image[] redLasers,  purpleMirrors;
    private Image redQuestionMark, purpleMirrorTarget, purpleQuestionMark;

    //what the current image being used by game is for laser and moveable mirror
    private Image ourLaser, ourMovablePurple;

    //array index ourLaser and ourMovablePurple currently are
    private int redIndex, purpleIndex;

    private CircleButton fireButton;
    private boolean draw = false;
    
    public BeginnerBoardPanel() {
        locations = new BoardLocations();
        sidePanel = new Point(602, 75);
        mirrorPoints = new Point[4];
        mirrorPoints[0] = locations.locationPoints[4][0];
        mirrorPoints[1] = locations.locationPoints[0][3];
        mirrorPoints[2] = locations.locationPoints[0][4];
        mirrorPoints[3] = sidePanel;
        
        //set images we start with on board
        String dir = "Images\\";
        board = new ImageIcon(dir + "Board.PNG").getImage();
        redQuestionMark = new ImageIcon(dir + "RedLaserQuestion.JPG").getImage();
        purpleMirrorTarget = new ImageIcon(dir + "PurpleMirrorWithTargetDown.JPG").getImage();
        purpleQuestionMark = new ImageIcon(dir + "PurpleQuestionMark.JPG").getImage();
        ourLaser = redQuestionMark;
        ourMovablePurple = purpleQuestionMark;

        // initial position of our movable mirror
        x = 602;
        y = 75;

        //load in purple and red piece images for rotation later
        purpleMirrors = new Image[4];
        purpleMirrors[0] = new ImageIcon(dir + "PurpleMirrorUp.JPG").getImage();
        purpleMirrors[1] = new ImageIcon(dir + "PurpleMirrorRight.JPG").getImage();
        purpleMirrors[2] = new ImageIcon(dir + "PurpleMirrorDown.JPG").getImage();
        purpleMirrors[3] = new ImageIcon(dir + "PurpleMirrorLeft.JPG").getImage();
        
        redLasers = new Image[4];
        redLasers[0] = new ImageIcon(dir + "LaserUp.JPG").getImage();
        redLasers[1] = new ImageIcon(dir + "LaserRight.JPG").getImage();
        redLasers[2] = new ImageIcon(dir + "LaserDown.JPG").getImage();
        redLasers[3] = new ImageIcon(dir + "LaserLeft.JPG").getImage();
        
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
     * THE REALLY IMPORTANT METHOD which decides where to draw stuff
     * Applet's paint method to manage our graphics
     * 
     * @param g the Graphics reference
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //these will stay same because hardcoded
        g.drawImage(board, 0, 0, null);
        g.drawImage(purpleMirrorTarget, 448, 69, null);
        g.drawImage(purpleMirrors[3], 65, 356, null);

        //these can both rotate and purple can be dragged
        g.drawImage(ourLaser, 65, 452, null);
        g.drawImage(ourMovablePurple, x, y, null);

        if (draw) {
            g.setColor(Color.RED);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
            g.drawLine(105,452,105,400);
            g.drawLine(105,400,480,400);
            g.drawLine(486,400,486,138);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //this is what gets called when the fire button is pressed
        draw = (mirrorPoints[3] == locations.locationPoints[4][3] && 
        ourLaser == redLasers[0] && ourMovablePurple == purpleMirrors[1]);
        repaint();
    }

    public void mouseClicked(MouseEvent e) {

        //if clicked on laser rotate it
        if (e.getX() >= 65 && e.getX() <= 65 + MIRROR_SIZE &&
        e.getY() >= 452 && e.getY() <= 452 + MIRROR_SIZE) {

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

        //if clicked on movable mirror rotate it
        else if (e.getX() >= x && e.getX() <= x + MIRROR_SIZE &&
        e.getY() >= y && e.getY() <= y + MIRROR_SIZE) {
            
            if (ourMovablePurple == purpleQuestionMark || 
            ourMovablePurple == purpleMirrors[3]) {
                ourMovablePurple = purpleMirrors[0];
                purpleIndex = 0;
                repaint();
            }
            else {
                purpleIndex++; 
                ourMovablePurple = purpleMirrors[purpleIndex];
                repaint();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {
        if (dragging) {
            //find drop location for where mouse was released
            Point drop = locations.getDropPoint(e.getX(), e.getY());
            
            //if not within board put on side panel
            if (drop == null) {
                x = 602;
                y = 75;
                mirrorPoints[3] = sidePanel;
            }
            
            //if within board and not over another piece, place it
            else if (drop != mirrorPoints[0] && drop != mirrorPoints[1] && 
            drop != mirrorPoints[2]) {
                x = drop.x;
                y = drop.y;
                mirrorPoints[3] = drop;
            }
            
            //if over another piece leave it where it came from
            else {
                x = mirrorPoints[3].x;
                y = mirrorPoints[3].y;
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
        draw = false;
        dragging = (e.getX() >= x && e.getX() <= x + MIRROR_SIZE &&
            e.getY() >= y && e.getY() <= y + MIRROR_SIZE);
        xOffset = e.getX() - x;
        yOffset = e.getY() - y;
    }

    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            x = e.getX() - xOffset;
            y = e.getY() - yOffset;
            repaint();
        }
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BeginnerBoardPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BeginnerBoardPanel panel = new BeginnerBoardPanel();
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
