import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
class LaserMazeBoardPanel extends JPanel implements MouseListener {
    private Image board;
    private Image purpleMirror;

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
        BufferedImage buttonImage1;
        BufferedImage greenMirror;
        JButton button1;
        JButton button2;
        try
        {

            buttonImage1 = ImageIO.read(new File("PurpleMirror.JPG"));
            greenMirror = ImageIO.read(new File("GreenMirror.JPG"));
            button1 = new JButton(new ImageIcon(buttonImage1));
            button1.setBorder(BorderFactory.createEmptyBorder());
            button1.setContentAreaFilled(false);
            button1.setBounds(55,350,90,90);
            button1.setBorder(BorderFactory.createEmptyBorder());
            button1.setContentAreaFilled(false); 
            panel.add(button1);
            
            button2 = new JButton(new ImageIcon(greenMirror));
            button2.setBorder(BorderFactory.createEmptyBorder());
            button2.setContentAreaFilled(false);
            button2.setBounds(550,50,175,175);
            button2.setBorder(BorderFactory.createEmptyBorder());
            button2.setContentAreaFilled(false);
            panel.add(button2);
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
        g.drawImage(purpleMirror,100,100,null);
    }

}
