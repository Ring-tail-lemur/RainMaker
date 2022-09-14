package org.example.functions.api_service.github.request_query_generator.graphql;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.api_service.github.HttpRequestDto;
import org.example.functions.api_service.github.request_query_generator.ApiGenerator;
import org.example.functions.util.ConfigReader;
import org.example.functions.util.StringFormatter;
import org.example.functions.util.TypeConverter;
import org.json.JSONObject;

public class GithubGraphqlGenerator implements ApiGenerator {

	private final TypeConverter typeConverter = TypeConverter.getTypeConverter();

	private final ConfigReader configReader = ConfigReader.getConfigReader();
	private final StringFormatter stringFormatter = new StringFormatter();
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
		String graphqlFileName = configElementJSONObject.getString("graphql");
		String graphqlBody = configReader.getStringConfig(String.format("/static/json/graphql/%s", graphqlFileName));
		String formattedBody = getGraphqlFormatString(graphqlBody);
		String body = stringFormatter.bindParameters(formattedBody, requestParameters);
		return HttpRequestDto.builder()
			.url(new URL("https://api.github.com/graphql"))
			.method("POST")
			.requestType(tableName)
			.body(body)
			.header(getHeader())
			.build();
	}

	private String getGraphqlFormatString(String graphqlBody) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("query", graphqlBody);
		return jsonObject.toString();
	}

	private JSONObject getHeader() {
		JSONObject header = new JSONObject();
		header.put("Authorization", String.format("Bearer %s", requestParameters.get("token")));
		return header;
	}
}
