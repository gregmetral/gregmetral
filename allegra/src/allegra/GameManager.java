package allegra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager {
	private List<PlayerMatrix> listPlayers = new ArrayList<>();
	private DrawPile drawPile = new DrawPile();
	private DiscardPile discardPile = new DiscardPile();
	private PlayerMatrix playerPlaying;
	private int indexPlayerPlaying = 0;



	/**
	 * deal cards to PlayerMatrix, Discard
	 * @param num_players
	 */
	public GameManager(int numPlayers)
	{
		List<Card> dealtCards = drawPile.dealCards(numPlayers);
		int columns = dealtCards.size()/3;
		// allow references in player matrixes
		List<List<Card>> columnsList = new ArrayList<>();

		for (int i = 0; i < columns; i++) {
			columnsList.add(new ArrayList<>(dealtCards.subList(i*3, i*3+3)));
		}
		for (int playerN = 0; playerN < numPlayers - 1; playerN++) {
			listPlayers.add(new PlayerMatrix(new ArrayList<>(columnsList.subList(playerN*4, playerN*4+5))));
		}

		// create last list composed of last 4 columns + 1 column
		List<List<Card>> lastMatrix = new ArrayList<>(columnsList.subList((numPlayers-1)*4, numPlayers*4));
		lastMatrix.add(columnsList.get(0));
		listPlayers.add(new PlayerMatrix(lastMatrix));


		//deal card to Discard
		this.discardPile.discardCard(dealtCards.remove(0));

		
		this.playerPlaying = this.listPlayers.get(indexPlayerPlaying);
	}

		// TEMP method for debug 
		public void showAllMatrix(){ 
 
			for (int i = 0; i < listPlayers.size(); i++) { 
				System.out.println("matrix :" + i); 
			} 
		} 
		public void showMatrix(int playerN){
			System.out.println(playerN+" index player");

		} 


	public int[][] checkAlligned(int indexPlayer){
		return listPlayers.get(indexPlayer).removeAligned();
	}

	/**
	 * changes internal playerPlaying to the next player who has not played last turn
	 * if a player has played last turn the next players will have last turn activated
	 * @return index of next player
	 */
	public int nextPlayer(){
		boolean lastTurn = listPlayers.stream().anyMatch(p -> p.lastTurnPLayed);
		do {
			// get next player
			indexPlayerPlaying = getNeighborIndex(indexPlayerPlaying);
			playerPlaying = listPlayers.get(indexPlayerPlaying);
		} while (playerPlaying.checkAllVisible()); // if he has played his last turn skip
		listPlayers.get(indexPlayerPlaying).lastTurnPLayed = lastTurn; // if a player has played his last turn activate his
		return indexPlayerPlaying;
	}

	public int flipCard(int x, int y){
		return playerPlaying.flipCard(x, y);
	}

	public boolean isFlipped(int x, int y){
		return playerPlaying.isFlipped(x, y);
	}

	public int getNeighborIndex(int Indexplayer){
		return (Indexplayer+1)%listPlayers.size();
	}
	
	/**
	 * remove card from chosen drawPile and return the card
	 * @param deckType
	 * @return the top card of the deck
	 */
	public Card pickCard(int deckType) {
		// returns card that is sent to discard
		return switch (deckType) {
			case 0 -> drawPile.pickCard();
			case 1 -> discardPile.pickCard();
			default -> null;
		};
	}

		/**
	 * replace card in the player matrix at the coords and return value of card sent to discard
	 * @param x & y
	 * @param card
	 */
	public Card replaceCard(int x, int y, Card card, int indexPlayer){
		Card removedCard = listPlayers.get(indexPlayer).replaceCard(x, y, card);
		discardCard(removedCard);
		return removedCard;
	}


	/**
	 * the card in param is sent to discard
	 * @param card
	 */
	public void discardCard(Card card){discardPile.discardCard(card);}

	public int showTopDiscardPile(){return discardPile.showTopCard();}

	/**
	 * card2 from stealcoord is replaced with card1 from coord and vice versa
	 * old: card1 in coord and card2 in coord2
	 * new: card2 in coord and card1 in coord2
	 * @param coord
	 * @param player
	 * @param stealCoord
	 */
	public Card[] stealCard(int[] coord,int Indexplayer, int[] stealCoord){
		PlayerMatrix playerStealing = listPlayers.get(Indexplayer);
		Card cardFrom = playerStealing.replaceCard(stealCoord[0], stealCoord[1], null);
		Card cardTo = playerPlaying.replaceCard(coord[0], coord[1], cardFrom);
		playerStealing.replaceCard(stealCoord[0], stealCoord[1], cardTo);

		return new Card[] {cardFrom, cardTo};
	}

	public boolean checkAllVisible(){
		// check it has not alreay been verified
		if (playerPlaying.lastTurnPLayed) {
			return true;
		}
		// if not verify it
		return playerPlaying.checkAllVisible();

	}
	
	
	public HashMap<Integer, Integer> getHashMap() {
		HashMap<Integer, Integer> playersHashMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < listPlayers.size(); i++) {
			playersHashMap.put(i, listPlayers.get(i).getScore());
		}
		
		return playersHashMap;
	}

	/**
	 * iterate through all PlayerMatrix to check if all players have played last turn
	 * @return bool
	 */
	public boolean checkEndGame(){
		for (PlayerMatrix player : listPlayers) {
			if (!player.lastTurnPLayed) {
				return false;
			}
		}
		return true;
	}
}