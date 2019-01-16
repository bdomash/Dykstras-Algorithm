/**
 * /////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2016 
// PROJECT:          (project name)
// FILE:             (file name)
//
// TEAM:    p5Team 130
// Authors: Brandon Domash
//
// /////////////////////////////////////////////////////////////////////////////
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
 /** 
 * This class implements GraphADT using the GraphNode class and calculates
 * Djiksra's Algorithm for the given tree
 * For this class, the Vertices are type Location and the Edges are type Path
 * @author Brandon Domash
 */
public class NavigationGraph implements GraphADT<Location, Path> {

	//A HashMap that Maps a String to a GraphNode<Location,Path>>
	private HashMap<String,GraphNode<Location,Path>> map;
	//String[] of valid properties that will be passes in through the constructor
	private String[] validProperties;
	//Keeps track of the number of GraphNode<Location,Path>> in the Graph
	private int size;
	
	/**
	 * A parameterized constructor used to initialize the Navigation Graph
	 * @param edgePropertyNames An array of valid property nanmes in the graph
	 */
	public NavigationGraph(String[] edgePropertyNames) {
		map = new HashMap<String,GraphNode<Location,Path>>();
		validProperties = edgePropertyNames;
		size = 0;
	}

	
	

	/**
	 * Adds a Vertex to the Graph
	 * Keeps track of the size of the Graph
	 * @param vertex The vertex to be added to the graph
	 * @throws and IllegalArgumentException if the Vertex is null
	 */
	public void addVertex(Location vertex) {
		if(vertex==null){
			throw new IllegalArgumentException("The location you entered is null");
		}
		if(!map.containsKey(vertex.getName()))
		{
			map.put(vertex.getName(), new GraphNode<Location,Path>(vertex,size));
			size++;
		}
	}


	/**
	 * Given two Locations in the graph, adds an Path between them
	 * @param src The source Location
	 * @param dest The destination Location
	 * @param edge The Path to be added between locations
	 * @throws IllegalArgumentException if any of the arguments are null
	 * or if the map does not contain either location
	 */
	public void addEdge(Location src, Location dest, Path edge) {
		if(src == null || dest == null || edge == null || src.getName()==dest.getName()){
			throw new IllegalArgumentException("Something was null, or you tried to add a self edge");
		}
		//directs were very unclear whether or not this is needed
		/*
		if(getEdgeIfExists(src, dest)!=null){
			throw new IllegalArgumentException();
		}
		*/
		
		if(!map.containsKey(src.getName())||!map.containsKey(dest.getName())){
			throw new IllegalArgumentException("One of those locations is not in the graph");
		}
		GraphNode<Location,Path> node = map.get(src.getName());
		node.addOutEdge(edge);
		
	}

	/**
	 * Returns a List<Location> containing all of the vertices in the graph
	 * @return a List<Location> containing all of the vertices in the graph
	 */
	public List<Location> getVertices() {
		List<Location> loc = new ArrayList<Location>();
		for(GraphNode<Location,Path> node : map.values()){
			loc.add(node.getVertexData());
		}
		return loc;
	}


	/**
	 * Given 2 locations, returns a path between the two locations if
	 * one exists in the graph
	 * @param src The source location
	 * @param dest The destination location
	 * @return The path between the two locations, or null if it does not exist
	 * @throws IllegalArgumentException if any of the parameters are null
	 * or if the src and dest parameters are the same
	 */
	public Path getEdgeIfExists(Location src, Location dest) {
		if(src == null || dest == null || src.getName() == dest.getName()){
			throw new IllegalArgumentException("Something is null or you tried to get a self-edge");
		}
		if(!map.containsKey(src.getName())||!map.containsKey(dest.getName())){
			return null;
		}
		GraphNode<Location,Path> node = map.get(src.getName());
		for(int i = 0; i<node.getOutEdges().size();i++){
			Path path = node.getOutEdges().get(i);
			if(path.getDestination().equals(dest)){
				return path;
			}
		}
		return null;
	}
	/**
	 * Returns the outgoing edges from a vertex
	 * 
	 * @param src
	 *            Source vertex for which the outgoing edges need to be obtained
	 * @return List of edges of type E
	 * @throws IllegalArgumentException 
	 */
	public List<Path> getOutEdges(Location src) {
		if(src==null || !map.containsKey(src.getName())){
			throw new IllegalArgumentException("The location is null or not in the graph");
		}
		return map.get(src.getName()).getOutEdges();
	}


	/**
	 * Returns neighbors of a vertex
	 * 
	 * @param vertex
	 *            vertex for which the neighbors are required
	 * @return List of vertices(neighbors) of type V
	 * @throws IllegalArgumentException if the vertex is null
	 * or if the vertex is not in the graph
	 */
	public List<Location> getNeighbors(Location vertex) {
		if(vertex==null || !map.containsKey(vertex.getName())){
			throw new IllegalArgumentException("The location is null or not in the graph");
		}
		List<Location> locs = new ArrayList<Location>();
		GraphNode<Location,Path> node = map.get(vertex.getName());
		for(int i = 0;i<node.getOutEdges().size();i++){
			locs.add(node.getOutEdges().get(i).getDestination());
		}
		return locs;
	}


