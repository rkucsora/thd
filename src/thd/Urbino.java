package thd;

public class Urbino implements IRegion
{
	@Override
	public String getRegionName() {
		return "Urbino";
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
