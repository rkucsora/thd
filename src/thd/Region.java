package thd;

public class Region 
{
	private String name;
	private Region[] adjacentRegions;
	private boolean isOccupied = false;
	private Player owner;
	
	public Region(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public void setAdjacentRegions(Region[] regions)
	{
		this.adjacentRegions = regions;
	}
	
	public Region[] getAdjacentRegions()
	{
		return adjacentRegions;
	}
	
	public String getRegionName()
	{
		return name;
	}
	
	public int getValue()
	{
		return adjacentRegions.length;
	}
	
	public boolean isOccupied()
	{
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied)
	{
		this.isOccupied = isOccupied;
	}
	
	public void occupyBy(Player player)
	{
		this.setOccupied(true);
		this.owner = player;
	}
	
	public Player getOwner()
	{
		return owner;
	}
}
