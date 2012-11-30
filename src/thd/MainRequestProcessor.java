package thd;

import java.util.StringTokenizer;

import thd.Card.CardType;

public class MainRequestProcessor {

	private Deck hand;
	private Deck playedCards;
	private Regions regions;
	private String actualRegion;
	private String actualProtectedRegion;
	
	public MainRequestProcessor() {
		hand = new Deck();
		playedCards = new Deck();
		regions = new Regions();
	}
	
	public String processRequest(String requestString) {
		StringTokenizer tokenizer = new StringTokenizer(requestString);
		
		String command = tokenizer.nextToken();
		
		if("Hand".equals(command))
		{
			hand = new Deck();
			tokenizer.nextToken();
			String card = tokenizer.nextToken();
			while(!"]".equals(card))
			{
				hand.addCardToDeck(new Card(card));
				card = tokenizer.nextToken();
			}
		}
		else if("?Condottiere".equals(command))
		{
			return regions.getHighestRegion().getRegionName();
		}
		else if("?Move".equals(command))
		{
			printHandAndDeck();
			if(!hand.isEmpty())
			{
				Card playedCard;
				if(playScareCrow())
				{
					
				}
				playedCard = hand.getHighestCard();
				hand.removeCard(playedCard);
				playedCards.addCardToDeck(playedCard);
				return playedCard.toString();
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
			String zone = tokenizer.nextToken();
			if(!"nothing".equals(zone))
			{
				regions.occupyRegion(zone);
				if(actualProtectedRegion != null)
				{
					regions.liberateRegion(actualProtectedRegion);
				}
				actualProtectedRegion = zone;
			}
		}
		else if("?Retrieve".equals(command))
		{
			printHandAndDeck();
			if(!playedCards.isEmpty())
			{
				// retrieve a played card
				Card retrievedCard = playedCards.getHighestCard();
				playedCards.removeCard(retrievedCard);
				hand.addCardToDeck(retrievedCard);
				return retrievedCard.toString();
			}
			else return "nothing";
		}
		else if("Players".equals(command))
		{
			
		}
		else if("Play".equals(command))
		{
			Card playedCard = new Card(tokenizer.nextToken());
		}
		else if("CurrentZone".equals(command))
		{
			actualRegion = tokenizer.nextToken();
		}
		else if("BattleStart".equals(command))
		{
			playedCards = new Deck();
		}
		else if("BattleEnd".equals(command))
		{
			if(!"tie".equals(tokenizer.nextToken()))
			{
				regions.occupyRegion(actualRegion);
			}
		}
		return null;
	}

	private void printHandAndDeck() 
	{
		System.out.println("Hand: " + hand.toString());
		System.out.println("Table: " + playedCards.toString());
	}

	private boolean playScareCrow()
	{
		return false;
	}
}
