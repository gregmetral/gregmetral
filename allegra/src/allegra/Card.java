package allegra;

public class Card {
	private boolean visible;
	private int value;
	
	/**
	 * @brief This method allows us to flip the card
	 */
	public int flipCard()
	{
		this.visible = true;
		return this.getValue();
	}
	
	/**
	 * @brief This method allows us to get the value of the card
	 * @return The value of the card
	 */
	public int getValue()
	{
		return this.value;
	}

	/**
	 * @brief This method allows us to check if the card is visible or not
	 * @return If the card is flipped or not
	 */
	public boolean getVisible()
	{
		return this.visible;
	}
	
	public Card(int setValue)
	{
		this.value = setValue;
		this.visible = false;
	}
}
