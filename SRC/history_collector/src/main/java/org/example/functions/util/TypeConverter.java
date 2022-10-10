package org.example.functions.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		return jsonArray.put(jsonObject);
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
			case "string":
			case "varchar":
			case "text":
				return convertToQueryString(value);

			case "uniqueidentifier":
				return convertToQueryUniqueidentifier(value);

			case "bit":
			case "bool":
			case "boolean":
				return convertToQueryBit(value);

			case "datetime2":
			case "time":
			case "datetime":
				return convertToQueryDatetime2(value);

			default:
				return convertToQueryBigint(value);
		}
	}

	public JSONArray getNormalizedJsonArray(String sourceData) {
		return normalizeJsonObject(coverJSONObjectWithJSONArray(convertStringToJSONObject(sourceData)));
	}

	public JSONArray normalizeJsonObject(JSONArray sourceData) {

		JSONArray normalizedSourceData = new JSONArray("[]");
		if (!sourceData.isEmpty()) {
			for (Object sourceDatum : sourceData) {
				List<JSONObject> treeList = new ArrayList<>();
				if (normalizeJsonObject(sourceDatum, (JSONObject)sourceDatum, treeList, null, null)) {
					normalizedSourceData.put(sourceDatum);
				} else {
					normalizedSourceData.putAll(treeList);
				}
			}
		}
		return normalizedSourceData;
	}


	private boolean normalizeJsonObject(Object node, JSONObject tree, List<JSONObject> treeList, String key,
		JSONObject lastJsonObject) {
		if (node instanceof JSONObject) {
			JSONObject jsonObjectNode = (JSONObject)node;
			Iterator<String> nextNodeKeys = jsonObjectNode.keys();
			boolean requireMerge = true;
			while (nextNodeKeys.hasNext()) {
				String nextNodeKey = nextNodeKeys.next();
				Object nextNode = jsonObjectNode.get(nextNodeKey);
				requireMerge =
					requireMerge && normalizeJsonObject(nextNode, tree, treeList, nextNodeKey, jsonObjectNode);
				if(!requireMerge)
					break;
			}
			return requireMerge;
		} else if (node instanceof JSONArray) {
			JSONArray jsonArrayNode = (JSONArray)node;
			for (Object nodeElement : jsonArrayNode) {
				lastJsonObject.remove(key);
				lastJsonObject.put(key, nodeElement);
				boolean requireMerge = normalizeJsonObject(nodeElement, tree, treeList, key, lastJsonObject);
				if (requireMerge) {
					treeList.add(new JSONObject(tree.toString()));
				}
			}
			return false;
		} else {
			return true;
		}
	}
}
