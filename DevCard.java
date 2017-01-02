public class DevCard {

	//Card Types
	public enum cardTypes {
		K,
		VP,
		RB,
		M,
		YOP;
	}
	
	//Identity
	private boolean used;
	private String card;
	
	//Constructor
	public DevCard(int i) {
		card = cardTypes.values()[i].toString();
		used = false;
	}
	
	//Identify
	public boolean used() {
		return used;
	}
	
	public void use() {
		used = true;
	}
	
	public String type() {
		return card;
	}
	
}
