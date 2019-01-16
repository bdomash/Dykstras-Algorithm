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
 /** 
 * This class is used to store data to calculate Sijkstra's algorithm
 * 
 * A Node is an object that stores a GraphNode<Location,Path>
 * with a corresponding predecessor, weight and visited variable
 * 
 * @author Brandon Domash
 */
class Node implements Comparable<Node>{
	//double to store the node's weight
	private double weight;
	//boolean to store whether the node has been visited
	private boolean visited;
	//Stores the GraphNode component of the node
	private GraphNode<Location,Path> node;
	//Stores a Node as a predecessor to the given node
	private Node predecessor;
	
	/**
	 * A parameterized constructor used to initialize the node
	 * with a given weight, visited, GraphNode and predecessor
	 * 
	 * For the purposes of this algorithm, weight should be initialized to
	 * Double.Max_Value, visited should be set to false and predecessor should
	 * be set to null
	 * 
	 * @param w The weight of the node
	 * @param v The visited component of the node
	 * @param n The actual GraphNode storing the Node's data
	 * @param p The Node's predecessor
	 */
	public Node(double w, boolean v, GraphNode<Location,Path> n,Node p){
		setWeight(w);
		setVisited(v);
		setNode(n);
		setPredecessor(p);
	}
	/**
	 * A get-method method to return the node's weight
	 * @return the node's weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * A mutator used to change the node's weight
	 * @param weight The weight that the node will be changed to
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * A get-method used to return whether the node has been visited
	 * @return Whether the node has been visited
	 */
	public boolean isVisited() {
		return visited;
	}
	/**
	 * A mutator used to change the node's visited variable
	 * @param visited A boolean to change the node's visited variable
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	/**
	 * A get-method that returns the GraphNode<Location,Path> of the Node
	 * @return the node's GraphNode<Location,Path> 
	 */
	public GraphNode<Location,Path> getNode() {
		return node;
	}
	/**
	 * A mutator used to change the node's GraphNode<Location,Path> 
	 * @param node A GraphNode<Location,Path> to change the Nodes GraphNode
	 */
	public void setNode(GraphNode<Location,Path> node) {
		this.node = node;
	}
	/**
	 * A get-Method that returns the node's predecessor
	 * @return The node's predecessor
	 */
	public Node getPredecessor() {
		return predecessor;
	}
	/**
	 * An mutator used to change the node's predecessor
	 * @param p The node's predecessor to be changed to
	 */
	public void setPredecessor(Node p) {
		this.predecessor = p;
	}
	/**
	 * This method defines how to compare two Nodes
	 * Nodes should be compared by weight, with larger weighted
	 * Nodes having higher values
	 */
	public int compareTo(Node o) {
		if(weight>o.getWeight()){
			return 1;
		}
		if(weight==o.getWeight()){
			return 0;
		}
		return -1;
	}
	
}
