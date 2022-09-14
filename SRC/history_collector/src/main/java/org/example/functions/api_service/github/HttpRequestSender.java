package org.example.functions.api_service.github;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.functions.util.TypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpRequestSender {

	TypeConverter typeConverter = TypeConverter.getTypeConverter();

	public Map<String, JSONArray> sendAllHttpRequest(List<HttpRequestDto> httpRequestDtoList) throws IOException {
		Map<String, JSONArray> responseJSONArrayList = new HashMap<>();
		for (HttpRequestDto httpRequestDto : httpRequestDtoList) {
			responseJSONArrayList.put(httpRequestDto.getRequestType(), sendHttpRequest(httpRequestDto));
		}
		return responseJSONArrayList;
	}
	public JSONArray sendHttpRequest(HttpRequestDto httpRequestDto) throws IOException {
		HttpURLConnection httpURLConnection = httpRequestDto.getHttpURLConnection();
		String responseString = getResponseString(httpURLConnection);

		if (typeConverter.canConvertJSONArray(responseString)) {
			return typeConverter.convertStringToJSONArray(responseString);
		}
		if (typeConverter.canConvertJSONObject(responseString)) {
			JSONObject responseJsonObject = typeConverter.convertStringToJSONObject(responseString);
			JSONArray responseJsonArray = typeConverter.coverJSONObjectWithJSONArray(responseJsonObject);
			return typeConverter.normalizeJsonObject(responseJsonArray);
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
}
