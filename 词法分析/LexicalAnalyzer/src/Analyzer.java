import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author zhongfang
 * 
 */
public class Analyzer {

	String temp1;
	LinkedList<Character> list1 = new LinkedList<Character>();
	LinkedList<Character> list2 = new LinkedList<Character>();
	BufferedWriter output;

	public Analyzer() {
		try {
			output = new BufferedWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getText() {                
		char a[];
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("input.txt"));
			String lString;
			while ((lString = bufferedReader.readLine()) != null) {
				temp1 = lString;
				temp1.replaceAll("\\s+", "");// 去掉一个以上的空白符，用一个空白代替
				a = temp1.toCharArray();
				for (char _char : a) {
					list1.offer(_char);
				}
				analysis();
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally { // 关闭资源
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (output != null)
				try {
					output.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
		}
	}

	public char readin() {              //读入操作：取出list1的第一个字符放入list2的末尾
		char a1 = list1.pollFirst();
		list2.offerLast(a1);
		return a1;
	}

	public void readout() {            //读出操作；取出list2的最后一个字符放回list1
		char a2 = list2.pollLast();
		list1.offerFirst(a2);
	}

	public String getFromList2() {       //过去list2里的内容以供输出
		String string = new String();
		for (int i = 0; i < list2.size(); i++) {
			char _char = list2.get(i);
			string += _char;
		}
		return string;
	}

	public void analysis() throws IOException {
		int state = 0;
		Token token = new Token();
		while (!list1.isEmpty()) {
			char c = readin();
			switch (state) {
			case 0:
				if (Character.isLetter(c))
					state = 1;
				else if (Character.isDigit(c))
					state = 3;
				else if (c == '+')
					state = 10;
				else if (c == '-')
					state = 14;
				else if (c == '*')
					state = 18;
				else if (c == '/')
					state = 21;
				else if (c == '%')
					state = 24;
				else if (c == '&')
					state = 27;
				else if (c == '|')
					state = 31;
				else if (c == '!')
					state = 35;
				else if (c == '^')
					state = 38;
				else if (c == '<')
					state = 41;
				else if (c == '>')
					state = 47;
				else if (c == '=')
					state = 57;
				else if (c == '?') {
					state = 60;
					readout();
				} else if (c == ':') {
					readout();
					state = 61;
				} else if (c == '[') {
					state = 62;
					readout();
				} else if (c == ']') {
					state = 63;
					readout();
				} else if (c == '(') {
					state = 64;
					readout();
				} else if (c == ')') {
					state = 65;
					readout();
				} else if (c == '.') {
					state = 66;
					readout();
				} else if (c == ',') {
					state = 67;
					readout();
				} else if (c == '{') {
					state = 68;
					readout();
				} else if (c == '}') {
					state = 69;
					readout();
				} else if (c == ';') {
					state = 70;
					readout();
				} else {
					if (c != ' ') {
						System.out.print("不能识别");
						output.write("不能识别");
						output.newLine();
						list2.clear();
						continue;
					}
				}
				break;
			case 1:
				if (Character.isLetter(c) || Character.isDigit(c))
					state = 1;
				else {
					readout();
					state = 2;
				}
				break;
			case 2: // 标识符或关键字类型
				readout();
				String string = getFromList2();
				if (IDType.isKeywords(string.trim())) { // 如果能在关键字表中找到，则是关键字类型
					token.setName(string.trim());
					token.setType("keyword");
					token.setLocation("_");
				} else { // 否则，是标识符类型
					token.setName(string.trim());
					if (IDType.findID(string.trim()) == -1)
						IDType.insertID(string.trim());
					token.setType("ID");
					int n = IDType.findID(string.trim());
					token.setLocation(Integer.toString(n));
				}
				token.display();
				output.write(token.toString());
				output.newLine();
				list2.clear();
				state = 0;
				break;
			case 3:
				if (Character.isDigit(c))
					state = 3;
				else if (c == '.')
					state = 4;
				else if (c == 'e' || c == 'E')
					state = 6;
				else {
					state = 9;
					readout();
				}
				break;
			case 4:
				if (Character.isDigit(c))
					state = 5;
				else {
					if (c != ' '){
						System.out.print("不能识别");
						output.write("不能识别");
						output.newLine();
						list2.clear();
						continue;
					}
				}
				break;
			case 5:
				if (c == 'e' || c == 'E')
					state = 6;
				else {
					state = 9;
					readout();
				}
				break;
			case 6:
				if (c == '+' || c == '-')
					state = 7;
				else if (Character.isDigit(c))
					state = 8;
				else {
					if (c != ' '){
						System.out.print("不能识别");
						output.write("不能识别");
						output.newLine();
						list2.clear();
						continue;
					}
				}
				break;
			case 7:
				if (Character.isDigit(c))
					state = 8;
				else {
					System.out.print("不能识别");
					output.write("不能识别");
					output.newLine();
					list2.clear();
					continue;
				}
				break;
			case 8:
				if (Character.isDigit(c))
					state = 8;
				else {
					state = 9;
					readout();
				}
				break;
			case 9: // 数值类型
				readout();
				String string1 = getFromList2();
				token.setName(string1.trim());
				if (NumType.findNum(string1.trim()) == -1)
					NumType.insertNum(string1.trim());
				token.setType("Num");
				int n2 = NumType.findNum(string1.trim());
				token.setLocation(Integer.toString(n2));
				token.display();
				output.write(token.toString());
				output.newLine();
				list2.clear();
				state = 0;
				break;
			case 10:
				if (Character.isDigit(c))
					state = 3;
				else if (c == '+') {
					state = 11;
					readout();
				} else if (c == '=') {
					state = 12;
					readout();
				} else {
					state = 13;
					readout();
				}
				break;
			case 11: // ++
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 12: // +=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 13: // +
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 14:
				if (Character.isDigit(c))
					state = 3;
				else if (c == '-') {
					state = 15;
					readout();
				} else if (c == '=') {
					state = 16;
					readout();
				} else {
					state = 17;
					readout();
				}
				break;
			case 15: // --
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 16: // -=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 17: // -
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 18:
				if (c == '*') {
					state = 19;
					readout();
				} else {
					state = 20;
					readout();
				}
				break;
			case 19: // *=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 20: // *
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 21:
				if (c == '=') {
					state = 22;
					readout();
				} else {
					state = 23;
					readout();
				}
				break;
			case 22: // /=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 23: // /
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 24:
				if (c == '=') {
					state = 25;
					readout();
				} else {
					state = 26;
					readout();
				}
				break;
			case 25: // %=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 26: // %
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 27:
				if (c == '&') {
					state = 28;
					readout();
				} else if (c == '=') {
					state = 29;
					readout();
				} else {
					state = 30;
					readout();
				}
				break;
			case 28: // &&
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 29: // &=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 30: // &
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 31:
				if (c == '|') {
					state = 32;
					readout();
				} else if (c == '=') {
					state = 33;
					readout();
				} else {
					state = 34;
					readout();
				}
				break;
			case 32: // ||
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 33: // |=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 34: // |
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 35:
				if (c == '=') {
					state = 36;
					readout();
				} else {
					state = 37;
					readout();
				}
				break;
			case 36: // !=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 37: // !
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 38:
				if (c == '=') {
					state = 39;
					readout();
				} else {
					state = 40;
					readout();
				}
				break;
			case 39: // ^=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 40: // ^
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 41:
				if (c == '=') {
					state = 42;
					readout();
				} else if (c == '<') {
					state = 43;
				} else {
					state = 46;
					readout();
				}
				break;
			case 42: // <=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 43:
				if (c == '=') {
					state = 44;
					readout();
				} else {
					state = 45;
					readout();
				}
				break;
			case 44: // <<=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 45: // <<
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 46: // <
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 47:
				if (c == '=') {
					state = 48;
					readout();
				} else if (c == '>')
					state = 49;
				else {
					state = 55;
					readout();
				}
				break;
			case 48: // >=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 49:
				if (c == '>')
					state = 50;
				else if (c == '=') {
					state = 53;
					readout();
				} else {
					state = 54;
					readout();
				}
				break;
			case 50:
				if (c == '=') {
					state = 51;
					readout();
				} else {
					state = 52;
					readout();
				}
				break;
			case 51: // >>>=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 52: // >>>
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 53: // >>=
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 54: // >>
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 55: // >
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 56:
				break;
			case 57:
				if (c == '=') {
					state = 58;
					readout();
				} else {
					state = 59;
					readout();
				}
				break;
			case 58: // ==
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 59: // =
				readout();
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 60: // ?
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				output.write(token.toString());
				output.newLine();
				token.display();
				state = 0;
				list2.clear();
				break;
			case 61: // :
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 62: // [
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 63: // ]
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 64: // (
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 65: // )
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 66: // .
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 67: // ,
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 68: // {
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 69: // }
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			case 70: // ;
				token.setName(getFromList2().trim());
				token.setType("_");
				token.setLocation("_");
				token.display();
				output.write(token.toString());
				output.newLine();
				state = 0;
				list2.clear();
				break;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Analyzer analyzer = new Analyzer();
		analyzer.getText();
	}
}
