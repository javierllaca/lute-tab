import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	static int numberStrings;
	static boolean convert;
	static Pattern pattern = Pattern.compile("[0-9][a-z]");

	public static String barline(int n) {
		String out = " \n";
		for (int i = 0; i < n; i++) {
			out += "|\n";
		}
		return out;
	}

	public static String parseDuration(String str) {
		String duration = " ";
		int number = Integer.parseInt(str.substring(str.indexOf("/") + 1).replace(".", ""));
		switch (number) {
			case 1: duration += "w"; break;
			case 2: duration += "h"; break;
			case 4: duration += "q"; break;
			case 8: duration += "e"; break;
			case 16: duration += "s"; break;
			case 32: duration += "t"; break;
		}
		duration += (str.charAt(str.length() - 1) == '.') ? "." : " ";
		return duration;
	}

	public static String match(ArrayList<String> matches, int i) {
		for (String match : matches) {
			if (i == (int) match.charAt(0) - '0') {
				return match;
			}
		}
		return null;
	}

	public static String parseNotes(String in) {
		String result = "";

		Matcher matcher = pattern.matcher(in);
		ArrayList<String> pairs = new ArrayList<String>();
		while (matcher.find()) {
			pairs.add(matcher.group());
		}

		for (int i = 0; i < numberStrings; i++) {
			String match = match(pairs, i + 1);
			if (match != null) {
				String fret = convert ? 
					Integer.toString((int) match.charAt(1) - 'a') : 
					Character.toString(match.charAt(1));
				result += "-" + fret + "-\n";
			}
			else {
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

		if (rows[1].length() < charLength) {
			return tab;
		}

		int index = rows[1].substring(0, charLength).lastIndexOf('|');
		for (String row : rows) {
			result += row.substring(0, index + 1) + "\n";
			remaining += row.substring(index) + "\n";
		}
		result += "\n";

		return result + splitRows(remaining, charLength);
	}

	public static String parseTab() {
		Scanner in = new Scanner(System.in);

		numberStrings = Integer.parseInt(in.nextLine());
		String barline = barline(numberStrings);
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
		return tab;
	}
		
	public static void main(String[] args) {
		if (args.length > 0 && args[0].charAt(1) == 'c') {
			convert = true;
		} else {
			convert = false;
		}
		
		String tab = parseTab();

		if (args.length > 1 && args[1].charAt(1) == 't') {
			if (args.length > 2) {
				System.out.println(splitRows(tab, Integer.parseInt(args[2])));
			} else {
				System.out.println(splitRows(tab, 80));
			}
		} else {
			System.out.println(tab);
		}
	}
}
