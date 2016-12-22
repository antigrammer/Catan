
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
	
	//Identify
	@Override
	public String toString() {
		return "Connecting " + v[0].getID() + " and " + v[1].getID() + " (" + p + ")";
	}
	
}
