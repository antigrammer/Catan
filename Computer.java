import java.awt.Color;


public class Computer extends Player {

	//Constructor
	public Computer(int i, String fileName) {
		super(i);
	}
	
	public Computer(int i, Color c, String fileName) {
		super(i, c);
	}
	
	//Gameplay
	public void turn() { 	}
	public String ask(String question, String prompt) {return null;}
	
}
