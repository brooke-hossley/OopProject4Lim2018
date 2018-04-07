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
                 * @param mE The pressing of the mouse causing the event
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
                 * 
                 */
                @Override
                public void mouseReleased(MouseEvent me)
                {
                    mousePressed = false;
                    repaint();
                }

                /**
                 * 
                 */
                @Override
                public void mouseExited(MouseEvent me)
                {
                    mouseOver = false;
                    mousePressed = false;
                    repaint();
                }

                /**
                 * 
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
     * 
     */
    private int getDiameter()
    {
        int diameter = Math.min(getWidth(), getHeight());
        return diameter;
    }

    /**
     * 
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
     * 
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
        g.fillOval(getWidth()/2 - radius, 

            getHeight()/2 - 
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

    private static void laserSound() throws Exception
    {
        File soundFile = new File("Laser_Gun2.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

        Clip clip = AudioSystem.getClip();

        clip.open(audioIn);

        clip.start();
        if (clip.isRunning()) clip.stop();

    }
}