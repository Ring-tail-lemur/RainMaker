package org.example.functions.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.functions.util.StringFormatter;
import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryGenerator {

	private final JSONObject jsonObjectConfig;
	private final TypeConverter typeConverter = TypeConverter.getTypeConverter();
	private final StringFormatter queryStringFormatter = new StringFormatter();

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
		if (parameters.get("queryValues").equals(""))
			return "";
		String queryTemplate = "INSERT INTO {{tableName}} ({{columns}}) SELECT * FROM (VALUES {{queryValues}}) tempName ({{columns}}) EXCEPT SELECT {{columns}} from {{tableName}}";
		return queryStringFormatter.bindParameters(queryTemplate, parameters).replace("''", "").replace("\n", "\\n");
	}

	private Map<String, String> getParametersMap(String tableName, JSONArray sourceData) {
		LinkedHashMap<String, JSONArray> metaDataLinkedHashMap = getMetaDataLinkedHashMap(tableName);
		return Map.of(
			"columns", getColumnNamesQueryString(metaDataLinkedHashMap),
			"queryValues", getQueryValuesString(sourceData, metaDataLinkedHashMap),
			"tableName", getPureTableName(tableName));
	}

	private String getPureTableName(String tableName) {
		StringFormatter filedStringFormatter = new StringFormatter("[", "]");
		return filedStringFormatter.removeStartingSubstitutor(tableName);
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

	private String getQueryValuesString(JSONArray sourceData, LinkedHashMap<String, JSONArray> metaDataLinkedHashMap) {
		List<String> valueList = new ArrayList<>();
		for (int i = 0; i < sourceData.length(); i++) {
			JSONObject targetSource = sourceData.getJSONObject(i);
			valueList.add(getSingleQueryValueString(targetSource, metaDataLinkedHashMap));
		}
		return String.join(", ", valueList);
	}

	private String getSingleQueryValueString(JSONObject targetSource,
		LinkedHashMap<String, JSONArray> metaDataLinkedHashMap) {
		List<String> valueList = new ArrayList<>();
		metaDataLinkedHashMap
			.forEach((valuePointer, valueType) -> valueList.add(
				getFiledValue(targetSource, valuePointer, valueType.getString(1))));
		return String.format("(%s)", String.join(", ", valueList));
	}

	private String getFiledValue(JSONObject targetSource, String valuePointer, String valueType) {
		if (valuePointer.startsWith("[default]")) {
			return typeConverter.getMssqlQueryString(valuePointer.replace("[default]", ""), "string");
		}
		return getValueWithPointer(targetSource, valuePointer, valueType);
	}

	private String getValueWithPointer(JSONObject targetSource, String valuePointer, String valueType) {
		List<String> pointerList = Arrays.asList(valuePointer.split("\\."));
		JSONPointer.Builder builder = JSONPointer.builder();
		pointerList.forEach(builder::append);
		JSONPointer jsonPointer = builder.build();
		String result;
		try {
			Optional<String> optionalResult = Optional.of(String.valueOf(jsonPointer.queryFrom(targetSource)));
			result = optionalResult.orElse("null");
		} catch (Exception e) {
			result = "null";
		}
		return typeConverter.getMssqlQueryString(
			result.replaceAll("'", "''").replace(System.getProperty("line.separator"), ""), valueType);
	}
}
