package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import maze.Maze;
public class mazebuilder extends JFrame implements KeyListener{
	public static void main(String[] args) throws IOException {
		new mazebuilder();
	}

	@SuppressWarnings("deprecation")
	public mazebuilder() throws IOException{
		//ssuper("BEST LABIRINTH EU");
		setSize(400, 500);
		JFrame.setDefaultLookAndFeelDecorated(true);
	    final JFrame frame = new JFrame("BEST LABIRINTH EU");
	    Container c = frame.getContentPane();
	
	    c.setLayout(new GridLayout(50, 50));
	    
	  
	
	//   frame.add(new Board());
	   
	    
	    try {
			URL url = new URL("http://th09.deviantart.net/fs71/PRE/f/2012/365/2/0/dragon_girl_by_byzwa_dher-d5ps5p5.jpg");
			Image image = ImageIO.read(url);
			c.add(new JLabel(new ImageIcon(image)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   // frame.add(new JButton("Button 3"));
	    

		JButton exit = new JButton("Exit");
		c.add(exit);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				System.exit(0);
			}
		});
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}