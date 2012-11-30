package thd;

public class Firenze implements IRegion
{

	@Override
	public String getRegionName() {
		return "Firenze";
	}

	@Override
	public int getValue() {
		return 7;
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
