package allegra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile extends Pile{

	public DrawPile(){
		fillDeck();
		Collections.shuffle(this.cards);
	}
	
	private void fillDeck()
	{
		for (int i=0; i<8; i++)
			for (int j=-1; j<4; j++)
				cards.add(new Card(j));
		
		for (int i=0; i<11; i++)
			for (int j=4; j<8; j++)
				cards.add(new Card(j));
		
		for (int i=0; i<8; i++)
			for (int j=8; j<12; j++)
				cards.add(new Card(j));	
	}

	/**
	 * returns 12 cards per player + 1 card for the discard
	 * @param numPlayers
	 * @return
	 */
	public List<Card> dealCards(int numPlayers){
		// create a copy of sublist
		List<Card> cardsDealt = new ArrayList<>(this.cards.subList(0, numPlayers * 12 + 1));

		//remove dealt cards
		this.cards.subList(0, numPlayers*12 + 1).clear();

		return cardsDealt;
	}
}
