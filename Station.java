package utility;

import java.util.*;

public class Station 
{
	String name;
	Map<Station,Integer>neighbours;
	
	public Station(String name)
	{
		this.name=name;
		this.neighbours=new HashMap<>();
		
	}
	public void addNeighbour(Station neighbour,int distance)
	{
		this.neighbours.put(neighbour, distance);
	}
}
