package org.example.functions.util;

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
}
