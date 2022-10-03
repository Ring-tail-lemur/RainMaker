package org.example.functions.extractor.datainterface.transporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.example.functions.dto.HttpRequestDto;
import org.example.functions.util.exception.ResponseTypeMissMatchException;
import org.example.functions.util.TypeConverter;
import org.json.JSONArray;

public class HttpRequestSender {

	private static final HttpRequestSender httpRequestSender = new HttpRequestSender();
	private final TypeConverter typeConverter = TypeConverter.getTypeConverter();

	private HttpRequestSender(){
	}

	public static HttpRequestSender getInstance() {
		return httpRequestSender;
	}

	public JSONArray send(HttpRequestDto httpRequestDto) throws ResponseTypeMissMatchException, IOException {
		String responseString = getResponseString(httpRequestDto.getHttpURLConnection());
		if (typeConverter.canConvertJSONArray(responseString)) {
			return typeConverter.convertStringToJSONArray(responseString);
		}
		if (typeConverter.canConvertJSONObject(responseString)) {
			return typeConverter.getNormalizedJsonArray(responseString);
		}
		throw new ResponseTypeMissMatchException("응답이 JSON형태가 아닙니다.");
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
