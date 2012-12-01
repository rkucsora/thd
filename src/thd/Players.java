package thd;

import java.util.ArrayList;
import java.util.List;

public class Players 
{
	private List<Player> players = new ArrayList<Player>(); 
	
	/**
	 * this method fills the players arraylist
	 * @param player the player objects
	 */
	public void addPlayer(Player... player)
	{
		for(Player actualPlayer : player)
		{
			players.add(actualPlayer);
		}
	}
	
	/**
	 * this method returns with a player
	 * @param playerName
	 * @return
	 */
	public Player getPlayer(String playerName)
	{
		
		for(Player actualPlayer : players)
		{
			if(playerName.equals(actualPlayer.getName()))
			{
				return actualPlayer;
			}
		}
		return null;
	}
	
	public void initPlayedCards()
	{
		for(Player player : players)
		{
			player.initPlayedCards();
		}
	}
	
	public Card getHighestMercenary()
	{
		Card highestCard = null;
		for(Player player : players)
		{
			Card potentialHighestCard = player.getPlayedCards().getHighestMercenary();
			if(potentialHighestCard != null && (highestCard == null || potentialHighestCard.getValue() > highestCard.getValue()))
			{
				highestCard = potentialHighestCard;
			}
		}
		return highestCard;
	}
	
	public Card getSecondHighestMercenary(Card highestMercenary)
	{
		Card secondHighestCard = null;
		Deck clonedDeck;
		for(Player player : players)
		{
			clonedDeck = new Deck(player.getPlayedCards());
			clonedDeck.removeAllOccurence(highestMercenary);
			Card potentialHighestCard = clonedDeck.getHighestMercenary();
			if(potentialHighestCard != null && (secondHighestCard == null || potentialHighestCard.getValue() > secondHighestCard.getValue()))
			{
				secondHighestCard = potentialHighestCard;
			}
		}
		return secondHighestCard; 
	}
	
	public List<Player> getPlayers()
	{
		return players;
	}

	public void removeCardFromAllPlayers(Card card)
	{
		for(Player player : players)
		{
			player.getPlayedCards().removeAllOccurence(card);
		}
	}

}
