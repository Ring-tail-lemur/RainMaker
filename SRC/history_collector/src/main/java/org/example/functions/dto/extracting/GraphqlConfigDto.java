package org.example.functions.dto.extracting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.util.FileReader;
import org.example.functions.util.StringFormatter;
import org.json.JSONObject;

import lombok.Getter;

@Getter
public class GraphqlConfigDto extends DataExtractingConfigDto implements DataSourceInterfaceConfigDtoInterface {

	private String body;
	private JSONObject header;
	private URL url;
	private String method;
	StringFormatter stringFormatter = new StringFormatter("$[[", "]]");

	public GraphqlConfigDto(DataExtractingConfigDto dataExtractingConfigDto) throws MalformedURLException {
		super(dataExtractingConfigDto);
		init();
	}

	public HttpRequestDto getHttpRequestDto() {
		return new HttpRequestDto(this);
	}

	public List<HttpRequestDto> getAllHttpRequestDto() {
		List<String> usingRequestVariableValues = getRequestVariableMap().get(getUsingRequestVariable());
		return usingRequestVariableValues.stream()
			.map(this::getFormattedHttpRequestDto)
			.collect(Collectors.toList());
	}

	private HttpRequestDto getFormattedHttpRequestDto(String variableValue) {
		HashMap<String, String> variableBindingMap = new HashMap<>();
		variableBindingMap.put(getUsingRequestVariable(), variableValue);
		String formattedBody = stringFormatter.bindParameters(body, variableBindingMap);
		return new HttpRequestDto(url, header, method, formattedBody);
	}

	private void init() throws MalformedURLException {
		setHeader();
		setUrl();
		setBody();
		setMethod();
	}

	private void setMethod() {
		method = "POST";
	}

	private void setBody() {
		String graphqlBody = getFileContents();
		body = getFormattedBody(graphqlBody);
	}

	private String getFormattedBody(String graphqlBody) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("query", graphqlBody);
		String formattedBody = jsonObject.toString();

		StringFormatter stringFormatter = new StringFormatter();
		return stringFormatter.bindParameters(formattedBody, getRequestParameter());
	}

	private String getFileContents() {
		String fileName = getDataRequestContext().getString("file_name");
		FileReader fileReader = FileReader.getInstance();
		return fileReader.readFile(String.format("/static/json/graphql/%s", fileName));
	}

	private void setUrl() throws MalformedURLException {
		url = new URL("https://api.github.com/graphql");
	}

	private void setHeader() {
		header = new JSONObject();
		header.put("Authorization", String.format("Bearer %s", getRequestParameter().get("token")));
	}
}
