
public class Player {

	//Identity
	private int id;
	
	//Constructors
	public Player() {
		id = 0;
	}
	
	public Player(int i) {
		id = i;
	}
	
	//Identify
	@Override
	public String toString() {
		if (id == 0)
			return "null";
		return "P" + id;
	}
	
}
