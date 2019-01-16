import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGraph {
	public static void main(String[] args){
		String[] props = {"cost"};
		
		NavigationGraph g = new NavigationGraph(props);
		for(char c = 'A'; c <= 'H';c++){
			g.addVertex(new Location(Character.toString(c)));
		}
		
		
		Location a = new Location("A");
		Location b = new Location("B");
		Location c = new Location("C");
		Location d = new Location("D");
		Location e = new Location("E");
		Location f = new Location("F");
		Location g2 = new Location("G");
		Location h = new Location("H");
		
		

		System.out.println(g.getSize());
		for(Location l : g.getVertices()){
			System.out.println(l);
		}
		
		Path AE = new Path(a,e,new ArrayList<Double>((Arrays.asList(15.0))));
		Path AB = new Path(a,b,new ArrayList<Double>((Arrays.asList(4.0))));
		Path AC = new Path(a,c,new ArrayList<Double>((Arrays.asList(2.0))));
		Path CD = new Path(c,d,new ArrayList<Double>((Arrays.asList(5.0))));
		Path BD = new Path(b,d,new ArrayList<Double>((Arrays.asList(1.0))));
		Path BE = new Path(b,e,new ArrayList<Double>((Arrays.asList(10.0))));
		Path DE = new Path(d,e,new ArrayList<Double>((Arrays.asList(3.0))));
		Path DF = new Path(d,f,new ArrayList<Double>((Arrays.asList(0.0))));
		Path FD = new Path(f,d,new ArrayList<Double>((Arrays.asList(2.0))));
		Path FH = new Path(f,h,new ArrayList<Double>((Arrays.asList(4.0))));
		Path GH = new Path(g2,h,new ArrayList<Double>((Arrays.asList(4.0))));
		System.out.println();
		
		ArrayList<Path> paths = new ArrayList<Path>(Arrays.asList(AE,AB,AC,CD,BD,BE,DE,DF,FD,FH,GH));
		for(Path p: paths){
			System.out.println(p);
			g.addEdge(p.getSource(), p.getDestination(), p);
			//System.out.println(p);
		}
		
		System.out.println(g.getOutEdges(new Location("A")));
		
		for(Location l: g.getNeighbors(a))
			System.out.println(l);
		//System.out.println();
		
		g.addVertex(new Location("A"));
		System.out.println(g.getVertices());
		//System.out.println();
		//g.addEdge(new Location("A"), new Location("A"), AB);
		
		System.out.println(g.getShortestRoute(a,f, "cost"));
		System.out.println(g.getLocationByName("A"));
		/*
		 *
		NavigationGraph n = new NavigationGraph(props);
		n.addVertex(a);
		n.addVertex(c);
		n.addEdge(a, c, AC);
		System.out.println(n.getShortestRoute(a, c, "cost"));
		*/
	}
}
