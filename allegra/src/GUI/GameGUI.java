package GUI;

import allegra.Card;
import allegra.GameManager;
import allegra.PlayerMatrix;
import GUI.EndingMenu;

import tools.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameGUI extends JFrame
{
	final int CARD_GAP = 10;
	final int CARD_SIZE = 64;
	final int PLAYER_GAP = 30;
	private int[] WindowSize = {CARD_SIZE*12 + CARD_GAP*12 + PLAYER_GAP*2 ,
		CARD_SIZE*9 + CARD_GAP*8 + PLAYER_GAP*2};

	private Container container;
	private GameManager game;
	private enum Stage {
		CHECKWIN,
		PICKPILE,
		DRAWPILE,
		REPLACE,
		FLIPCARD,
		STEAL,
		PICKSTEALER,
		STEALREPLACE,
		STEALPICK, PICKSWAP, SWAP
	}
	Map<Stage, String> StageMessage = new HashMap();

	private Stage currentStage = Stage.PICKPILE;
	private int indexPlayerPlaying = 0;
	private int indexPlayerStealing;
	private int indexCardSteal;

	private List<JPanel> listPlayers;
	private List<VolListener> listVolListener = new ArrayList<>();
	private JPanel pilePanel;
	private JButton endButton;

	private Card cardInUse;


	public GameGUI(int nbPlayers)
	{
		this.createScreen();
		this.pile();
		this.listPlayers = this.drawPlayerButtons(nbPlayers);
		this.game = new GameManager(nbPlayers);
		drawEnd();

		tools.setImage(pilePanel.getComponent(1), game.showTopDiscardPile());
	}

	class CardListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			JButton buttonPressed = (JButton) e.getSource();
			int cardIndex;
			int[] cardCoord;
			boolean neighbor=false;
			if (currentStage == Stage.STEALREPLACE || currentStage == Stage.PICKSWAP){// matrix from strealer
				cardIndex = listPlayers.get(indexPlayerStealing).getComponentZOrder(buttonPressed);
				if (cardIndex == -1){ //neighbor matrix
					cardIndex = listPlayers.get(game.getNeighborIndex(indexPlayerStealing)).getComponentZOrder(buttonPressed);
					neighbor = true;
				}
				cardCoord = convert(cardIndex, indexPlayerStealing, neighbor);
			}else{
				cardIndex = listPlayers.get(indexPlayerPlaying).getComponentZOrder(buttonPressed);
				if (cardIndex == -1){
					neighbor = true;
					cardIndex = listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)).getComponentZOrder(buttonPressed);
				}
				cardCoord = convert(cardIndex, indexPlayerPlaying, neighbor);
			}

			switch (currentStage) {
				case REPLACE, STEALREPLACE, DRAWPILE://all require to replace card
					int indexPlayerReplacing;
					if (currentStage == Stage.STEALREPLACE){ // replace as stealing player
						currentStage = Stage.PICKSWAP;
						
						indexPlayerReplacing = indexPlayerStealing;
					}else{ // replace as playing player
						currentStage = Stage.CHECKWIN;

						indexPlayerReplacing = indexPlayerPlaying;
						endButton.setText("End turn");
						endButton.setEnabled(true);
						tools.setEnabled(pilePanel, false);
					}
					// update card pressed with the replaced card image
					tools.setImage(buttonPressed, cardInUse.getValue());
					// get the discarded card
					System.out.println("replacing card at "+cardCoord[0]+" "+cardCoord[1]+" for "+indexPlayerReplacing);
					cardInUse = game.replaceCard(cardCoord[0], cardCoord[1], cardInUse, indexPlayerReplacing);

					// draw card face down on draw pile
					tools.setImage(pilePanel.getComponent(0), -2);
					// update discard pile card image
					tools.setImage(pilePanel.getComponent(1), cardInUse.getValue());
					game.showMatrix(indexPlayerReplacing);
					removeAlligne(indexPlayerReplacing);

					break;
				case FLIPCARD: // flip a card to show it
					if (game.isFlipped(cardCoord[0], cardCoord[1])){ //if card is already flip do nothing
						break;
					}

					currentStage = Stage.CHECKWIN;
					// set image of card fliped
					tools.setImage(buttonPressed, game.flipCard(cardCoord[0], cardCoord[1]));
					// deactivate player matrix
					tools.setEnabled(listPlayers.get(indexPlayerPlaying), false);
					tools.setEnabled(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)), false);
					// deactivate pile
					tools.setEnabled(pilePanel, false);
					// activate end button
					endButton.setEnabled(true);
					endButton.setText("End turn");
					// remove alligned cards
					game.showMatrix(indexPlayerPlaying);
					removeAlligne(indexPlayerPlaying);
					break;

				case PICKSWAP: // player playing picks a card to swap from playerStealing
					currentStage = Stage.SWAP;
					
					indexCardSteal = cardIndex;
					// disable the stealer matrix
					tools.setEnabled(listPlayers.get(indexPlayerStealing), false);
					tools.setEnabled(listPlayers.get(game.getNeighborIndex(indexPlayerStealing)), false);

					// enable the player playing
					tools.setEnabled(listPlayers.get(indexPlayerPlaying), true);
					tools.setPartialDisable(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)),game.getNeighborIndex(indexPlayerPlaying));
					
					break;
				case SWAP: // player playing selects a card to swap and swaps it with swapPicked card
					currentStage = Stage.CHECKWIN;
					// replace cards and store the moved cards
					Card[] cardsMoved = game.stealCard(cardCoord, indexPlayerStealing, tools.convert(indexCardSteal, indexPlayerStealing));
					// update the player playing card
					tools.setImage(buttonPressed, cardsMoved[0].getValue());
					// update the stealer card
					tools.setImage(listPlayers.get(indexPlayerStealing).getComponent(indexCardSteal), cardsMoved[1].getValue());

					//activate end button
					endButton.setEnabled(true);
					game.showMatrix(indexPlayerPlaying);
					removeAlligne(indexPlayerPlaying);

					break;
			}
		}

		public int[] convert(int cardIndex, int playerN, boolean neighbor){
			int[] cardCoord = new int[2];

			// if select shared column or not player playing
			if (neighbor){
				cardCoord = tools.convert(cardIndex, playerN);
				cardCoord[0] = 4;
				return cardCoord;
			}
			cardCoord = tools.convert(cardIndex, indexPlayerPlaying);
			return cardCoord;
		}
		
		public void removeAlligne(int indexPlayerReplacing){
			// check if cards alligned
			int[][] coordCardsToDiscard = game.checkAlligned(indexPlayerReplacing);
			if (coordCardsToDiscard != null){
				System.out.println(indexPlayerReplacing+" player playing");
				// go through the 3 coords to remove the cards
				for (int i = 0; i < coordCardsToDiscard.length; i++) {
					System.out.println(coordCardsToDiscard[i][0]+" "+coordCardsToDiscard[i][1]+" coord");
					// convert to JButton coord
					int [] coords = tools.convert(coordCardsToDiscard[i][0], coordCardsToDiscard[i][1], indexPlayerReplacing);
					System.out.println(coords[0]+" coord converted");
					// check if its current player or neighbor table
					if (coords[1] == 1) {
						listPlayers.get(indexPlayerReplacing).getComponent(coords[0]).setVisible(false);
					}else{
						listPlayers.get(game.getNeighborIndex(indexPlayerReplacing)).getComponent(coords[0]).setVisible(false);
					}
				}
			
			}
		}
	}

	class PileListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			JButton pile = (JButton) e.getSource();

			int pileIndex = pilePanel.getComponentZOrder(pile);
			// drawPile -> 0
			// discardPile -> 1

			switch (currentStage) {
				case PICKPILE:
					// pick card from the chosen pile
					cardInUse = game.pickCard(pileIndex);

					if (pileIndex == 0){// Draw => FlipCard or Replace or Steal
						currentStage = Stage.STEAL;
						pilePanel.getComponent(1).setEnabled(false);
						endButton.setEnabled(true);
						endButton.setText("End steal");
						tools.setImage(pile, cardInUse.getValue());
						listVolListener.forEach(v -> v.volButton.setEnabled(true));
						listVolListener.get(indexPlayerPlaying).volButton.setEnabled(false);
					}else{ //discard => REPLACE (deactivate pile)
						tools.setEnabled(pilePanel, false);
						currentStage = Stage.REPLACE;
					}

					// allows player to see their cards but cant interact with them
					tools.setEnabled(listPlayers.get(indexPlayerPlaying), true);
					tools.setPartialDisable(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)), game.getNeighborIndex(indexPlayerPlaying));
					break;

				case DRAWPILE: // equivelent to discarding a card
					if (pileIndex == 1) {
						currentStage = Stage.FLIPCARD;
						tools.setImage(pilePanel.getComponent(0), -2);
						game.discardCard(cardInUse);
						tools.setImage(pilePanel.getComponent(1), cardInUse.getValue());
						tools.setEnabled(pilePanel, false);
					}
					break;
			}
		}
	}

	class EndListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (currentStage) {
				case CHECKWIN:
					game.checkAllVisible();

					if (game.checkEndGame()){
						new GUI.EndingMenu(game.getHashMap());
					}

					currentStage = Stage.PICKPILE;
					
					// deactivate player matrix before switching to next player
					tools.setEnabled(listPlayers.get(indexPlayerPlaying), false);
					tools.setEnabled(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)), false);

					// get index of next player who hasn't played last turn
					indexPlayerPlaying = game.nextPlayer();
					tools.setEnabled(pilePanel, true);
					endButton.setEnabled(false);
					break;
				
				case STEAL:
					for (VolListener volListener : listVolListener) {
						volListener.volButton.setEnabled(false);
					}
					List<VolListener> steal = listVolListener.stream().filter(v -> v.steal).collect(Collectors.toList()); 
					if (steal.size() != 0){ // player has asked to steal => accept or decline
						currentStage = Stage.PICKSTEALER;
						for (VolListener volListener : steal) {
							volListener.volButton.setEnabled(true);
							volListener.volButton.setText("pick");
						}
						endButton.setText("refuse");
					}else{ // no player wants to steal => replace or discard CardInUse
						currentStage = Stage.DRAWPILE;
						tools.setEnabled(listPlayers.get(indexPlayerPlaying), true);
						tools.setPartialDisable(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)), game.getNeighborIndex(indexPlayerPlaying));
						pilePanel.getComponent(1).setEnabled(true);
						endButton.setEnabled(false);
					}
					break;

				case PICKSTEALER: // steal was refused
					currentStage = Stage.DRAWPILE;
					tools.setEnabled(listPlayers.get(indexPlayerPlaying), true);
					tools.setPartialDisable(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)), game.getNeighborIndex(indexPlayerPlaying));
					pilePanel.getComponent(1).setEnabled(true);
					endButton.setEnabled(false);
					break;
					
			
				default:
					break;
			}
		}
	}

	class VolListener implements ActionListener{
		private int id;
		private JButton volButton;
		private boolean steal = false;

		public VolListener(int id, JButton button){
			this.id = id;
			this.volButton = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (currentStage) {
				case STEAL:
					steal = true;
					volButton.setEnabled(false);
					break;
				case PICKSTEALER: // picking stealer
					currentStage = Stage.STEALREPLACE;
					indexPlayerStealing = id;
					listVolListener.forEach(v -> v.volButton.setEnabled(false));
					listVolListener.forEach(v -> v.steal = false);
					// deactivate the player playing cards
					tools.setEnabled(listPlayers.get(indexPlayerPlaying), false);
					tools.setEnabled(listPlayers.get(game.getNeighborIndex(indexPlayerPlaying)), false);

					// activate the player stealing cards
					tools.setEnabled(listPlayers.get(indexPlayerStealing), true);
					tools.setPartialDisable(listPlayers.get(game.getNeighborIndex(indexPlayerStealing)),game.getNeighborIndex(indexPlayerStealing));

					// deactivate end button
					endButton.setEnabled(false);
					endButton.setText("End turn");

				default:
					break;
			}
		}
	}

	// GUI 
	private void createScreen(){
		getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.black);
		this.setSize(WindowSize[0], WindowSize[1]);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.container = this.getContentPane();
	}

	private void pile(){
		int xPile = CARD_GAP*5 + CARD_SIZE*5 + PLAYER_GAP;
		int yPile = CARD_GAP*4 + CARD_SIZE*4;
		pilePanel = new JPanel(new GridLayout(1, 2,CARD_GAP, 0));
		pilePanel.setBounds(xPile, yPile, CARD_SIZE*2 + CARD_GAP, CARD_SIZE);


		JButton drawPile = new JButton(new ImageIcon("ressources\\-2.jpg"));
		drawPile.addActionListener(new PileListener());

		JButton discardPile = new JButton();
		discardPile.addActionListener(new PileListener());


		pilePanel.add(drawPile);
		pilePanel.add(discardPile);
		container.add(pilePanel);
	}

	private List<JPanel> drawPlayerButtons(int nbPlayers){
		List<JPanel> playerPanels = new ArrayList<>();
		// coord of panels
		int xPlayerPanel = CARD_GAP;
		int yPlayerPanel = CARD_GAP;
		
		// width and height of the panels
		final int WIDTH_PLAYER = CARD_SIZE*4 + CARD_GAP*3;
		final int HEIGHT_PLAYER = CARD_SIZE*3 + CARD_GAP*2;

		//create panel for each player
		for (int playerN = 0; playerN < nbPlayers; playerN++) {
			// create card panel
			playerPanels.add(new JPanel(new GridLayout(3, 4, CARD_GAP, CARD_GAP)));

			// shift new coords for next player panel
			yPlayerPanel += (playerN == 3) ? HEIGHT_PLAYER*2 : 0;
			if (playerN < 3){ // draw right to left
				playerPanels.get(playerN).setBounds(xPlayerPanel, yPlayerPanel, WIDTH_PLAYER, HEIGHT_PLAYER);
				xPlayerPanel += (WIDTH_PLAYER + PLAYER_GAP);
			}else{ // draw left to right
				xPlayerPanel -= (WIDTH_PLAYER + PLAYER_GAP);
				playerPanels.get(playerN).setBounds(xPlayerPanel, yPlayerPanel, WIDTH_PLAYER, HEIGHT_PLAYER);
			}			

			// add cards to player panel
			for (int cardN = 0; cardN < 12; ++cardN)
			{
				JButton Button = new JButton(new ImageIcon("ressources\\-2.jpg"));
				Button.addActionListener(new CardListener());
				playerPanels.get(playerN).add(Button);
				container.add(playerPanels.get(playerN));
			}

			// deactivate all cards for 1st turn
			tools.setEnabled(playerPanels.get(playerN), false);
			drawVolUI(nbPlayers);
		}

		return playerPanels;
	}
	public void drawVolUI(int nbPlayers){

		final int WIDTH_UI = CARD_SIZE * 3 + CARD_GAP*2;
		final int HEIGHT_UI = CARD_SIZE;
		final int HGAP = WIDTH_UI + CARD_GAP + CARD_SIZE + PLAYER_GAP;
		final int VGAP = HEIGHT_UI + CARD_SIZE + CARD_GAP*2;

		int xUI = CARD_GAP;
		int yUI = CARD_GAP*3 + CARD_SIZE*3;
		for (int playerN = 0; playerN < nbPlayers; playerN++) {

			JPanel UIpanel = new JPanel(new GridLayout(1, 3, CARD_GAP, 0));
			

			UIpanel.setBounds(xUI, yUI, WIDTH_UI, HEIGHT_UI);
			JLabel name = new JLabel("player"+(playerN+1));
			JLabel pion = new JLabel(new ImageIcon("ressources\\pion.png"));

			JButton vol = new JButton("Vol");
			vol.setEnabled(false);
			VolListener volListener = new VolListener(playerN, vol);
			listVolListener.add(volListener);
			vol.addActionListener(volListener);
			
			// shift when changing from top to bottom
			xUI += (playerN == 3) ? CARD_GAP + CARD_SIZE : 0;
			yUI += (playerN == 3) ? VGAP: 0;
			if (playerN < 3){ // top grid UI is on right side
				UIpanel.setBounds(xUI , yUI, WIDTH_UI, HEIGHT_UI);
				xUI += HGAP;

				UIpanel.add(pion);
				UIpanel.add(name);
				UIpanel.add(vol);
			}else{ // bottom grid UI is on the left side
				
				xUI -= HGAP;
				UIpanel.setBounds(xUI , yUI, WIDTH_UI, HEIGHT_UI);
				
				UIpanel.add(vol);
				UIpanel.add(name);
				UIpanel.add(pion);
			}
			container.add(UIpanel);
		}
	}

	public void drawEnd(){
		int xEnd = CARD_GAP*4 + CARD_SIZE*3 + PLAYER_GAP;
		int yEnd = CARD_GAP*5 + CARD_SIZE * 4;
		endButton = new JButton("END turn");
		endButton.setBounds(xEnd, yEnd, CARD_SIZE*2, CARD_SIZE/2);
		endButton.setEnabled(false);
		endButton.addActionListener(new EndListener());
		container.add(endButton);
	}
}