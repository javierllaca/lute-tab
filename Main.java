import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	static int stringNumber;

	public static String barline(int n) {
		String out = "";
		for (int i = 0; i < n; i++) {
			out += "|\n";
		}
		return out;
	}

	public static String note(String in) {
		String out = "";
		Pattern pattern = Pattern.compile("[0-9][a-p]");
		Matcher matcher = pattern.matcher(in);
		ArrayList<String> toks = new ArrayList<String>();
		while (matcher.find()) {
			toks.add(matcher.group());
		}

		for (int i = 0; i < stringNumber; i++) {
			boolean empty = true;
			for (String s : toks) {
				if ((int) s.charAt(0) - '0' == i + 1) {
					out += Integer.toString((int) s.charAt(1) - 'a') + "\n";
					empty = false;
				}
			}
			if (empty) {
				out += "-\n";
			}
		}
		return out;
	}

	public static String sum(String s1, String s2) {
		String[] s1tok = s1.split("\n");
		String[] s2tok = s2.split("\n");
		String out = "";
		for (int i = 0; i < stringNumber; i++) {
			if (i < s1tok.length) {
				out += s1tok[i];
			}
			out += "-";
			if (i < s2tok.length) {
				out += s2tok[i];
			}
			out += "\n";
		}
		return out;
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
				total = sum(total, note(line));
			};
		}
		System.out.println(total);
	}
}
