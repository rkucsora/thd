package thd;

public class Bologna implements IRegion
{

	@Override
	public String getRegionName() {
		return "Bologna";
	}

	@Override
	public int getValue() {
		return 4;
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
