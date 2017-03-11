import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import salil.resources.anim.Actor;
import salil.resources.anim.Button;
import salil.resources.anim.Stage;
import salil.resources.util.Random;


public class Board {

	//Structure
	private HashMap<Integer, Vertex> verticies;
	private HashMap<Integer, Tile> tiles;
	private int size;
	private String fileName;
	private boolean visual;
	
	private BufferedImage image;
	private Stage picture;
	private Button boardImage;
	
	//Constructor
	public Board(boolean standard, String fn, int s, boolean v) {

		fileName = fn;
		size = s;
		visual = v;
		
		//TERRAIN
		ArrayList<String> terrain = new ArrayList<String>(19);
		terrain.add("hill"); terrain.add("hill"); terrain.add("hill");
		terrain.add("mountain"); terrain.add("mountain"); terrain.add("mountain");
		terrain.add("field"); terrain.add("field"); terrain.add("field"); terrain.add("field");
		terrain.add("forest"); terrain.add("forest"); terrain.add("forest"); terrain.add("forest");
		terrain.add("pasture"); terrain.add("pasture"); terrain.add("pasture"); terrain.add("pasture");
		terrain.add("desert");
		terrain = Random.selectRandsNoRepeat(terrain);
		
		ArrayList<Integer> chitValues;
		if (standard) {
			//STANDARD CHITS
			Integer[] c = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 10, 9, 11};
			chitValues = new ArrayList<Integer>(Arrays.asList(c));
		}
		else {
			//RANDOM CHITS
			Integer[] c = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
			chitValues = Random.selectRandsNoRepeat(new ArrayList<Integer>(Arrays.asList(c)));
		}	
		
		//STANDARD TILE ID GENERATION
//		ArrayList<Integer> tileID = new ArrayList<Integer>();
//		int[] tileDelta = {9, 90, 99};
//		tileID.add(0);
//		for(int round = 0; round < 2; round++) {
//			int roundSize = tileID.size();
//			for(int i = 0; i < roundSize; i++) {
//				for(int j = 0; j < 3; j++) {
//					if (!tileID.contains(tileID.get(i) + tileDelta[j]))
//						tileID.add(tileID.get(i) + tileDelta[j]);
//					if (!tileID.contains(tileID.get(i) - tileDelta[j]))
//						tileID.add(tileID.get(i) - tileDelta[j]);
//				}
//			}
//		}
		
		//STANDARD TILE ID INITIALIZATION
		Integer[] t = {-180, -189, -198, -108, -18, 81, 180, 189, 198, 108, 18, -81, -90, -99, -9, 90, 99, 9, 0};
		ArrayList<Integer> tileID = new ArrayList<Integer>(Arrays.asList(t));
		
		//TILE SETUP
		tiles = new HashMap<Integer, Tile>();
		char name = 'A';
		for(Integer i : tileID) {
			String terr = terrain.remove(0);
			if(terr.equals("desert")) {
				Tile des = new Tile(new Chit(0, ' '), terr, i);
				des.setRobber(true);
				tiles.put(i, des);
			}
			else
				tiles.put(i, new Tile(new Chit(chitValues.remove(0), name++), terr, i));
		}
		
//		BufferedImage image = BoardDrawer.drawBoard(tiles, 60);
//		File boardPicture = new File("board.png");
//		try {
//			ImageIO.write(image, "png", boardPicture);
//		} catch (IOException e) {}
		
