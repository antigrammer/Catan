import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import salil.resources.util.Random;

public class Test {

//	public static void main(String[] args) {
//		
//		//TERRAIN
//		ArrayList<String> terrain = new ArrayList<String>(19);
//		terrain.add("hill"); terrain.add("hill"); terrain.add("hill");
//		terrain.add("mountain"); terrain.add("mountain"); terrain.add("mountain");
//		terrain.add("field"); terrain.add("field"); terrain.add("field"); terrain.add("field");
//		terrain.add("forest"); terrain.add("forest"); terrain.add("forest"); terrain.add("forest");
//		terrain.add("pasture"); terrain.add("pasture"); terrain.add("pasture"); terrain.add("pasture");
//		terrain.add("desert");
//		terrain = Random.selectRandsNoRepeat(terrain);
//		
//		//STANDARD CHITS
//		Integer[] c = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 10, 9, 11};
//		ArrayList<Integer> chitValues = new ArrayList<Integer>(Arrays.asList(c));
//		//RANDOM CHITS
////		Integer[] c = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
////		ArrayList<Integer> chitValues = Random.selectRandsNoRepeat(new ArrayList<Integer>(Arrays.asList(c)));
//		
//		//STANDARD TILE ID GENERATION
////		ArrayList<Integer> tileID = new ArrayList<Integer>();
////		int[] tileDelta = {9, 90, 99};
////		tileID.add(0);
////		for(int round = 0; round < 2; round++) {
////			int roundSize = tileID.size();
////			for(int i = 0; i < roundSize; i++) {
////				for(int j = 0; j < 3; j++) {
////					if (!tileID.contains(tileID.get(i) + tileDelta[j]))
////						tileID.add(tileID.get(i) + tileDelta[j]);
////					if (!tileID.contains(tileID.get(i) - tileDelta[j]))
////						tileID.add(tileID.get(i) - tileDelta[j]);
////				}
////			}
////		}
//		
//		//STANDARD TILE ID INITIALIZATION
//		Integer[] t = {-180, -189, -198, -108, -18, 81, 180, 189, 198, 108, 18, -81, -90, -99, -9, 90, 99, 9, 0};
//		ArrayList<Integer> tileID = new ArrayList<Integer>(Arrays.asList(t));
//		
//		//TILE SETUP
//		HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();
//		char name = 'A';
//		for(Integer i : tileID) {
//			String terr = terrain.remove(0);
//			if(terr.equals("desert")) {
//				Tile des = new Tile(new Chit(0, ' '), terr);
//				des.setRobber(true);
//				tiles.put(i, des);
//			}
//			else
//				tiles.put(i, new Tile(new Chit(chitValues.remove(0), name++), terr));
//		}
//		
//		//VERTEX SETUP
//		HashMap<Integer, Vertex> verticies = new HashMap<Integer, Vertex>();
//		int[] points = {-100, 1, -10, 100, -1, 10, -100};
//		for(int i = 0; i < tileID.size(); i++) {
//			int id = tileID.get(i);
//			for(int rot = 0; rot < 6; rot++) {
//				if(!verticies.containsKey(id + points[rot]))
//					verticies.put(id + points[rot], new Vertex(id + points[rot]));
//				tiles.get(id).addVertex(verticies.get(id + points[rot]));
//			}
//			for(int rot = 0; rot < 6; rot++)
//				Vertex.connect(verticies.get(id + points[rot]), verticies.get(id + points[rot+1]));
//		}
//		
//		Iterator<Integer> iter = verticies.keySet().iterator();
//		while(iter.hasNext()) {
//			System.out.println(verticies.get(iter.next()));
//		}
//		
//	}
	
}
