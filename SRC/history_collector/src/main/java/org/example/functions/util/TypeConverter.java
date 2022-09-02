package org.example.functions.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class TypeConverter {

	private static final TypeConverter typeConverter = new TypeConverter();

	private TypeConverter() {
	}

	public static TypeConverter getTypeConverter() {
		return typeConverter;
	}

	public JSONObject convertStringToJSONObject(String string) {
		return new JSONObject(string);
	}

	public boolean canConvertJSONObject(String string) {
		return string.charAt(0) == '{' && string.charAt(string.length() - 1) == '}';
	}

	public boolean canConvertJSONArray(String string) {
		return string.charAt(0) == '[' && string.charAt(string.length() - 1) == ']';
	}

	public JSONArray convertStringToJSONArray(String string) {
		return new JSONArray(string);
	}

	public JSONArray coverJSONObjectWithJSONArray(JSONObject jsonObject) {
		JSONArray jsonArray = new JSONArray();
		return jsonArray.put(jsonArray);
	}
}
