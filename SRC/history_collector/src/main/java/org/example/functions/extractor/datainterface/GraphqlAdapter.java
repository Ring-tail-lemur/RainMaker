package org.example.functions.extractor.datainterface;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.datainterface.transporter.HttpRequestSender;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;

public class GraphqlAdapter implements DataSourceAdapter {

	private static final GraphqlAdapter graphqlAdapter = new GraphqlAdapter();
	private final HttpRequestSender httpRequestSender = HttpRequestSender.getInstance();

	private GraphqlAdapter() {
	}

	public static GraphqlAdapter getInstance() {
		return graphqlAdapter;
	}

	@Override
	public boolean isAdapted(AdapterType interfaceType) {
		return interfaceType == AdapterType.GRAPHQL;
	}

	@Override
	public JSONArray send(DataExtractingConfigDto dataExtractingConfigDto) throws
		IOException, ResponseTypeMissMatchException {
		if (dataExtractingConfigDto.hasRequestVariable()) {
			return dataExtractingConfigDto.getAllHttpRequestDto()
				.parallelStream()
				.map(httpRequestDto -> sendAllPage(dataExtractingConfigDto, httpRequestDto))
				.reduce(JSONArray::putAll)
				.orElseGet(JSONArray::new);
		}
		HttpRequestDto httpRequestDto = dataExtractingConfigDto.getHttpRequestDto();
		return sendAllPage(dataExtractingConfigDto, httpRequestDto);
	}

	private JSONArray sendAllPage(DataExtractingConfigDto dataExtractingConfigDto, HttpRequestDto httpRequestDto) {
		if (dataExtractingConfigDto.hasPagination()) {
			JSONArray result = new JSONArray();
			String hasNextPage = "true";
			String endCursor = null;
			while (hasNextPage.equals("true")) {
				JSONArray response = httpRequestSend(httpRequestDto.getPaginatedDto(endCursor));
				result.putAll(response);
				Map<String, String> paginationInfo = dataExtractingConfigDto.getPaginationInfo();
				hasNextPage = responseHasNextPage(response, paginationInfo);
				endCursor = getNextPage(response, paginationInfo);
			}
			return result;
		}
		return httpRequestSend(httpRequestDto);
	}

	private String responseHasNextPage(JSONArray response, Map<String, String> paginationInfo) {
		if (response.isEmpty()) {
			return "false";
		}
		return getValueWithPointer(response.getJSONObject(0), paginationInfo.get("has_next"));
	}

	private String getNextPage(JSONArray response, Map<String, String> paginationInfo) {
		if (response.isEmpty()) {
			return null;
		}
		return getValueWithPointer(response.getJSONObject(0), paginationInfo.get("end_cursor"));
	}

	private JSONArray httpRequestSend(HttpRequestDto httpRequestDto) {
		try {
			return httpRequestSender.send(httpRequestDto);
		} catch (ResponseTypeMissMatchException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getValueWithPointer(JSONObject targetSource, String valuePointer) {
		List<String> pointerList = Arrays.asList(valuePointer.split("\\."));
		JSONPointer.Builder builder = JSONPointer.builder();
		pointerList.forEach(builder::append);
		JSONPointer jsonPointer = builder.build();
		try {
			return String.valueOf(jsonPointer.queryFrom(targetSource));
		} catch (Exception e) {
			return null;
		}
	}
}
