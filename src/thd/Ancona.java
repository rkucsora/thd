package thd;

public class Ancona implements IRegion
{
	
	@Override
	public String getRegionName() {
		return "Ancona";
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
