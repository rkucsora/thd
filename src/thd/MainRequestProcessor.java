package thd;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import thd.Card.CardType;

public class MainRequestProcessor
{

	private Deck hand;
	private Regions regions;
	private String actualRegion;
	private String actualProtectedRegion;
	private Player actualPlayer;
	private Players players;
	private boolean springOnField;
	private boolean winterOnField;

	public MainRequestProcessor()
	{
		hand = new Deck();
		regions = new Regions();
	}

	public String processRequest(String requestString)
	{
		StringTokenizer tokenizer = new StringTokenizer(requestString);

		String command = tokenizer.nextToken();

		if ("Hand".equals(command))
		{
			hand = new Deck();
			tokenizer.nextToken();
			String card = tokenizer.nextToken();
			while (!"]".equals(card))
			{
				hand.addCardToDeck(new Card(card));
				card = tokenizer.nextToken();
			}
		} else if ("?Condottiere".equals(command))
		{
			return regions.getHighestRegion().getRegionName();
		} else if ("?Move".equals(command))
		{
			printHandAndDeck();
			if (!hand.isEmpty())
			{
				Card playedCard = null;
				Card keyCard = new Card("Key");
				if (hand.size() == 1 && hand.containsCard(keyCard))
				{
					return "drop";
				}
				if (playKey())
				{
					System.out.println("More, winrar!");
					return keyCard.toString();
				}
				@SuppressWarnings("serial")
				Map<String, Integer> gainMap = new HashMap<String, Integer>() {{
				put("Drummer", gainFromDrummer());
				put("Heroine", gainFromHeroine());
				put(Integer.toString(gainFromHighestMerc()), gainFromHighestMerc());
				put("Spring", gainFromSpring());
				put("Winter", gainFromWinter());
				}};
				int topValue = Integer.MIN_VALUE;
				for(Entry<String, Integer> entry : gainMap.entrySet())
				{
					if(entry.getValue() > topValue)
					{
						topValue = entry.getValue();
						playedCard = new Card(entry.getKey());
					}
				}
				
				if (playScareCrow())
				{

				}
				if (playedCard == null)
				{
					playedCard = hand.getHighestCard();
					if(playedCard != null)
					{
						hand.removeCard(playedCard);
						return playedCard.toString();
					}
					else
					{
						return "pass";
					}
				} 
				else
				{
					hand.removeCard(playedCard);
					return playedCard.toString();
				}
			} else
				return "pass";
		} else if ("?Bishop".equals(command))
		{
			return "pass";
		} else if ("Protect".equals(command))
		{
			String zone = tokenizer.nextToken();
			if (!"nothing".equals(zone))
			{
				regions.occupyRegion(zone);
				if (actualProtectedRegion != null)
				{
					regions.liberateRegion(actualProtectedRegion);
				}
				actualProtectedRegion = zone;
			}
		} else if ("?Retrieve".equals(command))
		{
			printHandAndDeck();
			Deck playedCards = getMyPlayer().getPlayedCards();
			if (!playedCards.isEmpty())
			{
				// retrieve a played card
				Card retrievedCard = playedCards.getHighestMercenary();
				if (retrievedCard == null)
				{
					return "pass";
				}
				playedCards.removeCard(retrievedCard);
				hand.addCardToDeck(retrievedCard);
				return retrievedCard.toString();
			} else
				return "pass";
		} else if ("Retrieve".equals(command))
		{
			String card = tokenizer.nextToken();
			if (!"nothing".equals(card) && !"(null)".equals(card))
			{
				actualPlayer.getPlayedCards().removeCard(card);
			}
		} else if ("Players".equals(command))
		{
			players = new Players();
			tokenizer.nextToken();
			String player = tokenizer.nextToken();
			while (!"]".equals(player))
			{
				players.addPlayer(new Player(player));
				player = tokenizer.nextToken();
			}
		} else if ("Player".equals(command))
		{
			String playerName = tokenizer.nextToken();
			actualPlayer = players.getPlayer(playerName);
		} else if ("Play".equals(command))
		{
			Card playedCard = new Card(tokenizer.nextToken());
			actualPlayer.playCard(playedCard);
			if (playedCard.getType() == CardType.Bishop)
			{
				Card highestMercenary = getHighestMercenary();
				if (highestMercenary != null)
				{
					players.removeCardFromAllPlayers(highestMercenary);
				}
			}
			if (playedCard.getType() == CardType.Winter)
			{
				winterOnField = true;
				springOnField = false;
			}
			if (playedCard.getType() == CardType.Spring)
			{
				springOnField = true;
				winterOnField = false;
			}
		} else if ("CurrentZone".equals(command))
		{
			actualRegion = tokenizer.nextToken();
		} else if ("BattleStart".equals(command))
		{
			players.initPlayedCards();
		} else if ("BattleEnd".equals(command))
		{
			String winrar = tokenizer.nextToken();
			if (!"(tie)".equals(winrar) && !"tie".equals(winrar))
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
		System.out.println("Table: "
				+ getMyPlayer().getPlayedCards().toString());
	}

	private boolean playScareCrow()
	{
		return false;
	}

	private int getDelta(boolean spring, boolean winter)
	{
		int myScore = getMyPlayer().getPlayedCards().getAllCardValues(
				springOnField, winterOnField, getHighestMercenary(), false);
		int topScore = 0;
		for (Player player : players.getPlayers())
		{
			if (!getMyPlayer().equals(player))
			{
				int playerScore = player.getPlayedCards().getAllCardValues(
						springOnField, winterOnField, getHighestMercenary(), false);
				if (playerScore > topScore)
				{
					topScore = playerScore;
				}
			}
		}
		return myScore - topScore;
	}

	private int gainFromWinter()
	{
		if (hand.containsCard(new Card("Winter")))
		{
			int actualDelta = getDelta(springOnField, winterOnField);
			int deltaAfterWinter = getDelta(springOnField, true);
			return deltaAfterWinter - actualDelta;
		}
		return Integer.MIN_VALUE;
	}

	private int gainFromSpring()
	{
		if (hand.containsCard(new Card("Spring")))
		{
			int actualDelta = getDelta(springOnField, winterOnField);
			int deltaAfterSpring = getDelta(true, winterOnField);
			return deltaAfterSpring - actualDelta;
		}
		return Integer.MIN_VALUE;
	}

	private int gainFromHighestMerc()
	{
		Card highestMerc = hand.getHighestMercenary();
		if(highestMerc == null)
		{
			return Integer.MIN_VALUE;
		}
		else if(winterOnField)
		{
			return 1;
		}
		else
		{
			int highestMercValue = highestMerc.getValue();
			if(springOnField)
			{
				if(getHighestMercenary() == null || getHighestMercenary().getValue() <= highestMercValue)
				{
					return highestMercValue + 3;
				}
			}
			return highestMercValue;
		}
	}

	private Card getHighestMercenary()
	{
		return players.getHighestMercenary();
	}

	private boolean playKey()
	{
		if (hand.containsCard(new Card("Key")))
		{
			int myScore = getMyPlayer().getPlayedCards().getAllCardValues(
					springOnField, winterOnField, getHighestMercenary(), false);
			boolean isWinning = true;
			for (Player player : players.getPlayers())
			{
				if (player.getPlayedCards().getAllCardValues(springOnField,
						winterOnField, getHighestMercenary(), false) + 1 > myScore)
				{
					isWinning = false;
				}
			}
			return isWinning;
		}
		return false;
	}
	
	private int gainFromHeroine()
	{
		if(hand.containsCard(new Card("Heroine")))
		{
			return 10;
		}
		return Integer.MIN_VALUE;
	}
	
	private int gainFromDrummer()
	{
		if(hand.containsCard(new Card("Drummer")))
		{
			int currentValue = getMyPlayer().getPlayedCards().getAllCardValues(springOnField, winterOnField, getHighestMercenary(), false);
			int valueWithDrummer = getMyPlayer().getPlayedCards().getAllCardValues(springOnField, winterOnField, getHighestMercenary(), true);
			return valueWithDrummer - currentValue;
		}
		return Integer.MIN_VALUE;
	}
	
	private int gainFromBishop()
	{
		return 0;
	}
	
}
