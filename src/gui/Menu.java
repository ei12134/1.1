package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JButton randomGameButton;
    private JPanel panel1;
    private JButton defaultGameButton;
    private JButton exitButton;


    public Menu(){
        super("Menu");
        setContentPane(panel1);
        pack();
        setSize(new Dimension(800, 600));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        //Called when the Exit button is clicked
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        //Called when the start random game button is clicked
        randomGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRandomBoard();
            }
        });
    }


    public void startRandomBoard() {
        Board board = new Board(21);

        JFrame boardFrame = new JFrame();
        boardFrame.add(board);
        boardFrame.setTitle("Random Maze");
        boardFrame.setResizable(false);
        boardFrame.setContentPane(board);
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardFrame.setSize(new Dimension(32 * 21, 32 * 21));
        boardFrame.setVisible(true);
    }
}
