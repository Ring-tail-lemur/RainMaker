package org.example.functions.api_service.github.request_query_generator.graphql;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.example.functions.api_service.github.HttpRequestDto;
import org.example.functions.api_service.github.request_query_generator.ApiGenerator;
import org.example.functions.util.TypeConverter;
import org.json.JSONObject;

public class GithubGraphqlGenerator implements ApiGenerator {

	private final TypeConverter typeConverter = TypeConverter.getTypeConverter();

	private Map<String, String> requestParameters;

	public GithubGraphqlGenerator(Map<String, String> requestQueryParameters) {
		this.requestParameters = requestQueryParameters;
	}

	public List<HttpRequestDto> getHttpRequestDtoList(JSONObject configJSONObject) throws MalformedURLException {
		List<HttpRequestDto> httpRequestDtoList = new ArrayList<>();

		Iterator<String> configElementKeys = configJSONObject.keys();
		while (configElementKeys.hasNext()) {
			String configElementKey = configElementKeys.next();
			HttpRequestDto httpRequestDto = getHttpRequestDto(
				configJSONObject.getJSONObject(configElementKey),
				configElementKey);
			httpRequestDtoList.add(httpRequestDto);
		}
		return httpRequestDtoList;
	}

	public HttpRequestDto getHttpRequestDto(JSONObject configElementJSONObject, String tableName) throws
		MalformedURLException {
		JSONObject graphqlBody = configElementJSONObject.getJSONObject("graphql");
		String body = bindRequestParameters(graphqlBody.toString(), requestParameters);
		return HttpRequestDto.builder()
			.url(new URL("https://api.github.com/graphql"))
			.method("POST")
			.requestType(tableName)
			.body(body)
			.header(getHeader())
			.build();
	}

	private JSONObject getHeader() {
		JSONObject header = new JSONObject();
		header.put("token", requestParameters.get("token"));
		return header;
	}

	private String bindRequestParameters(String graphqlBody, Map<String, String> requestParameters) {
		graphqlBody = graphqlBody.toLowerCase();
		StringSubstitutor strSubstitutor = new StringSubstitutor(requestParameters, "{", "}");
		return strSubstitutor.replace(graphqlBody);
	}

}
