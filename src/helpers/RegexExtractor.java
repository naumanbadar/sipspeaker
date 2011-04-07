package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nauman Badar <nauman.gwt@gmail.com>
 * @created Apr 7, 2011
 *
 */
public class RegexExtractor {

	/**
	 * @param string
	 * @param reg1
	 * @param reg2
	 * @param includeHead
	 * @return
	 */
	public static String extract(String string, String reg1, String reg2, boolean includeHead) {
		int startIndex, endIndex;
		Pattern pattern1 = Pattern.compile(reg1);
		Pattern pattern2 = Pattern.compile(reg2);
		Matcher matcher = pattern1.matcher(string);
		if (!matcher.find()) {
			return "";
		}
		startIndex = matcher.start();
		int groupLength=matcher.group().length();
		matcher = pattern2.matcher(string.substring(startIndex+groupLength));
		if (!matcher.find()) {
			return "";
		}
		endIndex = matcher.start();
		if (!includeHead) {
			return string.substring(startIndex+groupLength, startIndex+groupLength+endIndex);
		}
		return string.substring(startIndex, startIndex+groupLength+endIndex);
	}
}
