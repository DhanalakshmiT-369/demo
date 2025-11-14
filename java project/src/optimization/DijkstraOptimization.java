package optimization;
import java.util.*;

public class DijkstraOptimization 
{
	public static List<String> findShortestPath(Station start,Station end)
	{
		Map<Station ,Integer> distances=new HashMap<>();
		Map<Station,Station>predecessors=new HashMap<>();
		PriorityQueue<Pair>pq=new PriorityQueue<>();
		
		distances.put(start,0);
		pq.add(new Pair(start,0));
		
		while(!pq.isEmpty())
		{
			Pair current =pq.poll();
			Station currentStation=current.station;
			int currentDistance=current.distance;
			
			if(currentDistance > distances.getOrDefault(currentStation,Integer.MAX_VALUE)) 
			{
				continue;
			}
			
			for(Map.Entry<Station,Integer>entry : currentStation.neighbours.entrySet())
			{
				Station neighbour=entry.getKey();
				int weight=entry.getValue();
				int newDistance=currentDistance+weight;
				
				if(newDistance < distances.getOrDefault(neighbour, Integer.MAX_VALUE))
				{
					distances.put(neighbour,newDistance);
					predecessors.put(neighbour, currentStation);
					pq.add(new Pair(neighbour,newDistance));
				}
			}
		}
		
		
		List<String> path=new ArrayList<>();
		Station temp=end;
		while(temp!=null && predecessors.containsKey(temp)) 
		{
			path.add(0,temp.name);
			temp=predecessors.get(temp);
			
		}
		if (temp!=null && temp.equals(start))
		{
			path.add(0,start.name);
		}
		return path;
	}
	
}