package org.example.functions.dto.extracting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.util.StringFormatter;
import org.json.JSONObject;

import lombok.Getter;

@Getter
public class RestApiConfigDto extends DataExtractingConfigDto implements DataSourceInterfaceConfigDtoInterface {

	private String body = null;
	private JSONObject header;
	private URL url;
	private String method;

	public RestApiConfigDto(DataExtractingConfigDto dataExtractingConfigDto) throws MalformedURLException {
		super(dataExtractingConfigDto);
		init();
	}

	@Override
	public HttpRequestDto getHttpRequestDto() {
		return new HttpRequestDto(this);
	}

	@Override
	public Map<String, String> getPaginationInfo() {
		return null;
	}

	private void init() throws MalformedURLException {
		setHeader();
		setUrl();
		setMethod();
	}

	private void setHeader() {
		header = getDataRequestContext().getJSONObject("header");
		header.put("Authorization", String.format("Bearer %s", getRequestParameter().get("token")));
	}

	private void setUrl() throws MalformedURLException {
		String formattedUrl = getPathParameterBoundUrl();
		String queryParameterBoundUrl = getQueryParameterBoundUrl(formattedUrl);
		url = new URL(queryParameterBoundUrl);
	}

	private void setMethod() {
		method = getDataRequestContext().getString("method");
	}

	private String getQueryParameterBoundUrl(String formattedUrl) {
		JSONObject queryParameters = getDataRequestContext().getJSONObject("query_parameters");
		List<String> queryParameterList = new ArrayList<>();
		Iterator<String> queryParameterKeys = queryParameters.keys();
		while (queryParameterKeys.hasNext()) {
			String queryParameterKey = queryParameterKeys.next();
			queryParameterList.add(String.format("%s=%s", queryParameterKey, queryParameters.get(queryParameterKey)));
		}

		return String.format("%s?%s", formattedUrl, String.join("&", queryParameterList));
	}

	private String getPathParameterBoundUrl() {
		StringFormatter stringFormatter = new StringFormatter();
		return stringFormatter.bindParameters(getDataRequestContext().getString("url"), getRequestParameter());
	}
}
