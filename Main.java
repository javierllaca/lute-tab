import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	static int stringNumber;

	public static String barline(int n) {
		String out = " \n";
		for (int i = 0; i < n; i++) {
			out += "|\n";
		}
		return out;
	}

	public static String parseDuration(String duration) {
		String[] s = duration.split("[/ ]");
		String out = "";
		out += 	s[1].equals("1") ? "w" : 
			s[1].equals("2") ? "h" : 
			s[1].equals("4") ? "q" : 
			s[1].equals("8") ? "e" : 
			s[1].equals("16") ? "s" : 
			s[1].equals("32") ? "t" : " ";
		out += (s.length > 2 && s[2].equals(".")) ? "." : " ";
		return out;
	}

	public static String block(String in) {
		String out = "";
		Pattern pattern = Pattern.compile("[0-9][a-p]");
		Matcher matcher = pattern.matcher(in);
		ArrayList<String> toks = new ArrayList<String>();
		while (matcher.find()) {
			toks.add(matcher.group());
		}
		
		out += " ";
		String[] test = in.split("\t");
		if (test.length > 1) {
			out += parseDuration(test[1]);	
		}
		else {
			out += "  ";
		}
		out += "\n";

		for (int i = 0; i < stringNumber; i++) {
			boolean empty = true;
			for (String s : toks) {
				if ((int) s.charAt(0) - '0' == i + 1) {
					out += "-" + Integer.toString((int) s.charAt(1) - 'a') + "-\n";
					empty = false;
				}
			}
			if (empty) {
				out += "---\n";
			}
		}
		return out;
	}

	public static String sum(String s1, String s2) {
		String[] s1tok = s1.split("\n");
		String[] s2tok = s2.split("\n");
		String out = "";
		for (int i = 0; i < s1tok.length; i++) {
			if (i < s1tok.length) {
				out += s1tok[i];
			}
			if (i < s2tok.length) {
				out += s2tok[i];
			}
			out += "\n";
		}
		return out;
	}

	public static String separate(String s, int charLength) {
		String[] toks = s.split("\n");
		String out = "";
		String rec = "";
		if (toks[1].length() < charLength) {
			return s;
		}
		int index = toks[1].indexOf("|", charLength);
		for (String tok : toks) {
			out += tok.substring(0, index + 1) + "\n";
			rec += tok.substring(index) + "\n";
		}
		out += "\n";
		return out + separate(rec, charLength);
	}
		
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		stringNumber = Integer.parseInt(in.nextLine());
		String barline = barline(stringNumber);
		String total = barline;
		while (in.hasNext()) {
			String line = in.nextLine();
			if (line.charAt(0) == ',') {
				total = sum(total, barline);
			}
			else {
				total = sum(total, block(line));
			};
		}
		System.out.println(separate(total, 80));
	}
}
