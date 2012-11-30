package thd;

public class Lucca implements IRegion
{
	@Override
	public String getRegionName() {
		return "Lucca";
	}

	@Override
	public int getValue() {
		return 3;
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
