package org.example.functions.db_service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.example.functions.util.StringFormatter;
import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

public class QueryGenerator {

	private JSONObject jsonObjectConfig;
	private TypeConverter typeConverter = TypeConverter.getTypeConverter();
	private StringFormatter stringFormatter = new StringFormatter();

	public QueryGenerator(JSONObject jsonObjectConfig) {
		this.jsonObjectConfig = jsonObjectConfig;
	}

	public List<String> generateQueryList(Map<String, JSONArray> sourceDataMap) {
		List<String> queryList = new ArrayList<>();
		sourceDataMap.forEach((tableName, sourceData) -> queryList.add(generateBulkInsertQuery(tableName, sourceData)));
		return queryList;
	}

	private String generateBulkInsertQuery(String tableName, JSONArray sourceData) {
		Map<String, String> parameters = getParametersMap(tableName, sourceData);
		String queryTemplate = "INSERT INTO {{tableName}} ({{columns}}) SELECT * FROM (VALUES {{queryValues}}) tempName ({{columns}}) EXCEPT SELECT {{columns}} from {{tableName}}";
		return stringFormatter.bindParameters(queryTemplate, parameters).replace("''", "").replace("\n", "\\n");
	}

	private Map<String, String> getParametersMap(String tableName, JSONArray sourceData) {
		LinkedHashMap<String, JSONArray> metaDataLinkedHashMap = getMetaDataLinkedHashMap(tableName);
		return Map.of(
			"columns", getColumnNamesQueryString(metaDataLinkedHashMap),
			"queryValues", getQueryValueSets(sourceData, metaDataLinkedHashMap),
			"tableName", tableName);
	}

	private LinkedHashMap<String, JSONArray> getMetaDataLinkedHashMap(String tableName) {
		JSONObject targetObject = jsonObjectConfig.getJSONObject(tableName);
		JSONObject targetObjectMapping = targetObject.getJSONObject("mapping");

		LinkedHashMap<String, JSONArray> metaDataLinkedHashMap = new LinkedHashMap<>();
		Iterator<String> targetObjectMappingKeys = targetObjectMapping.keys();
		while (targetObjectMappingKeys.hasNext()) {
			String targetObjectMappingKey = targetObjectMappingKeys.next();
			metaDataLinkedHashMap.put(targetObjectMappingKey, targetObjectMapping.getJSONArray(targetObjectMappingKey));
		}
		return metaDataLinkedHashMap;
	}

	private String getColumnNamesQueryString(LinkedHashMap<String, JSONArray> metaDataLinkedHashMap) {
		List<String> columnList = new ArrayList<>();
		metaDataLinkedHashMap
			.forEach((key, value) -> columnList.add(value.getString(0)));
		return String.join(", ", columnList);
	}

	private String getQueryValueSets(JSONArray sourceData, LinkedHashMap<String, JSONArray> metaDataLinkedHashMap) {
		List<String> valueList = new ArrayList<>();
		for (int i = 0; i < sourceData.length(); i++) {
			JSONObject targetSource = sourceData.getJSONObject(i);
			valueList.add(getQueryValueSet(targetSource, metaDataLinkedHashMap));
		}
		return String.join(", ", valueList);
	}


	private String getQueryValueSet(JSONObject targetSource, LinkedHashMap<String, JSONArray> metaDataLinkedHashMap) {
		List<String> valueList = new ArrayList<>();
		metaDataLinkedHashMap
			.forEach((valuePointer, valueType) -> valueList.add(
				getValueWithPointer(targetSource, valuePointer, valueType.getString(1))));
		return String.format("(%s)", String.join(", ", valueList));
	}

	private String getValueWithPointer(JSONObject targetSource, String valuePointer, String valueType) {
		List<String> pointerList = Arrays.asList(valuePointer.split("\\."));
		JSONPointer.Builder builder = JSONPointer.builder();
		pointerList.forEach(builder::append);
		JSONPointer jsonPointer = builder.build();
		String result = String.valueOf(jsonPointer.queryFrom(targetSource));
		return typeConverter.getMssqlQueryString(result.replaceAll("'", "''"), valueType);
	}
}
