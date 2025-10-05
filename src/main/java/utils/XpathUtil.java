package utils;

public class XpathUtil {
	
	public static String replaceXpath(String xpath, String... values) {
	    for (String val : values) {
	        xpath = xpath.replaceFirst("\\{.*?\\}", val);
	    }
	    return xpath;
	}

}
