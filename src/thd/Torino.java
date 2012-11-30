package thd;

public class Torino implements IRegion
{
	@Override
	public String getRegionName() {
		
		return "Torino";
	}

	@Override
	public int getValue() {
		
		return 2;
	}
	
	private boolean occupied;
	@Override
	public boolean isOccupied() {
		return occupied;
	}

	@Override
	public void setOccupied(boolean isOccupied) {
		occupied = isOccupied;
		
	}
}
