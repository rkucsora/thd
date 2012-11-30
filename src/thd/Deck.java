package thd;

import java.util.ArrayList;
import java.util.List;

import thd.Card.CardType;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
	}
	
	public void addCardToDeck(Card card)
	{
		cards.add(card);
	}
	
	public Card getHighestCard()
	{
		Card highestCard = cards.get(0);
		for(Card card : cards)
		{
			 if(card.getValue() > highestCard.getValue())
			 {
					highestCard = card;
			 }
		}
		return highestCard;
	}
	
	public Card getHighestMercenary()
	{
		Card highestCard = null;
		for(Card card : cards)
		{
			 if(card.getType() == CardType.Mercenary && (highestCard == null || card.getValue() > highestCard.getValue()))
			 {
					highestCard = card;
			 }
		}
		return highestCard;
	}
	
	public void removeCard(Card card)
	{
		cards.remove(card);
	}
	
	public boolean isEmpty()
	{
		return cards.isEmpty();
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(Card card : cards)
		{
			builder.append(card);
		}
		return builder.toString();
	}
}
