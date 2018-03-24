import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
class LaserMazeBoardPanel extends JPanel implements MouseListener {
    private Image board;
    private static JLabel pic1, pic2, pic3;
    private Point p = null;
    public LaserMazeBoardPanel(String board) {
        this(new ImageIcon(board).getImage());
    }

    public LaserMazeBoardPanel(Image board) {
        this.board = board;
        Dimension size = new Dimension(board.getWidth(null), board.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        addMouseListener(this);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame();
        LaserMazeBoardPanel panel = new LaserMazeBoardPanel(new ImageIcon("Images\\Board.PNG").getImage());
        frame.getContentPane().add(panel);
        frame.setTitle("Laser Maze");

        CircleButton fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        panel.add(fireButton);

        BufferedImage purpleMirrorTarget;
        BufferedImage purpleMirror;
        BufferedImage purpleQuestionMark;
        BufferedImage redLaser;
        BufferedImage redQuestionMark;

        JButton redButton;
        //position [3,0] mirror
        JButton purpleButton3_0;
        //position [0,4] mirror
        JButton purpleButton0_4;
        //mirror to add
        JButton purpleButtonA;

        try
        {
            //load in needed images
            purpleMirrorTarget = ImageIO.read(new File("Images\\PurpleMirrorWithTargetDown.JPG"));
            purpleMirror = ImageIO.read(new File("Images\\PurpleMirrorLeft.JPG"));
            purpleQuestionMark = ImageIO.read(new File("Images\\PurpleQuestionMark.JPG"));
            redLaser = ImageIO.read(new File("Images\\LaserUp.JPG"));
            redQuestionMark = ImageIO.read(new File("Images\\RedLaserQuestion.JPG"));

            //ut 4 mirror buttons on board
            purpleButton0_4 = new JButton(new ImageIcon(purpleMirrorTarget));
            purpleButton0_4.setBorder(BorderFactory.createEmptyBorder());
            purpleButton0_4.setContentAreaFilled(false);
            purpleButton0_4.setBounds(448,69,80,80);
            panel.add(purpleButton0_4);

            purpleButton3_0 = new JButton(new ImageIcon(purpleMirror));
            purpleButton3_0.setBorder(BorderFactory.createEmptyBorder());
            purpleButton3_0.setContentAreaFilled(false);
            purpleButton3_0.setBounds(65,356,80,80);
            panel.add(purpleButton3_0);

            redButton = new JButton(new ImageIcon(redQuestionMark));
            redButton.setBorder(BorderFactory.createEmptyBorder());
            redButton.setContentAreaFilled(false);
            redButton.setBounds(65,452,80,80);
            panel.add(redButton);

            purpleButtonA = new JButton(new ImageIcon(purpleQuestionMark));
            purpleButtonA.setBorder(BorderFactory.createEmptyBorder());
            purpleButtonA.setContentAreaFilled(false);
            purpleButtonA.setBounds(602,75,80,80);
            panel.add(purpleButtonA);
        }
        catch(Exception e){
            System.out.println("File not found");
        }

        frame.pack();
        frame.setVisible(true);
    }

    public void mousePressed(MouseEvent me) {
        p = me.getLocationOnScreen();
    }

    public void mouseReleased(MouseEvent me) { }

    public void mouseEntered(MouseEvent me) { }

    public void mouseExited(MouseEvent me) { }

    public void mouseClicked(MouseEvent me) { }

    public void mouseDragged(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        Point l = c.getLocation();
        Point here = e.getLocationOnScreen();
        c.setLocation(l.x + here.x - p.x, l.y + here.y - p.y);
        p = here;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(board, 0, 0, null);
    }

}
