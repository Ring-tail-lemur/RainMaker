package org.example.functions.api_service.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;

public class GithubRestApiSender {

	TypeConverter typeConverter = TypeConverter.getTypeConverter();

	public Map<String, JSONArray> sendAllGithubRestApi(List<GithubRestApiDto> githubRestApiDtoList) throws IOException {
		Map<String, JSONArray> responseJSONArrayList = new HashMap<>();
		for (GithubRestApiDto githubRestApiDto : githubRestApiDtoList) {
			responseJSONArrayList.put(githubRestApiDto.getRequestType(), sendGithubRestApi(githubRestApiDto));
		}
		return responseJSONArrayList;
	}
	public JSONArray sendGithubRestApi(GithubRestApiDto githubRestApiDto) throws IOException {
		HttpURLConnection httpURLConnection = (HttpURLConnection)githubRestApiDto.getUrl().openConnection();
		httpURLConnection.setRequestMethod(githubRestApiDto.getMethod());
		setConnectionHeader(githubRestApiDto, httpURLConnection);
		httpURLConnection.setDoOutput(true);

		String responseString = getResponseString(httpURLConnection);

		if (typeConverter.canConvertJSONArray(responseString)) {
			return typeConverter.convertStringToJSONArray(responseString);
		}
		if (typeConverter.canConvertJSONObject(responseString)) {
			JSONObject responseJsonArray = typeConverter.convertStringToJSONObject(responseString);
			return typeConverter.coverJSONObjectWithJSONArray(responseJsonArray);
		}
		throw new RuntimeException("응답이 JSON형태가 아닙니다.");
	}

	private String getResponseString(HttpURLConnection httpURLConnection) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}

	private void setConnectionHeader(GithubRestApiDto githubRestApiDto, HttpURLConnection httpURLConnection) {
		JSONObject header = githubRestApiDto.getHeader();
		Iterator<String> headerKeys = header.keys();
		while (headerKeys.hasNext()) {
			String headerKey = headerKeys.next();
			httpURLConnection.setRequestProperty(headerKey, String.valueOf(header.get(headerKey)));
		}
	}
}