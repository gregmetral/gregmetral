package allegra;

public class DiscardPile extends Pile {
	/**
	 * @brief card sent to top of pile (top pile is index 0)
	 * @param c The card that the player discards
	 */
	public void discardCard(Card card)
	{
		// evoids having empty cards in list
		if (card != null){
			this.cards.add(0, card);
		}
	}
	
	/**
	 * @brief returns the value of the top card
	 * @return int
	 */
	public int showTopCard()
	{
		return this.cards.get(0).getValue();
	}
}
