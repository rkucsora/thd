package thd;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private List<Card> m_cards;
	
	public Deck() {
		m_cards = new ArrayList<Card>();
	}
	
	public void addCardToDeck(Card card)
	{
		m_cards.add(card);
	}
	
	public Card getHighestCard()
	{
		Card highestCard = m_cards.get(0);
		for(Card card : m_cards)
		{
			 if(card.getValue() > highestCard.getValue())
			 {
					highestCard = card;
			 }
		}
		return highestCard;
	}
	
	public void removeCard(Card card)
	{
		m_cards.remove(card);
	}
	
	public boolean isEmpty()
	{
		return m_cards.isEmpty();
	}
}
