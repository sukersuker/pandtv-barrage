package org.duxpqk.barrage;

public class StringUtils {

	public static String unicode2String(String value) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < value.length();) {
			if (value.charAt(i) == '\\' && value.charAt(i + 1) == 'u') {
				String unicode = value.substring(i + 2, i + 6);
				if (unicode.matches("[0-9a-fA-F]{4}")) {
					char ch = (char) Integer.parseInt(unicode, 16);
					builder.append(ch);
					i += 6;
				} else {
					builder.append("\\u");
                    i += 2;
				}
			} else {
				builder.append(value.charAt(i));
                i++;
			}
		}
		return builder.toString();
	}
}
