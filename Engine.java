import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Engine {

	public static void main(String[] args) throws IOException {

		final int size = 60;
		Board b = new Board(true, "board", 60);
		Player player = new Player(1);
		TreeMap<Character, Tile> tName = new TreeMap<Character, Tile>();
		HashMap<Integer, Tile> tID = b.getTiles();
		for(Integer id : tID.keySet())
			tName.put(tID.get(id).getChit().name(), tID.get(id));
		BoardDrawer.drawBoard(b);
		
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.print(player.id() + "~: ");
			while(!in.hasNextLine()) {}
			String[] input = in.nextLine().split(" ");
			switch(input[0]) {
			case "exit": 
				System.out.println("closing...");
				System.exit(0);
				break;
			case "turn": 
				int tempid = (player.id()+1)%4;
				if(tempid == 0)
					tempid = 4;
				System.out.println("it is now " + tempid + "'s turn");
				player = new Player(tempid);
				break;	
			case "build":
				if(input.length == 1) {
					System.out.println("argument needed");
					break;
				}
				switch(input[1]) {
				case "settlement": 
					if(input.length != 3) {
						System.out.println("argument needed of the form <TILE> <VERTEX>");
						break;
					}
					try {
						int id = parseDir(input[2], tName);
						System.out.println("placing settlement on: " + id);
						b.buildSettlement(id, player);
					} catch (Exception e) {
						System.out.println("vertex: " + e.getMessage());
					}
					break;
				case "city": 
					if(input.length != 3) {
						System.out.println("argument needed of the form <TILE> <VERTEX>");
						break;
					}
					try {
						int id = parseDir(input[2], tName);
						Player o = b.getVerticies().get(id).owner();
						if (!o.equals(player)) {
							System.out.println("city: you cannot upgrade " + o + "'s city");
						}
						else {
							System.out.println("upgrading to city: " + id);
							b.buildCity(id);	
						}
					} catch (Exception e) {
						System.out.println("vertex: " + e.getMessage());
					}
					break;
				case "road":
					if(input.length != 4) {
						System.out.println("argument needed of the form <TILE> <VERTEX1> <VERTEX2>");
						break;
					}
					else {
						boolean ok = true;
						int id1 = 0;
						int id2 = 0;
						try {
							id1 = parseDir(input[2], tName);
						} catch (Exception e) {
							ok = false;
							System.out.println("vertex1: " + e.getMessage());
						}
						try {
							id2 = parseDir(input[3], tName);
						} catch (Exception e) {
							ok = false;
							System.out.println("vertex2: " + e.getMessage());
						}
						if(true) {
							System.out.println("building road from " + id1 + " to " + id2);
							b.buildRoad(id1, id2, player);
						}
					}
					break;
				}
				break;
			default:
				System.out.println("invalid statement, could not parse");
			}
		}
		
	}
	
	public static int parseDir(String s, TreeMap<Character, Tile> tName) throws Exception {
		if(s.charAt(0) == 'X')
			s = "" + ' ' + s.substring(1);
		if('A' > s.charAt(0) || 'R' < s.charAt(0))
			throw new Exception("error: not a valid tile");
		int tileID = tName.get(s.charAt(0)).getID();
		switch(s.substring(1)) {
			case "N": return tileID - 100;
			case "NE": return tileID + 1;
			case "SE": return tileID - 10;
			case "S": return tileID + 100;
			case "SW": return tileID - 1;
			case "NW": return tileID + 10;
			default: throw new Exception("error; not a valid coordinate");
		}
	}
	
}
