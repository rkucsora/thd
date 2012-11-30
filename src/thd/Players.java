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

}
