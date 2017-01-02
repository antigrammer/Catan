
public class Chit {

	//Identity
	private int value;
	private char name;
	
	//Constructor
	public Chit(int v, char c) {
		setValue(v);
		setName(c);
	}

	//Identify
	public int value() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public char name() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name + "-" + value;
	}

}