		//VERTEX SETUP
		verticies = new HashMap<Integer, Vertex>();
		int[] points = {-100, 1, -10, 100, -1, 10, -100};
		for(int i = 0; i < tileID.size(); i++) {
			int id = tileID.get(i);
			for(int rot = 0; rot < 6; rot++) {
				if(!verticies.containsKey(id + points[rot]))
					verticies.put(id + points[rot], new Vertex(id + points[rot]));
				tiles.get(id).addVertex(verticies.get(id + points[rot]));
			}
			for(int rot = 0; rot < 6; rot++)
				Vertex.connect(verticies.get(id + points[rot]), verticies.get(id + points[rot+1]));
		}
		if (visual) {
			initializeBoard();
		}
	}
	
	//Gameplay
	public void initializeBoard() {
		image = BoardDrawer.drawBoard(this);
		try {
			boardImage = new Button();
			boardImage.setMyImage(image);
			boardImage.setMyHeight(image.getHeight());
			boardImage.setMyWidth(image.getWidth());
			boardImage.setDrawImage(true);
			picture = new Stage(0, 0, image.getHeight(), image.getWidth(), 10, "board");
			picture.addActor(boardImage);
			
			writeImage();
		} catch (IOException e) {}
	}
	
	public void buildSettlement(int id, Player p) throws Exception{
		int ownerID = verticies.get(id).owner().id();
		
		ArrayList<Vertex> adj = verticies.get(id).getAdjacent();
		ArrayList<Vertex> temp;
		for(Vertex v : adj) {
			temp = v.getAdjacent();
			for (Vertex w : temp)
				if (!v.equals(verticies.get(id)) && (v.owner().id() != 0))
					throw new Exception ("build error: you cannot build a settlement within 2 spaces of another settlement");
		}
		
		if (ownerID != 0)
			throw new Exception("build error: cannot build here, there is already a building here");
		p.buildSettlement(verticies.get(id));		//player pays cost, registers new settlement
		verticies.get(id).builtBy(p);				//vertex updated to be settled by Player p
		image = BoardDrawer.drawSettlement(image, id, size, p);
		if (visual) {
			try {
				writeImage();
			} catch (Exception e) {
				throw new Exception ("io error: image file not found");
			}
		}
	}
	
	public void buildCity(int id, Player p) throws Exception {
		int ownerID = verticies.get(id).owner().id();
		if (ownerID != p.id())
			throw new Exception ("build error: only the owner of a settlement can upgrade it");
		verticies.get(id).owner().buildCity(verticies.get(id));	//player pays cost, registers settlement --> city
		verticies.get(id).cityUp();								//vertex updated to city
		image = BoardDrawer.drawCity(image, id, size, p);
		if(visual) {
			try {
				writeImage();
			} catch (Exception e) {
				throw new Exception ("io error: image file not found");
			}
		}
	}
	
	public void buildRoad(int id1, int id2, Player p) throws Exception {
		int ownerID1  = verticies.get(id1).owner().id();
		int ownerID2  = verticies.get(id2).owner().id();
		
		Edge e = verticies.get(id1).getConnecting(verticies.get(id2));
		
		ArrayList<Edge> adj1 = verticies.get(id1).edges();
		ArrayList<Edge> adj2 = verticies.get(id2).edges();
		System.out.println("before " + adj1.size() + " " + adj2.size());
		adj1.remove(e);
		adj2.remove(e);
		System.out.println("after " + adj1.size() + " " + adj2.size());
		
		boolean adjRoad = false;
		for (Edge edge : adj1)
			adjRoad |= edge.owner().id() == p.id();
		for (Edge edge : adj2)
			adjRoad |= edge.owner().id() == p.id();
		if (e.owner().id() != 0)
			throw new Exception("build error: there is already a road here");		
		if (p.id() != ownerID1 && p.id() != ownerID2 && !adjRoad)
			throw new Exception("build error: you must own an adjacent settlement or road");
		//EDGE NOT ALREADY OWNED
		//V1 OR V2 IS OWNED BY PLAYER
		//ADJACENT ROADS OWNED BY PLAYER
		
		p.buildRoad(e);													//player pays costs, calculates longest road
		e.builtBy(p);
		image = BoardDrawer.drawRoad(image, id1, id2, size, p);
		if(visual) {
			try {
				writeImage();
			} catch (Exception ex) {
				throw new Exception ("io error: image file not found");
			}
		}
	}
	
	//Identify
	public HashMap<Integer, Vertex> getVerticies() {
		return verticies;
	}
	
	public HashMap<Integer, Tile> getTiles() {
		return tiles;
	}
	
	public int getSize() {
		return size;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setFileName(String fn) {
		fileName = fn;
	}
	
	public void writeImage() throws IOException {
		File boardPicture = new File(fileName + ".png");
		ImageIO.write(image, "png", boardPicture);
		boardImage.setMyImage(image);
	}
	
	public int owner(int id) {
		return verticies.get(id).owner().id();
	}
	
	public boolean isCity(int id) {
		return verticies.get(id).isCity();
	}
	
	@Override
	public String toString() {
		String s = "";
		Iterator<Integer> iter = verticies.keySet().iterator();
		while(iter.hasNext())
			s += verticies.get(iter.next()) + "\n";
		return s;
	}
	
}