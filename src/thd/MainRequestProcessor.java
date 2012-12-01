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
	private int moveCounter;

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
			float averageValue = 1.0f * hand.getAllCardValues(false, false, null, false) / hand.size();
			if( averageValue > 3.0f )
			{
				Region linkingRegion = regions.getLinkingRegion();
				if (linkingRegion == null)
				{
					return regions.getHighestRegion().getRegionName();
				}
				else
				{
					return linkingRegion.getRegionName();
				}
			}
			else
			{
//				System.out.println("!!!! hudemessze");
				Region lonelyRegion = regions.getLonelyRegion();
				if (lonelyRegion != null)
				{
					return lonelyRegion.getRegionName();
				}
				else
				{
					return regions.getLowestRegion().getRegionName();
				}
			}
		} else if ("?Move".equals(command))
		{
			moveCounter++;
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
					return keyCard.toString();
				}
				if (hand.containsCard(new Card("Heroine")))
				{
					hand.removeCard(new Card("Heroine"));
					return "Heroine";
				}
				if (hand.containsCard(new Card("Courtesan")))
				{
					hand.removeCard(new Card("Courtesan"));
					return "Courtesan";
				}
				if (hand.containsCard(new Card("Drummer")))
				{
					hand.removeCard(new Card("Drummer"));
					return "Drummer";
				}
				@SuppressWarnings("serial")
				Map<String, Integer> gainMap = new HashMap<String, Integer>() {{
//					put("Drummer", gainFromDrummer());
//					put("Heroine", gainFromHeroine());
					put(hand.getLowestMercenary() == null ? "pass" : Integer.toString(hand.getLowestMercenary().getValue()), gainFromLowestMerc());
					put("Spring", gainFromSpring());
					put("Winter", gainFromWinter());
					put("Bishop", gainFromBishop());
				}};
				int topValue = Integer.MIN_VALUE;
				for(Entry<String, Integer> entry : gainMap.entrySet())
				{
					System.out.println("!!!! " + entry.getKey() + ": " + entry.getValue());
					if(entry.getValue() > topValue && entry.getValue() > 2)
					{
						topValue = entry.getValue();
						playedCard = new Card(entry.getKey());
					}
				}
				
				if (playedCard == null)
				{
					if(moveCounter > 2)
					{
						playedCard = hand.getHighestCard(winterOnField);
					}
					else
					{
						playedCard = hand.getLowestCardWithoutKeyAndBishop();
					}
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
			Region enemyRegion = regions.getEnemyLinkingRegion(players);
			if (enemyRegion == null)
			{
				return "pass";
			}
			else
			{
				return enemyRegion.getRegionName();
			}
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
//			printHandAndDeck();
//			Deck playedCards = getMyPlayer().getPlayedCards();
//			if (!playedCards.isEmpty())
//			{
//				// retrieve a played card
//				Card retrievedCard = playedCards.getHighestMercenary();
//				if (retrievedCard == null)
//				{
//					return "pass";
//				}
//				playedCards.removeCard(retrievedCard);
//				hand.addCardToDeck(retrievedCard);
//				return retrievedCard.toString();
//			} else
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
				for(Player player : players.getPlayers())
				{
					player.getPlayedCards().removeAllOccurence(new Card("Spring"));
				}
			}
			if (playedCard.getType() == CardType.Spring)
			{
				springOnField = true;
				winterOnField = false;
				for(Player player : players.getPlayers())
				{
					player.getPlayedCards().removeAllOccurence(new Card("Winter"));
				}
			}
		} else if ("CurrentZone".equals(command))
		{
			actualRegion = tokenizer.nextToken();
			regions.occupyRegion(actualRegion);
		} else if ("BattleStart".equals(command))
		{
			moveCounter = 0;
			players.initPlayedCards();
//			float averageValue = 1.0f * hand.getAllCardValues(false, false, null, false) / hand.size();
//			System.out.println("!!!! Current hand value:" + averageValue);
		} else if ("BattleEnd".equals(command))
		{
			String winrar = tokenizer.nextToken();
			System.out.println("!!!! winrar: " + winrar);
			if (!"(tie)".equals(winrar) && !"tie".equals(winrar))
			{
				regions.occupyRegionBy(actualRegion, players.getPlayer(winrar));
			}
			else
			{
				regions.liberateRegion(actualRegion);
			}
		}
		return null;
	}

	private Player getMyPlayer()
	{
		return players.getPlayer(Main.MY_NAME);
	}

	private void printHandAndDeck()
	{
		System.out.println("!!!! Hand: " + hand.toString());
		for(Player player : players.getPlayers())
		{
			System.out.println("!!!! " + player.getName() + ": "
					+ player.getPlayedCards().toString());
		}
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

	private int gainFromLowestMerc()
	{
		Card lowestMerc = hand.getLowestMercenary();
		if(lowestMerc == null)
		{
			return Integer.MIN_VALUE;
		}
		else if(winterOnField)
		{
			return 1;
		}
		else
		{
			int highestMercValue = lowestMerc.getValue();
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
	
	private Card getSecondHighestMercenary()
	{
		return players.getSecondHighestMercenary(getHighestMercenary());
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
	
//	private int gainFromHeroine()
//	{
//		if(hand.containsCard(new Card("Heroine")))
//		{
//			return 20;
//		}
//		return Integer.MIN_VALUE;
//	}
	
//	private int gainFromDrummer()
//	{
//		if(hand.containsCard(new Card("Drummer")))
//		{
//			int currentValue = getMyPlayer().getPlayedCards().getAllCardValues(springOnField, winterOnField, getHighestMercenary(), false);
//			int valueWithDrummer = getMyPlayer().getPlayedCards().getAllCardValues(springOnField, winterOnField, getHighestMercenary(), true);
//			return valueWithDrummer - currentValue;
//		}
//		return Integer.MIN_VALUE;
//	}
	
	private int gainFromBishop()
	{
		if(hand.containsCard(new Card("Bishop")))
		{
			int actualDelta = getDelta(springOnField, winterOnField);
			Deck myDeckWithBishop = new Deck(getMyPlayer().getPlayedCards());
			myDeckWithBishop.removeAllOccurence(getHighestMercenary());
			int myScore = myDeckWithBishop.getAllCardValues(
					springOnField, winterOnField, getSecondHighestMercenary(), false);
			int topScore = 0;
			Deck playerDeckWithBishop;
			for (Player player : players.getPlayers())
			{
				if (!getMyPlayer().equals(player))
				{
					playerDeckWithBishop = new Deck(player.getPlayedCards());
					playerDeckWithBishop.removeAllOccurence(getHighestMercenary());
					int playerScore = playerDeckWithBishop.getAllCardValues(
							springOnField, winterOnField, getSecondHighestMercenary(), false);
					if (playerScore > topScore)
					{
						topScore = playerScore;
					}
				}
			}
			int withBishopDelta = myScore - topScore;
			return 2*(withBishopDelta - actualDelta);
		}
		return Integer.MIN_VALUE;
	}
	
}
