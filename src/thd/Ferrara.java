package thd;

public class Ferrara implements IRegion
{

	@Override
	public String getRegionName() {
		return "Ferrara";
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
