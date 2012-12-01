package thd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import thd.Card.CardType;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
	}
	
	public Deck(Deck d)
	{
		cards = new ArrayList<Card>(d.cards);
	}
	
	public void addCardToDeck(Card card)
	{
		cards.add(card);
	}
	
	public Card getHighestCard()
	{
		Card highestCard = getFirstNonKey();
		if(highestCard == null)
		{
			return null;
		}
		for(Card card : cards)
		{
			 if(card.getValue() > highestCard.getValue())
			 {
					highestCard = card;
			 }
		}
		return highestCard;
	}
	
	public Card getLowestCard()
	{
		Card lowestCard = getFirstNonKey();
		if(lowestCard == null)
		{
			return null;
		}
		for(Card card : cards)
		{
			if(card.getValue() < lowestCard.getValue())
			{
				lowestCard = card;
			}
		}
		return lowestCard;
	}
	
	private Card getFirstNonKey()
	{
		for(Card c: cards)
		{
			if(c.getType() != CardType.Key)
			{
				return c;
			}
		}
		return null;
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
	
	public Card getLowestMercenary()
	{
		Card lowestCard = null;
		for(Card card : cards)
		{
			if(card.getType() == CardType.Mercenary && (lowestCard == null || card.getValue() < lowestCard.getValue()))
			{
				lowestCard = card;
			}
		}
		return lowestCard;
	}
	
	public void removeCard(Card card)
	{
		cards.remove(card);
	}
	
	public void removeCard(String cardName)
	{
		Card card = new Card(cardName);
		Iterator<Card> iterator = cards.iterator();
		while(iterator.hasNext())
		{
			Card c = iterator.next();
			if (c.getType() == card.getType() && c.getValue() == card.getValue())
			{
				iterator.remove();
				break;
			}
		}
	}
	
	public void removeAllOccurence(Card card)
	{
		boolean anyMore;
		do
		{
			 anyMore = cards.remove(card);
		}
		while(anyMore);
	}
	
	public boolean isEmpty()
	{
		return cards.isEmpty();
	}
	
	public int size()
	{
		return cards.size();
	}
	
	public int getAllCardValues(boolean spring, boolean winter, Card strongestPlayedMercenary, boolean forceDrummer)
	{
		int value = 0;
		boolean drummerPresent = cards.contains(new Card("Drummer")) || forceDrummer;
		for(Card c : cards)
		{
			if(winter && c.getType() == CardType.Mercenary)
			{
				value++;
				if(drummerPresent)
				{
					value++;
				}
			}
			else if(spring && c.equals(strongestPlayedMercenary))
			{
				if(drummerPresent)
				{
					value += c.getValue();
				}
				value += c.getValue()+3;
			}
			else
			{
				if(c.getType() == CardType.Mercenary && drummerPresent)
				{
					value += c.getValue();
				}
				value += c.getValue();
			}
		}
		return value;
	}
	
	public boolean containsCard(Card card)
	{
		return cards.contains(card);
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(Card card : cards)
		{
			builder.append(card).append(' ');
		}
		return builder.toString();
	}
}
