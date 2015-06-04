import java.util.Vector;

/**
 * 
 */

/**
 * @author zhongfang
 * 
 */
public class IDType {

	private static int numOfID = 0;
	private static Vector<String> iDs = new Vector<>();
	private final static String keywords[] = { "abstract", "assert", "boolean",
			"break", "byte", "case", "catch", "char", "class", "const",
			"continue", "default", "do", "double", "else", "enum", "extends",
			"final", "finally", "float", "for", "goto", "if", "implements",
			"import", "instanceof", "int", "interface", "long", "native",
			"new", "package", "private", "protected", "public", "return",
			"short", "static", "strictfp", "super", "switch", "synchronized",
			"this", "thorw", "throws", "transient", "try", "void", "volatile",
			"while" };

	/**
	 * 
	 */
	public IDType() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isKeywords(String s) {
		for (String string : keywords)
			if (s.equals(string))
				return true;
		return false;
	}

	public static void insertID(String string) {
		iDs.addElement(string);
		numOfID++;
	}

	public static int findID(String s) {
		return iDs.indexOf(s);
	}

	/**
	 * @return the numOfID
	 */
	public static int getNumOfID() {
		return numOfID;
	}

}
