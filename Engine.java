import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Engine {

	static boolean gameType = false;		//simulation = true, testing = false
	static int numPlayers = 0;				//number of players
	static int numHumans = 0;
	static int numCPU = 0;
	static String[] stratFiles;				//strategies for CPUs
	static int numEvos = 0;					//number of evolutions
	static int evoTime = 0;					//time before evolution
	static boolean transcribe = false;		//whether or not to transcribe
	static String transcript = "";			//file to transcribe to
	static boolean track = false;			//whether or not to track picture
	private static String picture = "";		//file of game picture
	
	static ArrayList<Player> players;		//players
	static Board board;						//board
	
	public static void main(String[] args) throws IOException {

		
////////////////////////////CONFIGURATION////////////////////////////////////////
		
		boolean ready = false;
		boolean temp = false;
		Scanner in = new Scanner(System.in);
		
		System.out.println("---config---");
		//LOAD EXISTING CONFIG FILE
		do {
			System.out.println("Do you want to load configuration from a config file? (y/n)");
			while(!in.hasNextLine()) {}
			switch(in.nextLine()) {
				case "yes":
				case "y":
					System.out.print("File name: ");
					while(!in.hasNextLine()) {}
					String fn = in.nextLine();
					if(Pattern.matches(".*[.].*", fn.substring(0)))
						System.out.println("error: enter file name with no extension");
					else if(isLegalConfig(fn)) {
						temp = true;
						ready = true;
					}
					else
						System.out.println("error: not a legal config file");
					break;
				case "no":
				case "n":
					temp = true;
					break;
				default: System.out.println("error: not a valid response");
			}
			
		}
		while (!temp);
		temp = false;
		
		
		while(!ready) {
			
			//CHOOSE GAME TYPE
			do {
				System.out.println("Choose game type\n1)Simulation\n2)Testing");
				while(!in.hasNextLine()) {}
				switch(in.nextLine()) {
					case "1":
						gameType = true;
						temp = true;
						break;
					case "2":
						gameType = false;
						temp = true;
						break;
					default: System.out.println("error: not a valid game type");
				}
			}
			while (!temp);
			temp = false;
			
			//number of players
			do {
				System.out.print("Number of players: ");
				while(!in.hasNextLine()) {}
				switch(in.nextLine()) {
					case "4":
						numPlayers++;
					case "3":
						numPlayers++;
					case "2":
						numPlayers++;
					case "1":
						numPlayers++;
						temp = true;
						break;
					default: System.out.println("error: not a valid number of players");
				}
			}
			while (!temp);
			temp = false;
			
			//NUMBER OF HUMANS
			if (!gameType) {
				do {
					System.out.print("Number of humans: ");
					while(!in.hasNextLine()) {}
					try {
						int i = Integer.parseInt(in.nextLine());
						if (i > numPlayers)
							System.out.println("error: not a valid number of humans");
						else if (i < 0)
							System.out.println("error: not a valid number of humans");
						else {
							numHumans = i;
							temp = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("error: not a valid number");
					}
				}
				while (!temp);
				temp = false;
			}
			else
				numHumans = 0;
			//NUMBER OF CPUS
			numCPU = numPlayers - numHumans;
			
			//CPU STRATEGIES
			stratFiles = new String[numCPU];
			for(int i = 0; i < numCPU; i++) {
				do {
					System.out.println("Load a .strat file for CPU" + (i+1) + "? (y/n)");
					while(!in.hasNextLine()) {}
					switch(in.nextLine()) {
						case "yes":
						case "y":
							System.out.print("File name: ");
							while(!in.hasNextLine()) {}
							String fn = in.nextLine();
							if(Pattern.matches(".*[.].*", fn.substring(0)))
								System.out.println("error: enter file name with no extension");
							else if(isLegalStrat(fn)) {
								stratFiles[i] = fn;
								temp = true;
							}
							else
								System.out.println("error: not a legal strat file");
							break;
						case "no":
						case "n":
							System.out.println("The default .strat file for this computer is com" + (i+1) + ".strat");
							stratFiles[i] = "com" + (i+1);
							makeStrat("com" + (i+1));
							temp = true;
							break;
						default: System.out.println("error: not a valid response");
					}
				}
				while (!temp);
				temp = false;
			}
			
			//NUMBER OF EVOLUTIONS AND EVOLUTION TIME (SIMULATION ONLY)
			if (gameType) {
				do {
					System.out.print("Number of evolutions: ");
					while(!in.hasNextLine()) {}
					try {
						int i = Integer.parseInt(in.nextLine());
						if (i > 1000 || i < 1)
							System.out.println("error: too many games to play at once <temporary>");
						else if (i < 1)
							System.out.println("error: not a valid number of evolutions (1-1000)");
						else {
							numEvos = i;
							temp = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("error: not a valid number");
					}
				}
				while (!temp);
				temp = false;
				
				do {
					System.out.print("Evolution time: ");
					while(!in.hasNextLine()) {}
					try {
						int i = Integer.parseInt(in.nextLine());
						if (i > 100 || i < 1)
							System.out.println("error: evo time too long <temporary>");
						else if (i < 1)
							System.out.println("error: not a valid number of games before evolution (1-100)");
						else {
							evoTime = i;
							temp = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("error: not a valid number");
					}
				}
				while (!temp);
				temp = false;
			}
			
			//TRANSCRIPT (TESTING ONLY)
			if (!gameType) {
				do {
					System.out.println("Do you want to keep a transcript of the game? (y/n)");
					while(!in.hasNextLine()) {}
					switch(in.nextLine()) {
						case "yes":
						case "y":
							transcribe = true;
							System.out.print("File to transcribe to: ");
							while(!in.hasNextLine()) {}
							transcript = in.nextLine();
							if(Pattern.matches(".*[.].*", transcript.substring(0)))
								System.out.println("error: enter file name with no extension");
							else
								temp = true;
							break;
						case "no":
						case "n":
							temp = true;
							break;
						default: System.out.println("error: not a valid response");
					}
					
				}
				while (!temp);
				temp = false;
			}
			
			//IMAGE (TESTING ONLY)
			if (!gameType) {
				do {
					System.out.println("Do you want to keep save the game image? (y/n)");
					while(!in.hasNextLine()) {}
					switch(in.nextLine()) {
						case "yes":
						case "y":
							track = true;
							System.out.print("File to save picture: ");
							while(!in.hasNextLine()) {}
							picture = in.nextLine();
							if(Pattern.matches(".*[.].*", picture.substring(0)))
								System.out.println("error: enter file name with no extension");
							else
								temp = true;
							break;
						case "no":
						case "n":
							temp = true;
							break;
						default: System.out.println("error: not a valid response");
					}
					
				}
				while (!temp);
				temp = false;
			}
			
			//SAVE CONFIG FILE
			if (!gameType) {
				do {
					System.out.println("Do you want to save these configurations? (y/n)");
					while(!in.hasNextLine()) {}
					switch(in.nextLine()) {
						case "yes":
						case "y":
							System.out.print("File to save as: ");
							while(!in.hasNextLine()) {}
							String tocfg = in.nextLine();
							if(Pattern.matches(".*[.].*", tocfg.substring(0)))
								System.out.println("error: enter file name with no extension");
							else {
								PrintStream outcfg = new PrintStream(new FileOutputStream("" + tocfg + ".cfg"));
								if(gameType)
									outcfg.println("simulation");
								else
									outcfg.println("testing");
								outcfg.println(numPlayers);
								outcfg.println(numHumans);
								outcfg.println(numCPU);
								for(int i = 0; i < stratFiles.length; i++) {
									outcfg.println(stratFiles[i] + ".strat");
								}
								if(gameType) {
									outcfg.println(numEvos);
									outcfg.println(evoTime);
								}
								else {
									if(transcribe) {
										outcfg.println("transcribe");
										outcfg.println(transcript);
									}
									else 
										outcfg.println("no transcript");
									if(track) {
										outcfg.println("track image");
										outcfg.println(picture);
									}
									else
										outcfg.println("no tracking");
								}
								outcfg.close();
								temp = true;
							}
							break;
						case "no":
						case "n":
							temp = true;
							break;
						default: System.out.println("error: not a valid response");
					}
					
				}
				while (!temp);
				temp = false;
			}
			
			ready = true;
			
		}
		
		System.out.println("---config---");
		
/////////////////////////////////////////////////////////////////////////////////
		
		System.exit(0);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//COLOR OF PLAYERS
		System.out.println("Colors are currently fixed as follows:");
		System.out.println("Player 1 = blue\nPlayer 2 = red\nPlayer 3 = white\nPlayer 4 = orange");
		
		
		//GENERATING PLAYERS
		int[] rolls = new int[numPlayers];
		if(!gameType) {
			for(int j = 0; j < numHumans; j++) {
				do {
					System.out.println("P" + (j+1) + "'s starting roll: ");
					while(!in.hasNextLine()) {}
					try {
						int i = Integer.parseInt(in.nextLine());
						if (i > 12)
							System.out.println("error: not a valid roll");
						else if (i < 2)
							System.out.println("error: not a valid roll");
						else {
							rolls[j] = i;
							temp = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("error: not a valid number");
					}
				}
				while (!temp);
				temp = false;
			}
			for(int j = numHumans; j < numPlayers; j++) {
				do {
					System.out.println("COM" + (j+1-numHumans) + "'s starting roll: ");
					while(!in.hasNextLine()) {}
					try {
						int i = Integer.parseInt(in.nextLine());
						if (i > 12)
							System.out.println("error: not a valid roll");
						else if (i < 2)
							System.out.println("error: not a valid roll");
						else {
							rolls[j] = i;
							temp = true;
						}
					} catch (NumberFormatException e) {
						System.out.println("error: not a valid number");
					}
				}
				while (!temp);
				temp = false;
			}
			int maxRoll = 0;
			int maxP = 0;
			for(int i = 0; i < numPlayers; i++) {
				
			}
		}
		
		
		
		
		
		
		
		
		for(int i = 0; i < numPlayers; i++) {
			System.out.println(rolls[i]);
		}
		System.exit(0);
		
		////////////////////////////////////////////////////////////////////////
		
		final int size = 60;
		Board b = new Board(true, "board", 60, true);
		Player player = new Player(1);
		TreeMap<Character, Tile> tName = new TreeMap<Character, Tile>();
		HashMap<Integer, Tile> tID = b.getTiles();
		for(Integer id : tID.keySet())
			tName.put(tID.get(id).getChit().name(), tID.get(id));
		BoardDrawer.drawBoard(b);
		
		while (true) {
			System.out.print(player.id() + "~: ");
			while(!in.hasNextLine()) {}
			String[] input = in.nextLine().split(" ");
			switch(input[0]) {
			case "exit": 
				System.out.println("closing...");
				in.close();
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
	
	//////////////////////////////////////////////////////////////////////////
	
	public static boolean isLegalConfig(String fileName) {
		
		Scanner in;
		try {
			in = new Scanner(new File("" + fileName + ".cfg"));
		} catch (FileNotFoundException e) {
			return false;
		}
		int i;
		
		//READING GAME TYPE
		if(in.hasNextLine()) {
			switch(in.nextLine()) {
				case "simulation":
					gameType = true;
					break;
				case "testing":
					break;
				default:
					return false;
			}
		}
		else
			return false;
		//READING NUMBER OF P/H/C
		if(in.hasNextLine()) {
			try {
				i = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				return false;
			}
			if(i < 1 || i > 4)
				return false;
			else
				numPlayers = i;
		}
		else
			return false;
		if(in.hasNextLine()) {
			try {
				i = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				return false;
			}
			if(i < 0 || i > numPlayers)
				return false;
			else
				numHumans = i;
		}
		else
			return false;
		if(in.hasNextLine()) {
			try {
				i = Integer.parseInt(in.nextLine());
			} catch (NumberFormatException e) {
				return false;
			}
			if(numPlayers - numHumans != i)
				return false;
			else
				numCPU = i;
		}
		else
			return false;
		//READING CPU STRATEGIES
		stratFiles = new String[numCPU];
		for(i = 0; i < numCPU; i++) {
			if(in.hasNextLine()) {
				stratFiles[i] = in.nextLine();
			}
			else
				return false;
		}
		if(gameType) {
			//NUMBER OF EVOLUTIONS/EVOLUTION TIME (SIMULATION ONLY)
			if(in.hasNextLine()) {
				try {
					i = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					return false;
				}
				if(i < 1 || i > 1000)
					return false;
				else
					numEvos = i;
			}
			else
				return false;
			if(in.hasNextLine()) {
				try {
					i = Integer.parseInt(in.nextLine());
				} catch (NumberFormatException e) {
					return false;
				}
				if(i < 1 || i > 100)
					return false;
				else
					evoTime = i;
			}
			else
				return false;
		}
		else {
			//TRANSCRIPE/TRACK IMAGE (TESTING ONLY)
			if(in.hasNextLine()) {
				switch(in.nextLine()) {
					case "transcribe":
						if(in.hasNextLine()) {
							String s = in.nextLine();
							if(Pattern.matches(".*[.].*", s.substring(0)))
								return false;
							else
								transcript = s;
						}
						else
							return false;
						break;
					case "no transcript":
						break;
					default:
						return false;
				}
			}
			else
				return false;
			if(in.hasNextLine()) {
				switch(in.nextLine()) {
					case "track image":
						if(in.hasNextLine()) {
							String s = in.nextLine();
							if(Pattern.matches(".*[.].*", s.substring(0)))
								return false;
							else
								picture = s;
						}
						else
							return false;
						break;
					case "no tracking":
						break;
					default:
						return false;
				}
			}
			else
				return false;
		}
		return true;
	}
	
	public static boolean isLegalStrat(String fileName) {
		return true;
	}
	
	public static void makeConfig(String fileName) {
		
	}
	
	public static void makeStrat(String fileName) {
		
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
