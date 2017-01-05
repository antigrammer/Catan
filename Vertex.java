import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Vertex {

	//Map Structure
	private int id;
//	private HashMap<Integer, Vertex> adj;
	private ArrayList<Vertex> adj;
	private ArrayList<Tile> tiles;
	private HashMap<Vertex, Edge> edges;
	private Port port;
	private boolean city;
	
	//Player
	private Player p;
	
	//Constructor
	public Vertex(int i) {
		id = i;
		port = null;
		p = new Player();
//		adj = new HashMap<Integer, Vertex>();
		adj = new ArrayList<Vertex>();
		tiles = new ArrayList<Tile>();
		edges = new HashMap<Vertex, Edge>();
		city = false;
	}
	
	//Gameplay
	public boolean isCity() {
		return city;
	}
	
	public void cityUp() {
		city = true;
	}
	
	public void builtBy(Player builder) {
		p = builder;
	}
	
	public Player owner() {
		return p;
	}
	
	//Structure
	public void addTile(Tile t) {
		tiles.add(t);
	}
	
	public static void connect(Vertex v1, Vertex v2) {
		Edge u = new Edge(v1, v2);
//		v1.adj.put(v2.getID(), v2);
//		v2.adj.put(v1.getID(), v1);
		v1.adj.add(v2);
		v2.adj.add(v1);
		v1.edges.put(v2, u);
		v2.edges.put(v1, u);
	}
	
	//Identity
	public int getID() {
		return id;
	}
	
	public ArrayList<Vertex> getAdjacent() {
		return adj;
	}
	
	public ArrayList<Edge> edges() {
		ArrayList<Edge> es = new ArrayList<Edge>();
		for(Vertex v : adj) {
			es.add(edges.get(v));
		}
		return es;
	}
	
	public Edge getConnecting(Vertex v) {
		return edges.get(v);
	}
	
	public String edgeString() {
		String s = "Edges of vertex " + id;
		Iterator<Vertex> es = edges.keySet().iterator();
		while (es.hasNext()) {
			Vertex v = es.next();
			s += "\n\t" + edges.get(v).toString();
		}
		return s;
	}
	
	@Override
	public String toString() {
		String s = "--- " + id + "(" + p + ") ---";
		s += "\nRESOURCES:";
		for(Tile t : tiles)
			s += "\n\t" + t.toString();
		s += "\n" + edgeString();
		return s;
	}
	
}
