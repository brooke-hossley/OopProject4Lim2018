import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
class LaserMazeBoardPanel extends JPanel implements MouseListener {
    private Image board;
    
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
        LaserMazeBoardPanel panel = new LaserMazeBoardPanel(new ImageIcon("Board.png").getImage());
        frame.getContentPane().add(panel);
        frame.setTitle("Lase Maze");
        CircleButton fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        BufferedImage purpleMirror;
        BufferedImage greenMirror;
        BufferedImage laserQuestionMark;
        JButton purpleButton;
        JButton greenButton;
        JButton redButton;
        try
        {
            //card 1 mirror adding
            purpleMirror = ImageIO.read(new File("PurpleMirror.JPG"));
            greenMirror = ImageIO.read(new File("GreenMirror.JPG"));
            laserQuestionMark = ImageIO.read(new File("Images\\RedLaserQuestion.JPG"));
            
            purpleButton = new JButton(new ImageIcon(purpleMirror));
            purpleButton.setBorder(BorderFactory.createEmptyBorder());
            purpleButton.setContentAreaFilled(false);
            purpleButton.setBounds(70,360,90,90);
            purpleButton.setBorder(BorderFactory.createEmptyBorder());
            purpleButton.setContentAreaFilled(false); 
            panel.add(purpleButton);
            
            greenButton = new JButton(new ImageIcon(greenMirror));
            greenButton.setBorder(BorderFactory.createEmptyBorder());
            greenButton.setContentAreaFilled(false);
            greenButton.setBounds(550,50,175,175);
            greenButton.setBorder(BorderFactory.createEmptyBorder());
            greenButton.setContentAreaFilled(false);
            //panel.add(greenButton);
            
            redButton = new JButton(new ImageIcon(laserQuestionMark));
            redButton.setBorder(BorderFactory.createEmptyBorder());
            redButton.setContentAreaFilled(false);
            redButton.setBounds(70,455,90,90);
            redButton.setBorder(BorderFactory.createEmptyBorder());
            redButton.setContentAreaFilled(false);
            panel.add(redButton);
        }
        catch(Exception e){}
        panel.add(fireButton);
        frame.pack();
        frame.setVisible(true);
    }

    public void mousePressed(MouseEvent me) { }

    public void mouseReleased(MouseEvent me) { }

    public void mouseEntered(MouseEvent me) { }

    public void mouseExited(MouseEvent me) { }

    public void mouseClicked(MouseEvent me) { }

    public void paintComponent(Graphics g) {
        g.drawImage(board, 0, 0, null);
    }

}
