import java.util.ArrayList;

public class Edge {

	//Map Structure
	private Vertex[] v;
	
	//Player
	private Player p;
	
	//Constructor
	public Edge(Vertex v1, Vertex v2) {
		v = new Vertex[2];
		v[0] = v1;
		v[1] = v2;
		p = new Player();
	}
	
	//Gameplay
	public void builtBy(Player builder) {
		p = builder;
	}
	
	public Player owner() {
		return p;
	}
	
	//Identify
	public ArrayList<Edge> getAdjacent() {
		ArrayList<Edge> adj = new ArrayList<Edge>();
		for(Vertex v : v[0].getAdjacent())
			for(Edge e : v.edges())
				if (!e.equals(this))
					adj.add(e);
		for(Vertex v : v[1].getAdjacent())
			for(Edge e : v.edges())
				if (!e.equals(this))
					adj.add(e);
		return adj;
	}
	
	public boolean isAdjacent(Edge e) {
		return getAdjacent().contains(e);
	}
	
	@Override
	public String toString() {
		return "Connecting " + v[0].getID() + " and " + v[1].getID() + " (" + p + ")";
	}
	
}
