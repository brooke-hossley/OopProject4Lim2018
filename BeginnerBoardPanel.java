import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
/**
 * Write a description of class BeginnerBoardPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BeginnerBoardPanel extends JPanel implements MouseListener, MouseMotionListener
{
    /** position of the rectangle */
    private int x, y;

    /** am I currently dragging the rectangle? */
    private boolean dragging;
    
    private boolean rotating;

    /** remember mouse "grab point" in the rectangle */
    private int xOffset, yOffset;

    /** icon size */
    public static final int MIRROR_SIZE = 80;

    private static Image board;
    private Image redLaser, redQuestionMark, purpleMirror, purpleMirrorTarget, purpleQuestionMark;

    public BeginnerBoardPanel() {
        board = new ImageIcon("Images\\Board.PNG").getImage();
        redLaser = new ImageIcon("Images\\LaserUp.JPG").getImage();
        redQuestionMark = new ImageIcon("Images\\RedLaserQuestion.JPG").getImage();
        purpleMirror = new ImageIcon("Images\\PurpleMirrorLeft.JPG").getImage();
        purpleMirrorTarget = new ImageIcon("Images\\PurpleMirrorWithTargetDown.JPG").getImage();
        purpleQuestionMark = new ImageIcon("Images\\PurpleQuestionMark.JPG").getImage();

        //setBackground( Color.WHITE );
        //setOpaque(true);
        Dimension size = new Dimension(board.getWidth(null), board.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        // initial position of our draggable rectangle
        x = 602;
        y = 75;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * THE REALLY IMPORTANT METHOD which decides where to draw stuff
     * Applet's paint method to manage our graphics
     * 
     * @param g the Graphics reference
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(board, 0, 0, null);
        g.drawImage(purpleMirrorTarget, 448, 69, null);
        g.drawImage(purpleMirror, 65, 356, null);
        g.drawImage(redQuestionMark, 65, 452, null);
        g.drawImage(purpleQuestionMark, x, y, null);

    }

    public void mouseClicked(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    /** 
     * determine if the mouse was pressed in the bounds of
     * our movable image.
     * 
     * @param e mouse event information
     */
    public void mousePressed(MouseEvent e) {

        //////////////definitely gonna have to add to this for rotation\\\\\\\\\\\\\\

        dragging = (e.getX() >= x && e.getX() <= x + MIRROR_SIZE &&
                    e.getY() >= y && e.getY() <= y + MIRROR_SIZE);
        xOffset = e.getX() - x;
        yOffset = e.getY() - y;
    }

    public void mouseReleased(MouseEvent e) {
        //repaint();
    }

    public void mouseDragged(MouseEvent e) {
        
        //Since x and y are reset here
        //Will need to add functionality for making sure mirror goes into actual board spot
        
        if (dragging) {
            x = e.getX() - xOffset;
            y = e.getY() - yOffset;
            repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {

        // if (e.getX() >= x && e.getX() <= x + MIRROR_SIZE &&
        // e.getY() >= y && e.getY() <= y + MIRROR_SIZE) {
            //rectColor = Color.red;
        // }
        // else {
            // rectColor = Color.black;
        // }
        // repaint();
    }

    
    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("BeginnerBoardPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BeginnerBoardPanel panel = new BeginnerBoardPanel();
        frame.getContentPane().add(panel);
        //panel.addImage(board);
        CircleButton fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        panel.add(fireButton);

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