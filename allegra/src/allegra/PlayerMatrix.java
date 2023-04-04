package allegra;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatrix {

	private List<List<Card>> matrix = new ArrayList<>();
	public boolean lastTurnPLayed = false;

	public PlayerMatrix(List<List<Card>> dealedCards){
		// add each column to list
		for (List<Card> list : dealedCards) {
			this.matrix.add(list); //list of 3 cards
		}	
	}

	/**
	 * returns the value of the card and sets it visible
	 */
	protected int flipCard(int x, int y){
		return matrix.get(x).get(y).flipCard();
	}

	protected boolean isFlipped(int x, int y){
		return matrix.get(x).get(y).getVisible();
	}
	
	/**
	 * replace card A with B
	 * @param x
	 * @param y
	 * @param card
	 * @return
	 */
	protected Card replaceCard(int x, int y, Card card)
	{
		// returns value that was swapped
		if (card != null) {
			card.flipCard();	
		}
		return this.matrix.get(x).set(y, card);
	}
	
	/**
	 * check if 3 cards have identical value in 1 column
	 * @param x coord of the column to check
	 * @return bool 
	 */
	private boolean checkAllignedVertical(int x)
	{	
		// contains only 3 cards per column so if missing card then no allignment
		if (this.matrix.get(x).contains(null)) {
			return false;
		}

		// check if card is visible and same value
		int firstCard = this.matrix.get(x).get(0).getValue();
		for (Card card : this.matrix.get(x)) {
			if (!card.getVisible() || card.getValue() != firstCard){
				
				return false;
			}
		}

		return true;
	}
	
	/**
	 * check if 3 cards alligned
	 * @param y line to check
	 * @return 3 coords of x axis
	 */
	private List<Integer> checkAllignedHorizontal(int y)
	{
		// create list that will contain the index of the identical values of cards
		List<Integer> counterIndex = new ArrayList<>();

		// the card value to check with the other neighbor cards set to impossible value
		int checkCard = -2;
		for (int i = 0; i < this.matrix.size(); i++) {
			// if same value and visible
			if (matrix.get(i).get(y) == null) {
				//skip empty card
				;
			}else if (matrix.get(i).get(y).getVisible() && matrix.get(i).get(y).getValue() == checkCard) {
				// add index to the list
				counterIndex.add(i);

				// if 3 indexes (3 alligned cards)
				if (counterIndex.size() == 3) {
					//convert to int[] and return
					return counterIndex;
			}
			}else{
				// reset counterIndex
				counterIndex.clear();
				if (matrix.get(i).get(y).getVisible()){
					// reset with new card to compare
					checkCard = matrix.get(i).get(y).getValue();
					counterIndex.add(i);
				}else{
					// set to impossible value so checkCard gets reset next iteration
					checkCard = -2;
				}
			}
		}

		// if no cards where found return null
		return null;
	}
	
	/**
	 * remove alligned cards if any found, first vertical then horizontal (arbitrary choice)
	 *@param Card[] cards to be sent to discard
	 */
	protected int[][] removeAligned()
	{
		int[][] coordsRemove = new int[3][2];
		// vertical removal
		for (int x = 0; x < 5; x++) {
			// returns bool
			if (this.checkAllignedVertical(x)){

				// remove cards from the matrix
				for (int y = 0; y < 3; y++) {
					matrix.get(x).set(y, null);
					coordsRemove[y] = new int[] {x, y};
				}
				return coordsRemove;
			}
			// nothing found so check for next column
		}

		// if no verticial removal then do horizontal
		for (int y = 0; y < 3; y++) {
			// returns x coords of cards to remove
			List<Integer> coordCardsToDiscard = this.checkAllignedHorizontal(y);
			List<Card> cardsToDiscard = new ArrayList<>();

			// check if cards to discard are found
			if (coordCardsToDiscard != null) {
				for (int i = 0; i < coordCardsToDiscard.size(); i++) {
					int x = coordCardsToDiscard.get(i);
					cardsToDiscard.add(matrix.get(x).get(y));
					this.matrix.get(x).set(y, null);

					coordsRemove[i] = new int[] {x, y};
				}
				return coordsRemove;
			}
			// nothing found so check for next line
		}

		return null;
	}
	
	/**
	 * return the total score by adding value of every card
	 * @return
	 */
	public int getScore() {
        
	    int score = 0;
	    for (int i = 0; i < this.matrix.size(); i++) {
	        for (int j = 0; j < this.matrix.get(i).size(); j++) {
	            if(this.matrix.get(i).get(j) != null) {
	                score += this.matrix.get(i).get(j).getValue();
	            }
	        }
	    }
	    return score;
	}
	
	/**
	 * checks if all cards are visible
	 * @return
	 */
	protected boolean checkAllVisible(){

		for (List<Card> column : matrix) {
			for (Card card : column) {
				if (card != null && !card.getVisible())
				{
					lastTurnPLayed = false;
					return false;
				}
			}
		}
		lastTurnPLayed = true;
		return true;
	}
}
