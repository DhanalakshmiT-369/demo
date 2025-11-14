package utility;

class Pair implements Comparable<Pair>
{

	Station station;
	int distance;
	
	public Pair(Station station,int distance)
	{
		this.station=station;
		this.distance=distance;
	}
	@Override
	public int compareTo(Pair that) {
		
		return Integer.compare(this.distance,that.distance);
	}
	
}
