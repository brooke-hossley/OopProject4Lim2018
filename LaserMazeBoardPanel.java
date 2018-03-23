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
        panel.add(fireButton);
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
            //System.out.println("found purple");
            String s = "." + File.separator + "Images" + File.separator + "GreenQuestionMark.JPG";
            //System.out.println(s);
            greenMirror = ImageIO.read(new File(s).getAbsoluteFile());
            //System.out.println("found green");
            laserQuestionMark = ImageIO.read(new File("Images\\RedLaserQuestion.JPG"));
            //System.out.println("found red");
            
            purpleButton = new JButton(new ImageIcon(purpleMirror));
            purpleButton.setBorder(BorderFactory.createEmptyBorder());
            purpleButton.setContentAreaFilled(false);
            purpleButton.setBounds(70,360,90,90);
            panel.add(purpleButton);
            //System.out.println("added purple");
            
            greenButton = new JButton(new ImageIcon(greenMirror));
            greenButton.setBorder(BorderFactory.createEmptyBorder());
            greenButton.setContentAreaFilled(false);
            greenButton.setBounds(550,50,175,175);
            panel.add(greenButton);
            //System.out.println("added green");
            
            redButton = new JButton(new ImageIcon(laserQuestionMark));
            redButton.setBorder(BorderFactory.createEmptyBorder());
            redButton.setContentAreaFilled(false);
            redButton.setBounds(70,455,90,90);
            panel.add(redButton);
            //System.out.println("added red");
        }
        catch(IOException e){
            //System.out.println("Catch block");
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
