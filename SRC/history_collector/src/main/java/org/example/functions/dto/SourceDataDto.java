package org.example.functions.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.util.StringFormatter;
import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

import lombok.Getter;

@Getter
public class SourceDataDto {

	private String dataName;
	private JSONArray responseArray;
	private JSONObject dataLoadConfig;//mapping 객체가 들어감
	private LinkedHashMap<String, JSONArray> dataLoadConfigLinkedHashMap = new LinkedHashMap<>();
	private List<List<String>> entityList = new ArrayList<>();

	public SourceDataDto(JSONArray responseArray, DataExtractingConfigDto dataExtractingConfigDto) {
		this.dataName = dataExtractingConfigDto.getDataName();
		this.dataLoadConfig = dataExtractingConfigDto.getDataLoadConfig();
		this.responseArray = responseArray;
		setDataLoadConfigLinkedHashMap();
		setEntityList();
	}

	public boolean isEmpty() {
		return responseArray.isEmpty();
	}

	public String getPureDataName() {
		StringFormatter filedStringFormatter = new StringFormatter("[", "]");
		return filedStringFormatter.removeStartingSubstitutor(dataName);
	}

	public List<String> getColumnList() {
		List<String> columnList = new ArrayList<>();
		dataLoadConfigLinkedHashMap.forEach((key, value) -> columnList.add(value.getString(0)));
		return columnList;
	}

	public String getSqlFormatEntityList() {
		return entityList.stream()
			.map(entity -> "(" + String.join(", ", entity) + ")")
			.collect(Collectors.joining(", "));
	}

	private void setDataLoadConfigLinkedHashMap() {
		Iterator<String> targetObjectMappingKeys = dataLoadConfig.keys();
		while (targetObjectMappingKeys.hasNext()) {
			String targetObjectMappingKey = targetObjectMappingKeys.next();
			dataLoadConfigLinkedHashMap.put(targetObjectMappingKey, dataLoadConfig.getJSONArray(targetObjectMappingKey));
		}
	}

	private void setEntityList() {
		for (int i = 0; i < responseArray.length(); i++) {
			JSONObject targetSource = responseArray.getJSONObject(i);
			entityList.add(getEntity(targetSource));
		}
	}

	private List<String> getEntity(JSONObject targetSource) {
		List<String> entity = new ArrayList<>();
		dataLoadConfigLinkedHashMap
			.forEach((valuePointer, valueType) ->
				entity.add(findValue(targetSource, valuePointer, valueType.getString(1))));
		return entity;
	}

	private String findValue(JSONObject targetSource, String valuePointer, String valueType) {
		if (valuePointer.startsWith("[default]")) {
			return TypeConverter.getTypeConverter()
				.getMssqlQueryString(valuePointer.replace("[default]", ""), "string");
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
			result = optionalResult.orElseThrow(() -> new Exception("해당 원소가 없습니다."));
			if (result.equals("")) {
				result = "";
			}
		} catch (Exception e) {
			return "null";
		}
		String test = result.replaceAll("'", "''").replace(System.getProperty("line.separator"), "");
		return TypeConverter.getTypeConverter().getMssqlQueryString(
			test, valueType);
	}
}
