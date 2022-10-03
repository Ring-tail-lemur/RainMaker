package org.example.functions.extractor.datainterface;

import java.io.IOException;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.dto.extracting.DataExtractingConfigDto;
import org.example.functions.extractor.datainterface.transporter.HttpRequestSender;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.json.JSONArray;

public class GraphqlAdapter implements DataSourceAdapter {

	private static final GraphqlAdapter graphqlAdapter = new GraphqlAdapter();
	public HttpRequestSender httpRequestSender = HttpRequestSender.getInstance();

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
		IOException,
		ResponseTypeMissMatchException {
		if (dataExtractingConfigDto.hasRequestVariable()) {
			return dataExtractingConfigDto.getAllHttpRequestDto()
				.parallelStream()
				.map(this::httpRequestSend)
				.reduce(JSONArray::putAll)
				.orElseGet(JSONArray::new);
		}
		HttpRequestDto httpRequestDto = dataExtractingConfigDto.getHttpRequestDto();
		return httpRequestSender.send(httpRequestDto);
	}

	private JSONArray httpRequestSend(HttpRequestDto httpRequestDto) {
		try {
			return httpRequestSender.send(httpRequestDto);
		} catch (ResponseTypeMissMatchException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
