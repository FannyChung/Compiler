/**
 * 
 */

/**
 * @author zhongfang
 * 
 */
public class Token {

	private String name;
	private String type;
	private String location;

	@Override
	public String toString() {
		String string = "< " + this.name + " , " + this.type + " , "
				+ this.location + " >";
		return string;
	}

	public void display() {
		System.out.println("< " + this.name + " , " + this.type + " , "
				+ this.location + " >");
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * 
	 */
	public Token() {
		// TODO Auto-generated constructor stub
	}

}
