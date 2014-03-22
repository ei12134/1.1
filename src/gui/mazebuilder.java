package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class mazebuilder extends JFrame implements KeyListener{
	public static void main(String[] args) {
		new mazebuilder();
	}

	public mazebuilder(){
		super("BEST LABIRINTH EU");
		setSize(600, 500);
                setMinimumSize(new Dimension(getWidth(),getHeight()));// para ter tamanho minimo de como inicia
		setLocationRelativeTo(null);
		
		
		JPanel panel = new JPanel();
                panel.setSize(new Dimension(100,100));
                
		Container c = getContentPane();
                c.setLayout(new BorderLayout());
		c.add(panel,BorderLayout.CENTER);
		
		JButton exit = new JButton("Exit");
		c.add(exit,BorderLayout.SOUTH);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();// fecha a thread da Jframe e continua o programa na main e acaba
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
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