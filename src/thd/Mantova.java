package thd;

public class Mantova implements IRegion
{

	@Override
	public String getRegionName() {
		return "Mantova";
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
