package thd;

public class Spoleto implements IRegion
{

	@Override
	public String getRegionName() {
		return "Spoleto";
	}

	@Override
	public int getValue() {
		return 5;
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
