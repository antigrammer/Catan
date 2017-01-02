import java.io.IOException;

public class Engine {

	public static void main(String[] args) throws IOException {

		Board b = new Board(true);
		BoardDrawer.drawBoard(b, 60);
		b.writeImage("board");
		Player p1 = new Player(1);
		System.out.println("---------------------------\n" + b + "\n---------------------------");
		System.out.println("---------------------------\n" + p1.status() + "\n---------------------------");
	
		b.buildSettlement(-170, p1);
		System.out.println("---------------------------\n" + b + "\n---------------------------");
		System.out.println("---------------------------\n" + p1.status() + "\n---------------------------");
		
	}
	
}
