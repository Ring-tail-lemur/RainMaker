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

	private String convertToQueryString(String rawString) {
		return String.format("'%s'", rawString);
	}

	private String convertToQueryDatetime2(String rawString) {
		return String.format("convert(datetime2, '%s',126)", rawString);
	}

	private String convertToQueryUniqueidentifier(String rawString) {
		return convertToQueryString(rawString);
	}

	private String convertToQueryBit(String rawString) {
		return rawString.equals("true") ? "1" : "0";
	}

	private String convertToQueryBigint(String rawString) {
		return rawString;
	}

	public String getMssqlQueryString(JSONArray valueArray) {
		String valueString = valueArray.getString(0);
		String valueType = valueArray.getString(1);
		return getMssqlQueryString(valueString, valueType);
	}

	public String getMssqlQueryString(String value, String valueType) {
		switch (valueType.toLowerCase()) {
			case "string": case "varchar": case "text":
				return convertToQueryString(value);

			case "uniqueidentifier":
				return convertToQueryUniqueidentifier(value);

			case "bit": case "bool": case "boolean":
				return convertToQueryBit(value);

			case "datetime2": case "time":
				return convertToQueryDatetime2(value);

			default:
				return convertToQueryBigint(value);
		}
	}
}
