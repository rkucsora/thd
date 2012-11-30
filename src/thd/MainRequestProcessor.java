package thd;

import java.util.StringTokenizer;

import thd.Card.CardType;

public class MainRequestProcessor {

	private Deck deck;
	private Deck playedCards;
	private Regions regions;
	
	public MainRequestProcessor() {
		deck = new Deck();
		playedCards = new Deck();
		regions = new Regions();
	}
	
	public String processRequest(String requestString) {
		StringTokenizer tokenizer = new StringTokenizer(requestString);
		
		String command = tokenizer.nextToken();
		
		if("Hand".equals(command))
		{
			tokenizer.nextToken();
			String card = tokenizer.nextToken();
			while(!"]".equals(card))
			{
				deck.addCardToDeck(new Card(card));
				card = tokenizer.nextToken();
			}
			return null;
		}
		else if("?Condottiere".equals(command))
		{
			return regions.getHighestRegion().getRegionName();
		}
		else if("?Move".equals(command))
		{
			if(!deck.isEmpty())
			{
				Card playedCard = deck.getHighestCard();
				deck.removeCard(playedCard);
				playedCards.addCardToDeck(playedCard);
				if(playedCard.getType() == CardType.Mercenary)
				{
					return Integer.toString(playedCard.getValue());
				}
				return playedCard.getType().name();
			}
			else return "pass";
		}
		else if("?Bishop".equals(command))
		{
			// place bishop to an unoccupied field
			return "pass";
		}
		else if("Protect".equals(command))
		{
			String protectedZone = tokenizer.nextToken();
			// mark the zone as protected
		}
		else if("?Retrieve".equals(command))
		{
			if(!playedCards.isEmpty())
			{
				// retrieve a played card
				Card retrievedCard = playedCards.getHighestCard();
				playedCards.removeCard(retrievedCard);
				deck.addCardToDeck(retrievedCard);
				if(retrievedCard.getType() == CardType.Mercenary)
				{
					return Integer.toString(retrievedCard.getValue());
				}
				return retrievedCard.getType().name();
			}
			else return "nothing";
		}
		else if("Players".equals(command))
		{
			
		}
		else if("Play".equals(command))
		{
			tokenizer.nextToken();
		}
		return null;
	}

}
