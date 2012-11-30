package thd;

public interface IRegion 
{
	String getRegionName();
	int getValue();
	boolean isOccupied();
	void setOccupied(boolean isOccupied);
}
