package org.example.functions.db_service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class QueryGenerator {

	private JSONObject jsonObjectConfig;

	public QueryGenerator(JSONObject jsonObjectConfig) {
		this.jsonObjectConfig = jsonObjectConfig;
	}

	public List<String> generateQueryList(Map<String, JSONArray> sourceDataMap) {
		List<String> queryList = new ArrayList<>();
		sourceDataMap.forEach((tableName, sourceData) -> queryList.add(generateBulkInsertQuery(tableName, sourceData)));
		return queryList;
	}

	private String generateBulkInsertQuery(String tableName, JSONArray sourceData) {
		LinkedHashMap<String, String> metaDataLinkedHashMap = getMetaDataLinkedHashMap(tableName);
		String columns = getColumnNamesQueryString(metaDataLinkedHashMap);
		String queryValues = getQueryValues(sourceData, metaDataLinkedHashMap);
		return String.format("INSERT INTO %s (%s) VALUES %s", tableName, columns, queryValues);
	}

	private LinkedHashMap<String, String> getMetaDataLinkedHashMap(String tableName) {
		JSONObject targetObject = jsonObjectConfig.getJSONObject(tableName);
		JSONObject targetObjectMapping = targetObject.getJSONObject("mapping");
		return targetObjectMapping.toMap().entrySet()
			.stream()
			.collect(Collectors.toMap(Map.Entry::getKey,
				entry -> (String)(entry.getValue()),
				(oldValue, newValue) -> newValue,
				LinkedHashMap::new));
	}

	private String getColumnNamesQueryString(LinkedHashMap<String, String> metaDataLinkedHashMap) {
		List<String> columnList = new ArrayList<>();
		metaDataLinkedHashMap
			.forEach((key, value) -> columnList.add(value));
		return String.join(", ", columnList);
	}

	private String getQueryValues(JSONArray sourceData, LinkedHashMap<String, String> metaDataLinkedHashMap) {
		List<String> valueList = new ArrayList<>();
		for (int i = 0; i < sourceData.length(); i++) {
			JSONObject targetSource = sourceData.getJSONObject(i);
			valueList.add(getQueryValue(targetSource, metaDataLinkedHashMap));
		}
		return String.join(", ", valueList);
	}

	private String getQueryValue(JSONObject targetSource, LinkedHashMap<String, String> metaDataLinkedHashMap) {
		List<String> valueList = new ArrayList<>();
		metaDataLinkedHashMap.keySet()
			.forEach(valuePointer -> valueList.add(getValueWithPointer(targetSource, valuePointer)));
		return String.format("(%s)", String.join(", ", valueList));
	}

	private String getValueWithPointer(JSONObject targetSource, String valuePointer) {
		List<String> pointerList = Arrays.asList(valuePointer.split("\\."));
		Iterator<String> pointerIterator = pointerList.iterator();
		JSONObject currentNode = targetSource;
		String value = "";
		while (pointerIterator.hasNext()) {
			String point = pointerIterator.next();
			if (pointerIterator.hasNext())
				currentNode = currentNode.getJSONObject(point);
			else
				value = String.valueOf(currentNode.get(point));
		}
		return value;
	}
}
