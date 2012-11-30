package thd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Regions 
{
	/**
	 * First element is the region name, the others are the name of the adjacent regions
	 */
	public String[][] setup = {
			{"Torino", "Milano", "Genova"},
			{"Milano", "Torino", "Venezia", "Genova", "Mantova", "Modena", "Parma"},
			{"Venezia", "Milano", "Mantova", "Ferrara"},
			{"Genova", "Torino", "Milano", "Parma"},
			{"Mantova", "Milano", "Venezia", "Ferrara", "Modena"},
			{"Parma", "Genova", "Milano", "Modena", "Lucca"},
			{"Modena", "Parma", "Milano", "Mantova", "Ferrara", "Bologna", "Firenze", "Lucca"},
			{"Ferrara", "Venezia", "Mantova", "Modena", "Bologna"},
			{"Bologna", "Ferrara", "Modena", "Firenze", "Urbino"},
			{"Lucca", "Parma", "Modena", "Firenze"},
			{"Firenze", "Lucca", "Modena", "Bologna", "Urbino", "Spoleto","Siena", "Roma"},
			{"Siena", "Firenze", "Roma"},
			{"Spoleto", "Firenze", "Urbino", "Ancona", "Napoli", "Roma"},
			{"Urbino", "Bologna", "Firenze", "Spoleto", "Ancona"},
			{"Ancona", "Urbino", "Spoleto", "Napoli"},
			{"Roma", "Siena", "Firenze", "Spoleto", "Napoli"},
			{"Napoli", "Roma", "Spoleto", "Ancona"}
	};
	
	private List<Region> createRegionMap()
	{
		Map<String, Region> regsMap = new HashMap<String, Region>();
		List<Region> regions = new ArrayList<Region>();
		// Create regions without adjacent regions
		for(String[] regData: setup)
		{
			Region region = new Region(regData[0]);
			regsMap.put(regData[0], region);
			regions.add(region);
		}
		// add adjacent regions
		for(String[] regData: setup)
		{
			Region region = regsMap.get(regData[0]);
			List<Region> adj = new ArrayList<Region>();
			for(int i = 1; i < regData.length; ++i)
			{
				adj.add( regsMap.get(regData[i]));
			}
			region.setAdjacentRegions(adj.toArray(new Region[adj.size()]));
		}
		return regions;
	}
	
	public List<Region> regions = createRegionMap();

	public Region getHighestRegion()
	{
		int max = 0;
		Region maxregion = null;
		for(Region actualreg : regions)
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
		for(Region actualreg : regions)
		{
			if(regionName.equals(actualreg.getRegionName()))
			{
				actualreg.setOccupied(true);
				return;
			}
		}
	}
	
	public Region getRegion(String name)
	{
		for(Region actualreg : regions)
		{
			if(name.equals(actualreg.getRegionName()))
			{
				actualreg.setOccupied(true);
				return actualreg;
			}
		}
		return null;
	}
	
	public void occupyRegionBy(String regionName, Player player)
	{
		Region region = getRegion(regionName);
		region.occupyBy(player);
	}
	
	public void liberateRegion(String regonName)
	{
		for(Region actualreg : regions)
		{
			if(regonName.equals(actualreg.getRegionName()))
			{
				actualreg.setOccupied(true);
				return;
			}
		}
	}
	
}
