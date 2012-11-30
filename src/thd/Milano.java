package thd;

public class Milano implements IRegion
{

	@Override
	public String getRegionName() {
		return "Milano";
	}

	@Override
	public int getValue() {
		return 6;
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
