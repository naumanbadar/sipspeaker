package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractor {

	public static String extract(String string, String reg1, String reg2) {
		int startIndex, endIndex;
		Pattern pattern1 = Pattern.compile(reg1);
		Pattern pattern2 = Pattern.compile(reg2);
		Matcher matcher = pattern1.matcher(string);
		if (!matcher.find()) {
			return "";
		}
		startIndex = matcher.start();
		matcher = pattern2.matcher(string);
		if (!matcher.find()) {
			return "";
		}
		endIndex = matcher.start();
		return string.substring(startIndex, endIndex);
	}
}
