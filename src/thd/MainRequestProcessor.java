package thd;

import java.util.StringTokenizer;

import thd.Card.CardType;

public class MainRequestProcessor {

	private Deck hand;
	private Regions regions;
	private String actualRegion;
	private String actualProtectedRegion;
	private Player actualPlayer;
	private Players players;
	
	public MainRequestProcessor() {
		hand = new Deck();
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
				return playedCard.toString();
			}
			else return "pass";
		}
		else if("?Bishop".equals(command))
		{
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
			Deck playedCards = getMyPlayer().getPlayedCards();
			if(!playedCards.isEmpty())
			{
				// retrieve a played card
				Card retrievedCard = playedCards.getHighestMercenary();
				if(retrievedCard == null)
				{	
					return "pass";
				}
				playedCards.removeCard(retrievedCard);
				hand.addCardToDeck(retrievedCard);
				return retrievedCard.toString();
			}
			else return "pass";
		}
		else if("Retrieve".equals(command))
		{
			String card = tokenizer.nextToken();
			if(!"nothing".equals(card) && !"null".equals(card))
			{
				actualPlayer.getPlayedCards().removeCard(card);
			}
		}
		else if("Players".equals(command))
		{
			players = new Players();
			tokenizer.nextToken();
			String player = tokenizer.nextToken();
			while(!"]".equals(player))
			{
				players.addPlayer(new Player(player));
				player = tokenizer.nextToken();
			}
		}
		else if("Player".equals(command))
		{
			String playerName = tokenizer.nextToken();
			actualPlayer = players.getPlayer(playerName);
		}
		else if("Play".equals(command))
		{
			Card playedCard = new Card(tokenizer.nextToken());
			actualPlayer.playCard(playedCard);
			if(playedCard.getType() == CardType.Bishop)
			{
				Card highestMercenary = players.getHighestMercenary();
				players.removeCardFromAllPlayers(highestMercenary);
			}
		}
		else if("CurrentZone".equals(command))
		{
			actualRegion = tokenizer.nextToken();
		}
		else if("BattleStart".equals(command))
		{
			players.initPlayedCards();
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

	private Player getMyPlayer()
	{
		return players.getPlayer("TWO_AND_A_HALF_DEV");
	}

	private void printHandAndDeck() 
	{
		System.out.println("Hand: " + hand.toString());
		System.out.println("Table: " + getMyPlayer().getPlayedCards().toString());
	}

	private boolean playScareCrow()
	{
		return false;
	}
}
