package cz.osslite.common.utility;

import java.text.Normalizer;

public class StringNormalizer {

	public static String stripAccents(String input) {
		if (input == null) return null;
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		normalized = normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return normalized;
	}

}
