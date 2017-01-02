import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;

public class BoardDrawer {

	public static BufferedImage drawBoard(Board b, int s) {
		
		HashMap<Integer, Tile> tiles = b.getTiles();
		
		//SETUP IMAGE
		int height = 10 * s;
		int width = 2 * s + s * (int) Math.floor((double) Math.sqrt(3) * 5);
		int cx = width/2;
		int cy = height/2;
		BufferedImage board = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) board.getGraphics();
		g.setColor(new Color(0, 190, 255));
		g.fillRect(0, 0, width, height);
		
		//DRAW TILES
		Iterator<Integer> iter = tiles.keySet().iterator();
		while(iter.hasNext()) {
			int id = iter.next();
			int[] directions = directionalVectors(id);
			double[] delta = new double[2];
			delta[0] = (double) directions[0] - (double) directions[1]/2 - (double) directions[2]/2;
			delta[1] = (double) Math.sqrt(3) * (-directions[1])/2 + (double) Math.sqrt(3) * (directions[2])/2;	
			int dx = (int) ((double) delta[1] * s);
			int dy = (int) ((double) delta[0] * s);
			int x = cx + dx;
			int y = cy + dy;
			int[] xpoints = new int[6];
			int[] ypoints = new int[6];
			xpoints[0] = x; ypoints[0] = y - s;
			xpoints[1] = x + (int) ((double) s * Math.sqrt(3)/2); ypoints[1] = y - (int) ((double) s/2);
			xpoints[2] = x + (int) ((double) s * Math.sqrt(3)/2); ypoints[2] = y + (int) ((double) s/2);
			xpoints[3] = x; ypoints[3] = y + s;
			xpoints[4] = x - (int) ((double) s * Math.sqrt(3)/2); ypoints[4] = y + (int) ((double) s/2);
			xpoints[5] = x - (int) ((double) s * Math.sqrt(3)/2); ypoints[5] = y - (int) ((double) s/2);
			switch(tiles.get(id).getTerrain()) {
				case "hill": g.setColor(new Color(140, 70, 20)); break;
				case "mountain": g.setColor(new Color(105, 105, 105)); break;
				case "field": g.setColor(new Color(255, 210, 0)); break;
				case "forest": g.setColor(new Color(0, 100, 0)); break;
				case "pasture": g.setColor(new Color(150, 200, 70)); break;
				case "desert": g.setColor(new Color(220, 200, 75)); break;
				default: g.setColor(new Color(0, 190, 255)); break;
			}
			
			g.fillPolygon(xpoints, ypoints, 6);
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SERIF, Font.PLAIN, s/3));
			int c = tiles.get(id).getChit().value();
			if (c > 9)
				g.drawString(tiles.get(id).getChit().name() + "-" + c, x - s/4, y + s/8);
			else if (c > 0) {
				if (c == 6 || c == 8) {
					g.setColor(Color.RED);
					g.drawString(tiles.get(id).getChit().name() + "-" + c, x - s/4, y + s/8);
					g.setColor(Color.BLACK);
				}
				else
					g.drawString(tiles.get(id).getChit().name() + "-" + c, x - s/4, y + s/8);
			}
			g.setStroke(new BasicStroke(5));
            g.drawPolygon(xpoints, ypoints, 6);
            if (tiles.get(id).hasRobber()) {
            	g.setColor(Color.RED);
    			g.setStroke(new BasicStroke(2));
                g.drawLine(xpoints[5], ypoints[5], xpoints[2], ypoints[2]);
                g.drawLine(xpoints[1], ypoints[1], xpoints[4], ypoints[4]);
            }
            
		}
		
		return board;
		
	}
	
	public static int[] directionalVectors(int id) {
		
		//30 --> 1, 150 --> 10, 270 --> 100
		int[] directions = new int[3];
		directions[0] = (int) Math.round((double) id/100);
		id = id - directions[0] * 100;
		directions[1] = (int) Math.round((double) id/10);
		id = id - directions[1] * 10;
		directions[2] = (int) Math.round((double) id/1);
		id = id - directions[2] * 1;
		return directions;
		
	}
	
}
