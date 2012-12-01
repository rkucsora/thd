package thd;

public class Card 
{
	public enum CardType { Courtesan, Heroine, Winter, Spring, Bishop, Drummer, Scarecrow, Key, Mercenary };
	
	private CardType type;
	private int value;
	
	public Card(String s)
	{
		for (CardType cardType : CardType.values())
		{	
			if(s.equals(cardType.name()))
			{
				type = cardType;
				if(cardType == CardType.Courtesan)
				{
					value = 1;
				}
				else if(cardType == CardType.Heroine)
				{
					value = 10;
				}
				else
				{
					value = 0;
				}
				return;
			}
		}
		type = CardType.Mercenary;
		value = Integer.parseInt(s);
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		if(type == CardType.Mercenary)
		{
			return Integer.toString(value);
		}
		return type.name();
	}
	@Override
	public boolean equals(Object obj)
	{
		if(obj != null && obj instanceof Card)
		{
			Card card = (Card)obj;
			if(card.getType() == type && card.getValue() == value)
			{
				return true;
			}
		}
		return false;
	}
}
