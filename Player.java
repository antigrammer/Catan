import java.util.ArrayList;
import java.util.TreeMap;

public class Player {

	//Identity
	private int id;
	
	//Game State
	private int vp;
	private int[] resources;
	private int[] materials = {15, 5, 4};
	private  ArrayList<DevCard> developmentCards;
	
	//Board State
	private TreeMap<Integer, Vertex> settlements;
	private TreeMap<Integer, Vertex> cities;
	private ArrayList<Edge> roads;
	private ArrayList<Node> roadStruct;
	private int longestRoad;
	
	
	
	//Constructors
	public Player() {
		id = 0;
	}
	
	public Player(int i) {
		id = i;
		vp = 0;
		resources = new int[5];
		developmentCards = new ArrayList<DevCard>();
		initialize();
	}
	
	public void initialize() {
		settlements = new TreeMap<Integer, Vertex>();
		cities = new TreeMap<Integer, Vertex>();
		roads = new ArrayList<Edge>();
		roadStruct = new ArrayList<Node>();
	}
		
	//Gameplay
	public void turn() { 	}
	public String ask(String question, String prompt) {return null;}
	
	public void buildSettlement(Vertex v) {
		settlements.put(v.getID(), v);		//adding settlement
		resources[0] -= 1;					//building costs
		resources[1] -= 1;
		resources[3] -= 1;
		resources[4] -= 1;
		materials[1] -= 1;					//material cost
		vp += 1;							//gain a vp
	}
	
	public void buildCity(Vertex v) {
		settlements.remove(v.getID()); 	//replacing settlement with city
		cities.put(v.getID(), v);
		resources[2] -= 3;
		resources[3] -= 2;				//resource cost
		materials[1] += 1;				//material cost
		materials[2] -= 1;
		vp += 1;						//gain a vp
	}
	
	public void buildRoad(Edge e) {
		resources[0] -= 1;					//resource cost
		resources[4] -= 1;
		materials[0] -= 1;					//material cost
		roads.add(e);						
		Node n = new Node(e);				//adding road to road structure
		for(Node no : roadStruct)
			if(n.isAdjacent(no))
				n.addNode(no);
		roadStruct.add(n);
		longestRoad = longestRoad(roadStruct);
	}
	
	public static int longestRoad(ArrayList<Node> struct) {
		for(Node n : struct) {
			if(!n.hasParent)
				n.updateValue();
		}
		int lr = 0;
		for(Node n : struct)
			if(n.value > lr)
				lr = n.value;
		return lr + 1;
	}
	
	//Identify
	public int id() {
		return id;
	}
	
	public String resources() {
		return "BRICK: " + resources[0] + " WOOL: " + resources[1] +
				" ORE: " + resources[2] + " GRAIN: " + resources[3] +
				" LUMBER: " + resources[4];
	}
	
	public String materials() {
		return "ROADS: " + materials[0] + " SETTLEMENTS: " + materials[1] +
				" CITIES: " + materials[2];
	}
	
	public String status() {
		return this.toString() + "\n" + resources() + "\n" + materials();
	}
	
	@Override
	public String toString() {
		if (id == 0)
			return "null";
		return "P" + id + "(" + vp + ")";
	}
////////////////////////////////////////////////////////////////////////////////
	private class Node {
		
		//Connections
//		private Node parent;
		private boolean hasParent;
		private ArrayList<Node> children;
		private boolean hasChildren;
		
		//Identity
		private Edge road;
		private int value;
		
		//Constructor
		public Node(Edge r) {
			road = r;
			hasParent = false;
			value = 0;
			children = new ArrayList<Node>();
		}

		//Connect
		public boolean hasParent() {
			return hasParent;
		}
		
		public boolean hasChildren() {
			return hasChildren;
		}
		
		public void addParent(Node n) {
			hasParent = true;
//			parent = n;
		}
		
		public void addChild(Node n) {
			hasChildren = true;
			children.add(n);
		}
		
		public void addNode(Node n) {
			if(!n.hasParent) {
				n.addParent(this);
				addChild(n);
			}
			else {
				addParent(n);
				n.addChild(this);
			}
		}
		
		//Identify
		public ArrayList<Edge> getAdjacent() {
			return road.getAdjacent();
		}
		
		public boolean isAdjacent(Node n) {
			return this.road.isAdjacent(n.road);
		}
		
		//Gameplay		
		public int updateValue() {
			if (children.size() == 0) {				//if leaf, value = 0
				value = 0;
				return -1;
			}
			int ready = children.size();			//wait until all children
			for(Node n : children) {				//have updated
				ready += n.updateValue();
			}
			while (ready != 0) {}
			if (children.size() == 1)	{			//if one child, value =
				value = children.get(0).value + 1;	//value of child + 1
			}
			else {									//if multiple children,
				int m1 = 0;							//value = sum of two maximum
				int m2 = 0;							//values of children + 2
				for(Node n : children) {
					if(n.value >= m1) {
						m2 = m1;
						m1 = n.value;
					}
					else if(n.value > m2)
						m2 = n.value;
				}
				value = 2 + m1 + m2;
			}
			return -1;
		}
			
	}
	
}
