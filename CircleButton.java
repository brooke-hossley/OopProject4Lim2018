import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.*;
/**
 * ALISSA?
 * 
 * @author Alissa Ronca, Patrick Barber, Brooke Hossley, 
 *      Chris Adams, Hieu Le
 * @version Spring 2018
 */
public class CircleButton extends JButton
{
    private boolean mouseOver = false;
    private boolean mousePressed = false;
    /**
     * The constructor for the FIRE button for the laser
     * 
     * @param text ALISSA?
     */
    protected CircleButton(String text)
    {
        super(text);
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);

        MouseAdapter mouseListener = new MouseAdapter()
            {
                /**
                 * Determines if the mouse is pressed within the 
                 * correct X and Y coordinates
                 * 
                 * @param mE The mouse event
                 * @see java.awt.event
                 */
                @Override
                public void mousePressed(MouseEvent mE)
                {
                    if(contains(mE.getX(), mE.getY()))
                    {
                        mousePressed = true;
                        repaint();
                    }
                }

                /**
                 * Determines if the mouse is released
                 * 
                 * @param mE The mouse event
                 * @see java.awt.event
                 */
                @Override
                public void mouseReleased(MouseEvent me)
                {
                    mousePressed = false;
                    repaint();
                }

                /**
                 * Determines if the mouse has exited a 
                 * fixed space
                 * 
                 * @param mE The mouse event
                 * @see java.awt.event
                 */
                @Override
                public void mouseExited(MouseEvent me)
                {
                    mouseOver = false;
                    mousePressed = false;
                    repaint();
                }

                /**
                 * Determines if the mouse is moved within 
                 * a fixed space
                 * 
                 * @param mE The mouse event
                 * @see java.awt.event
                 */
                @Override
                public void mouseMoved(MouseEvent me)
                {
                    mouseOver = contains(me.getX(), me.getY());
                    repaint();
                }   
            };
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);      
    }

    /**
     * Determins the diameter of the circle button
     * 
     * @return The diameter of the button
     */
    private int getDiameter()
    {
        int diameter = Math.min(getWidth(), getHeight());
        return diameter;
    }

    /**
     * Constructor for the preferred size of the 
     * button
     * 
     * @see java.awt.Dimension
     */
    @Override
    public Dimension getPreferredSize()
    {
        FontMetrics metrics = getGraphics().getFontMetrics(getFont());
        int minDiameter = 10 + 
            Math.max(metrics.stringWidth(getText()), metrics.getHeight());
        return new Dimension(minDiameter, minDiameter);
    }

    /**
     * 
     */
    @Override
    public boolean contains(int x, int y)
    {
        int radius = getDiameter()/2;
        return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
    }

    /**
     * Method to create the circle button
     * 
     * @see java.awt.Graphics
     */
    @Override
    public void paintComponent(Graphics g)
    {
        int diameter = getDiameter();
        int radius = diameter/2;

        if(mousePressed)
        {
            g.setColor(new Color(204, 0, 0));
            try{
                laserSound();
            }
            catch(Exception e){}

        }
        else
        {
            g.setColor(Color.RED);
        }
        g.fillOval(getWidth()/2 - radius, getHeight()/2 - 
            radius, diameter, diameter);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 25));
        FontMetrics metrics = g.getFontMetrics(getFont());
        int stringWidth = metrics.stringWidth(getText());
        int stringHeight = metrics.getHeight();
        g.drawString(getText(), 

            getDiameter()/3 - stringWidth/2, 
            getHeight()/2 + stringHeight/2);
    }

    /**
     * Method for the laser firing sound
     * 
     * @throws The possiblility of the file not being found
     */
    private static void laserSound() throws Exception
    {
        //Creating the Sound file
        File soundFile = new File("Laser_Gun2.wav"); 
        //Allocate a AudioInputStream piped from a file
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        //Allocate a sound Clip resource
        Clip clip = AudioSystem.getClip();
        //Open the clip to load sound samples from the audio input stream
        clip.open(audioIn);
        //Play once
        clip.start();
        if (clip.isRunning()) clip.stop(); //Stop playing 

    }
}