	/**
	 * Calculate the shortest route from src to dest vertex using
	 * edgePropertyName
	 * 
	 * @param src
	 *            Source vertex from which the shortest route is desired
	 * @param dest
	 *            Destination vertex to which the shortest route is desired
	 * @param edgePropertyName
	 *            edge property by which shortest route has to be calculated
	 * @return List of edges that denote the shortest route by edgePropertyName
	 * @throws IllegalArgumentException if any of the parameters are null,
	 *  if the src and dest are the same, if the edgeProperty is not valid, if
	 *  the map does not contain src or dest, or if there is no route between src and
	 *  dest
	 */
	public List<Path> getShortestRoute(Location src, Location dest, String edgePropertyName) {
		if(src == null || dest == null || src.getName() == dest.getName() || edgePropertyName == null){
			throw new IllegalArgumentException("Something is null or you tried to find a self-edge");
		}
		if(!map.containsKey(src.getName())||!map.containsKey(dest.getName())){
			throw new IllegalArgumentException("The location you entered is not in the graph");
		}
		if(!validProperty(edgePropertyName)){
			throw new IllegalArgumentException("Invalid edge property");
		}
		
		//Dijkstra algorithm Start
		double max = Double.MAX_VALUE;
		
		//Hashmap used to map a String to nodes
		HashMap<String,Node> nodes = new HashMap<String,Node>();
		
		//taking each GraphNode in the map and converting it to a Node data type
		//adding it to the nodes HashMap
		for(GraphNode<Location,Path> node : map.values())
		{
			//each node visited set to false, each vertex v's total weight infinity
			//each vertex v's predecessor is null
			//each node as an id, the index it was added in
			nodes.put(node.getVertexData().getName(),new Node(max,false,node,null));
		}
		
		Node start = nodes.get(src.getName());
		
		//"initializing" first vertex, setting its weight to zero
		start.setWeight(0);

		//priming the priority queue for the algorithm
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		pq.add(start);
		
		//run algorithm until pq is empty
		while(!pq.isEmpty()){
			Node curr = pq.remove();
			curr.setVisited(true);
			
			for(Path p : curr.getNode().getOutEdges()){
				Location loc = p.getDestination();
				
				//mapping each path destination location to a Node in nodes HashMap
				Node succ = nodes.get(loc.getName());
				
				//if the mapped node has not been visited, run algorithm
				if(!succ.isVisited()){
					//calculating the new weight of the node
					double newWeight = curr.getWeight()+
							p.getProperties().get(getPropertyIndex(edgePropertyName));
					if(newWeight<succ.getWeight()){
						succ.setWeight(newWeight);
						//setting predecessor of the node
						succ.setPredecessor(curr);
						
					}
					
					boolean inQ = false;
					//checking if the Node is already in the PQ
					for(Node n : pq){
						if(n.getNode().getId()==succ.getNode().getId()){
							inQ = true;
						}
					}
					//if node not already in PQ, add it
					if(!inQ){
						pq.add(succ);
					}
				}
			}
			
		}
		
		//this arrayList will be used to store the path of Nodes from src to dest
		//eventually used to create the List<Path> to be returned
		//The nodes in this array will be the Nodes of the route
		ArrayList<Node> shortestPathOfNodes = new ArrayList<Node>();

		Node destination = nodes.get(dest.getName());
		
		
		Node temp = destination;
		
		//adds each node in reverse order so src is at beginning and dest is at end
		//This is because we are starting at destination and working backwards, as
		//we know each node's predecessor but not successor
		do{
			shortestPathOfNodes.add(0, temp);
			temp = temp.getPredecessor();
		}while(temp!=null);
		

		//arraylist of paths constructed from the list of Nodes
		//probably not the most efficient way of constructing the path but good enough
		ArrayList<Path> routeOfPaths = new ArrayList<Path>();
		for(int i = 0;i<shortestPathOfNodes.size()-1;i++){
			Location fromNode = shortestPathOfNodes.get(i).getNode().getVertexData();
			Location toNode = shortestPathOfNodes.get(i+1).getNode().getVertexData();
			routeOfPaths.add(getEdgeIfExists(fromNode, toNode));
			
		}
		return routeOfPaths;
		
		
		
		
	}


	/**
	 * Getter method for edge property names
	 * 
	 * @return array of String that denotes the edge property names
	 */
	public String[] getEdgePropertyNames() {
		return validProperties;
	}
	/**
	 * Returns a Location object given its name
	 * 
	 * @param name
	 *            name of the location
	 * @return Location object
	 */
	public Location getLocationByName(String name) {
		if(name==null){
			throw new IllegalArgumentException("The name you entered is null");
		}
		if(map.containsKey(name)){
			return map.get(name).getVertexData();
		}
		return null; 
	}
	/**
	 * Helper method to determine if the passed in property to getShortestRoute
	 * is in validproperties
	 * @param prop property passed into getShortestRoute
	 * @return Whether or not the property is valid
	 */
	private boolean validProperty(String prop){
		for(int i = 0; i<validProperties.length;i++){
			if(validProperties[i].equals(prop)){
				return true;
			}
		}
		return false;
	}
	/**
	 * Given a string, returns an int for the index of a given property value 
	 * Throws an IllegalArgumentException if the Property is not valid
	 * @param path
	 * @return
	 */
	private int getPropertyIndex(String propertyName){
		for(int i = 0; i<validProperties.length;i++){
			if(propertyName.equals(validProperties[i])){
				return i;
			}
		}
		throw new IllegalArgumentException("Invalid Edgeproperty index");
	}
	/**
	 * Builds and returns a toString
	 * @return a string representation of the class
	*/
	public String toString(){
		String str = "";
		for(GraphNode<Location,Path> g : map.values()){
			for(Path p:g.getOutEdges())
				str+=p + ", ";
		}
		//gets rid of the last comma and space at the end of the string
		return str.substring(0, str.length()-2);
	}
	
	

	
	

}
