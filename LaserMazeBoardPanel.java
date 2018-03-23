import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
class LaserMazeBoardPanel extends JPanel implements MouseListener {
    private Image board;
    private static JLabel pic1, pic2, pic3;
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
        LaserMazeBoardPanel panel = new LaserMazeBoardPanel(new ImageIcon("Images\\Board.png").getImage());
        frame.getContentPane().add(panel);
        frame.setTitle("Laser Maze");
        CircleButton fireButton = new CircleButton("FIRE");
        fireButton.setBounds(595,490,100,100);
        panel.add(fireButton);
        BufferedImage purpleMirror;
        BufferedImage greenMirror;
        BufferedImage laserQuestionMark;
        JButton purpleButton;
        JButton redButton;
        try
        {
            purpleMirror = ImageIO.read(new File("Images\\PurpleMirrorLeft.JPG"));
            purpleButton = new JButton(new ImageIcon(purpleMirror));
            purpleButton.setBorder(BorderFactory.createEmptyBorder());
            purpleButton.setContentAreaFilled(false);
            purpleButton.setBounds(55,350,90,90);
            panel.add(purpleButton);

            laserQuestionMark = ImageIO.read(new File("Images\\RedLaserQuestion.JPG"));
            redButton = new JButton(new ImageIcon(laserQuestionMark));
            redButton.setBorder(BorderFactory.createEmptyBorder());
            redButton.setContentAreaFilled(false);
            redButton.setBounds(70,455,90,90);
            panel.add(redButton);
        }
        catch(Exception e){
            System.out.println("File not found");
        }

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
