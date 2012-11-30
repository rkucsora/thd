package thd;

import java.util.Arrays;
import java.util.List;

public class Regions 
{
	public List<IRegion> regions = Arrays.asList(new Ancona(), 
			new Bologna(), new Ferrara(), new Firenze(), new Lucca(), new Mantova(), new Milano(), new Modena(),
	new Napoli(), new Parma(), new Roma(), new Siena(), new Spoleto(), new Torino(), new Urbino(), new Venezia(), new Genova());

	public IRegion getHighestRegion()
	{
		int max = 0;
		IRegion maxregion = null;
		for(IRegion actualreg : regions)
		{
			if(!actualreg.isOccupied())
			{
				if(actualreg.getValue()>=max)
				{
					maxregion = actualreg;
				}
			}
		}
		return maxregion;
	}
	
	public void occupyRegion(String regionName)
	{
		for(IRegion actualreg : regions)
		{
			if(regionName.equals(actualreg.getRegionName()))
			{
				actualreg.setOccupied(true);
				return;
			}
		}
	}
	
	public void liberateRegion(String regonName)
	{
		for(IRegion actualreg : regions)
		{
			if(regonName.equals(actualreg.getRegionName()))
			{
				actualreg.setOccupied(true);
				return;
			}
		}
	}
	
}
