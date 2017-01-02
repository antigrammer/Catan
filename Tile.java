import java.util.ArrayList;

public class Tile {

	//Map Structure
	private Chit chit;
	private String terrain;
	private ArrayList<Vertex> v;
	private boolean robber;
	
	//Constructor
	public Tile(Chit c, String t) {
		chit = c;
		terrain = t;
		v = new ArrayList<Vertex>();
		robber = false;
	}
	
	//Structure
	public void addVertex(Vertex vert) {
		if (!v.contains(vert)) {
			v.add(vert);
			vert.addTile(this);	
		}
	}
	
	public boolean hasRobber() {
		return robber;
	}
	
	public void setRobber(boolean b) {
		robber = b;
	}
	
	//Identify
	public String getTerrain() {
		return terrain;
	}
	
	public Chit getChit() {
		return chit;
	}
	
	public int[] vertexIds() {
		int[] vs = new int[6];
		for(int i = 0; i < 6; i++)
			vs[i] = v.get(i).getID();
		return vs;
	}
	
	@Override
	public String toString() {
		return terrain + "(" + chit + ")";
	}
	
}
