package it.eng.stamp.util;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONParser {
	private static Logger LOGGER = Logger.getLogger(JSONParser.class.getName());
	private Gson parser = new Gson();

	private static final JSONParser _instance = new JSONParser();

	private JSONParser() {
	}

	public static JSONParser get() {
		return _instance;
	}

	public <T> T parseMapping(String s, Class<T> classOfT) {
		LOGGER.info("[JSONParser] Parsing report for class " + classOfT.getName());
		T result;
		try {
			result = parser.fromJson(s, classOfT);
			if (result == null)
				LOGGER.warning("[JSONParser] Error parsing file. Are you sure it is not empty?");
			return result;
		} catch (JsonSyntaxException e) {
			LOGGER.warning("[JSONParser] Error parsing file. Are you sure it was generated with METHOD option?");
			return null;
		}

	}

}