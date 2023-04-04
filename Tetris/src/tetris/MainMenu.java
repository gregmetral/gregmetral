package tetris;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class MainMenu extends JFrame{
	
	public MainMenu() {

		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,350);
        this.setLocationRelativeTo(null);
		JButton level_1 = new JButton("Level 1");
		level_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFocusable(false);
				setVisible(false);
				Game.start(0);
			}
		});
		level_1.setBounds(55, 30, 89, 23);
		getContentPane().add(level_1);
		
		JButton level_2 = new JButton("Level 2");
		level_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFocusable(false);
				setVisible(false);
				Game.start(1000);
			}
		});
		level_2.setBounds(244, 30, 89, 23);
		getContentPane().add(level_2);
		
		JButton level_3 = new JButton("Level 3");
		level_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFocusable(false);
				setVisible(false);
				Game.start(3000);
			}
		});
		level_3.setBounds(55, 64, 89, 23);
		getContentPane().add(level_3);
		
		JButton level_4 = new JButton("Level 4");
		level_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFocusable(false);
				setVisible(false);
				Game.start(6000);
			}
		});
		level_4.setBounds(244, 64, 89, 23);
		getContentPane().add(level_4);
		
		JButton level_5 = new JButton("Level 5");
		level_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFocusable(false);
				setVisible(false);
				Game.start(10000);
			}
		});
		level_5.setBounds(144, 93, 89, 23);
		getContentPane().add(level_5);
		
		JLabel lblNewLabel = new JLabel("Choose the level to start at");
		lblNewLabel.setBounds(136, 11, 197, 14);
		getContentPane().add(lblNewLabel);
		
		
	
		Icon icon = new ImageIcon("download.png");
		
		JButton bouttonJambon = new JButton(icon);
		bouttonJambon.setBounds(31, 127, 312, 173);
		
		getContentPane().add(bouttonJambon);

		
		
	    this.setResizable(false); 

	}

	
	
	public static void main(String[] args) {
		
		java.awt.EventQueue.invokeLater(new Runnable() { 
			public void run() {
				
				new MainMenu().setVisible(true);
			}
			
		});
	}
}
