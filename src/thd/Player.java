package thd;

public class Player
{
	private String name;
	private int score;

	private Deck playedCards = new Deck();

	public Player(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public void initPlayedCards()
	{
		playedCards = new Deck();
	}

	public void playCard(Card card)
	{
		playedCards.addCardToDeck(card);
	}
	
	public Deck getPlayedCards()
	{
		return playedCards;
	}

	@Override
	public String toString()
	{
		return name + ": " + playedCards;
	}
}
