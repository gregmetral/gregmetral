package allegra;

import java.util.ArrayList;
import java.util.List;

public class Pile {
    protected List<Card> cards = new ArrayList<>();

    public Card pickCard()
	{		
		if (this.cards.isEmpty()) return null;
		
		//Top card is first card in list
		return this.cards.remove(0);
	}
}
