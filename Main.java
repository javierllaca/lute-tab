import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	static int stringNumber;
	static boolean convert;

	public static String barline(int n) {
		String out = " \n";
		for (int i = 0; i < n; i++) {
			out += "|\n";
		}
		return out;
	}

	public static String parseDuration(String str) {
		String out = " ";
		int number = Integer.parseInt(str.substring(str.indexOf("/") + 1).replace(".", ""));
		switch (number) {
			case 1: out += "w"; break;
			case 2: out += "h"; break;
			case 4: out += "q"; break;
			case 8: out += "e"; break;
			case 16: out += "s"; break;
			case 32: out += "t"; break;
		}
		out += (str.charAt(str.length() - 1) == '.') ? "." : " ";
		return out;
	}

	public static String parseNotes(String in) {
		String result = "";

		Pattern pattern = Pattern.compile("[0-9][a-z]");
		Matcher matcher = pattern.matcher(in);
		ArrayList<String> pairs = new ArrayList<String>();
		while (matcher.find()) {
			pairs.add(matcher.group());
		}

		for (int i = 0; i < stringNumber; i++) {
			boolean activeString = false;
			for (String pair : pairs) {
				if (i + 1 == (int) pair.charAt(0) - '0') {
					activeString = true;
					String fret = convert ? 
						Integer.toString((int) pair.charAt(1) - 'a') : 
						Character.toString(pair.charAt(1));
					result += "-" + fret + "-\n";
				}
			}
			if (!activeString) {
				result += "---\n";
			}
		}
		return result;
	}

	public static String tabColumn(String in) {
		String[] params = in.split("\t");
		String duration = (params.length > 1) ? parseDuration(params[1]) : "   ";
		return duration + "\n" + parseNotes(in);
	}

	public static String mergeTabs(String tab1, String tab2) {
		String[] rows1 = tab1.split("\n");
		String[] rows2 = tab2.split("\n");
		String sum = "";
		for (int i = 0; i < rows1.length; i++) {
			if (i < rows1.length) {
				sum += rows1[i];
			}
			if (i < rows2.length) {
				sum += rows2[i];
			}
			sum += "\n";
		}
		return sum;
	}

	public static String splitRows(String tab, int charLength) {
		String[] rows = tab.split("\n");
		String result = "", remaining = "";

		if (rows[1].length() <= charLength) {
			return tab;
		}

		int index = rows[1].indexOf("|", charLength);
		for (String row : rows) {
			result += row.substring(0, index + 1) + "\n";
			remaining += row.substring(index) + "\n";
		}
		result += "\n";

		return result + splitRows(remaining, charLength);
	}
		
	public static void main(String[] args) {
		if (args.length > 0 && args[0].charAt(1) == 'c') {
			convert = true;
		} else {
			convert = false;
		}
		
		Scanner in = new Scanner(System.in);

		stringNumber = Integer.parseInt(in.nextLine());
		String barline = barline(stringNumber);
		String tab = barline;

		while (in.hasNext()) {
			String line = in.nextLine();
			if (line.charAt(0) == ',') {
				tab = mergeTabs(tab, barline);
			}
			else {
				tab = mergeTabs(tab, tabColumn(line));
			}
		}
		System.out.println(splitRows(tab, 70));
	}
}
