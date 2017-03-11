import java.awt.Color;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Human extends Player{

	//Constructor
	public Human(int i, Color c) {
		super(i, c);
	}
	
	//Gameplay
	@Override
	public void turn() { 	}
	
	@Override
	public boolean isHuman() { 
		return true;
	}
	
	@Override
	public String ask(String question, String prompt) {
		System.out.print(prompt);
		Scanner in = new Scanner(System.in);
		boolean response = false;
		String answer = "";
		do {
			while(!in.hasNextLine()) {}
			switch(in.nextLine()) {
				
				default: System.out.println("error: not a valid response");
			}
		}
		while (!response);
		response = false;
		return answer;
	}
	
	
}
