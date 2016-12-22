import java.util.ArrayList;


public class Tile {

	//Map Structure
	private int chit;
	private String terrain;
	private ArrayList<Vertex> v;
	private boolean robber;
	
	//Constructor
	public Tile(int c, String t) {
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
	
	public int getChit() {
		return chit;
	}
	
	public String toString() {
		return terrain + "(" + chit + ")";
	}
	
}
