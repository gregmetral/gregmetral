package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

class Menu extends JFrame
{

	public Menu() //cration d'une fenetre pour choisir le nombre de joueurs
	{
		// set default screen settings
		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
		this.setVisible(true);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 420, 350);
		// create main label
		JLabel labelJoueurs = new JLabel("Choisissez le nombre de joueurs");
		labelJoueurs.setBounds(113, 29, 199, 14);
		mainPanel.add(labelJoueurs);
		
		// create button for number of players

		//2 players
		JButton joueur2 = new JButton("2 joueurs");
		joueur2.setBounds(68, 101, 89, 23);
		joueur2.addActionListener(new CreateGame(2));
		mainPanel.add(joueur2);

		//3 players
		JButton joueur3 = new JButton("3 joueurs");
		joueur3.setBounds(68, 160, 89, 23);
		joueur3.addActionListener(new CreateGame(3));
		mainPanel.add(joueur3);

		//4 players
		JButton joueur4 = new JButton("4 joueurs");
		joueur4.setBounds(247, 101, 89, 23);
		joueur4.addActionListener(new CreateGame(4));
		mainPanel.add(joueur4);

		//5 players
		JButton joueur5 = new JButton("5 joueurs");
		joueur5.setBounds(247, 160, 89, 23);
		joueur5.addActionListener(new CreateGame(5));
		mainPanel.add(joueur5);

		//6 players
		JButton joueur6 = new JButton("6 joueurs");
		joueur6.setBounds(159, 220, 89, 23);
		joueur6.addActionListener(new CreateGame(6));
		mainPanel.add(joueur6);

		getContentPane().add(mainPanel);
	}

	class CreateGame implements ActionListener{
		int nbPlayers;
		public CreateGame(int nbPlayers){
			this.nbPlayers = nbPlayers;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			setFocusable(false);
			new GameGUI(nbPlayers).setVisible(true);
			dispose();
		}
	}
	
	public static void main(String[] args) 
	{
		new Menu();
	}
}
