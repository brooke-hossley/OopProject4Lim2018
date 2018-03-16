import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
/**
 * Write a description of class BoardPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BoardPanel extends JFrame
{
    JPanel panel = new JPanel ();
    JButton buttons [] = new JButton[25];

    public static void main(String args[])
    {
        new BoardPanel();   
    }

    public BoardPanel()
    {
        super("BoardPanel");
        setSize(500,500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(5,5,5,5));
        GridBagConstraints c = new GridBagConstraints();
        for(int i=0; i<25;i++)
        {
            buttons[i] = new JButton();
            //buttons[i].setPreferredSize(new Dimension(100,100));
            panel.add(buttons[i]);
        }
        add(panel);
        setVisible(true);
    }

}
