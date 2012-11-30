package thd;

public class Genova implements IRegion
{

	@Override
	public String getRegionName() {
		return "Genova";
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
